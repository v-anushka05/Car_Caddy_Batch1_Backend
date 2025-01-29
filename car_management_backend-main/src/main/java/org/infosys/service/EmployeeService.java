package org.infosys.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.infosys.exception.DuplicateContactNumberException;
import org.infosys.exception.DuplicateEmailException;
import org.infosys.exception.InvalidEntityException;
import org.infosys.exception.InvalidLoginException;
import org.infosys.model.Employee;
import org.infosys.repository.EmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public Employee registerEmployee(Employee employee) {
		if (emailExists(employee.getEmailId())) {
			throw new DuplicateEmailException("Email already exists.");
		}
		if (contactNumberExists(employee.getContactNumber())) {
			throw new DuplicateContactNumberException("Contact number already exists.");
		}

		String defaultPassword = generateDefaultPassword(employee);
		employee.setPassword(defaultPassword);
		employee.setIsFirstLogin(true);

		// Save the employee to the database
		Employee registeredEmployee = employeeRepository.save(employee);

		// Send an email to the employee after registration
		String subject = "Account Created Successfully";
		String body = "Dear " + employee.getFullName() + ",\n\n" + "Your account has been successfully created.\n"
				+ "Your Employee ID: " + registeredEmployee.getEmployeeId() + "\n" + "Email: " + employee.getEmailId()
				+ "\n" + "Your default password: " + defaultPassword + "\n\n"
				+ "Please log in and change your password for security purposes.\n\n" + "Best Regards,\nTeam";

		// Send email notification
		emailService.sendEmail(employee.getEmailId(), subject, body);

		return registeredEmployee;
	}

	@Override
	public Employee login(String emailId, String password) throws InvalidLoginException {
		Employee employee = employeeRepository.findByEmailId(emailId);
		if (employee == null || !employee.getPassword().equals(password)) {
			throw new InvalidLoginException("Invalid email or password.");
		}

		// Check if employee is temporary and if their term has expired
		if ("temporary".equalsIgnoreCase(employee.getAccountType())
				&& employee.getExpiryDate().isBefore(LocalDate.now())) {
			throw new InvalidLoginException("Your term has expired. Please contact support.");
		}

		return employee;
	}

	@Override
	public Employee updatePassword(String emailId, String newPassword) throws InvalidEntityException {
		Employee employee = employeeRepository.findByEmailId(emailId);
		if (employee != null) {
			employee.setPassword(newPassword);
			employee.setIsFirstLogin(false);
			Employee remployee = employeeRepository.save(employee);
			// Send a notification email to the employee
			String subject = "Password Updated Successfully";
			String body = "Dear " + employee.getFullName() + ",\n\n" + "Your password has been successfully updated.\n"
					+ "If you did not request this change, please contact support immediately.\n\n"
					+ "Best Regards,\nTeam";

			emailService.sendEmail(employee.getEmailId(), subject, body);
			return remployee;
		} else {
			throw new InvalidEntityException("Employee not found with email: " + emailId);
		}
	}

	@Override
	public Employee updateEmployee(Long employeeId, Employee updatedDetails) throws InvalidEntityException {
		// Fetch existing employee or throw exception
		Employee existingEmployee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEntityException("Employee not found with ID: " + employeeId));

		// Validate account type, expiry date, and other details
		validateEmployeeDetails(existingEmployee, updatedDetails);

		// Prepare email details
		final String subjectToEmployee = "Employee Details Updated";
		final String subjectToAdmin = "Employee Details Updated";

		// Update employee details
		applyUpdatedDetails(existingEmployee, updatedDetails);

		// Save updated employee
		Employee updatedEmployee = employeeRepository.save(existingEmployee);

		// Send notification to employee
		String bodyToEmployee = prepareEmployeeEmailBody(existingEmployee, updatedEmployee);
		emailService.sendEmail(updatedEmployee.getEmailId(), subjectToEmployee, bodyToEmployee);

		// Send notification to admin
		String bodyToAdmin = prepareAdminEmailBody(updatedEmployee);
		emailService.sendEmail("springboardmentor430@gmail.com", subjectToAdmin, bodyToAdmin);

		return updatedEmployee;
	}

	/**
	 * Validates employee details such as contact number, account type, and expiry
	 * date.
	 */
	private void validateEmployeeDetails(Employee existingEmployee, Employee updatedDetails) throws InvalidEntityException {
		// Validate contact number (10 digits and unique)
		if (!updatedDetails.getContactNumber().matches("\\d{10}")) {
			throw new InvalidEntityException("Contact number must be exactly 10 digits.");
		}

		boolean isContactNumberTaken = employeeRepository.existsByContactNumberAndEmployeeIdNot(
				updatedDetails.getContactNumber(), existingEmployee.getEmployeeId());
		if (isContactNumberTaken) {
			throw new InvalidEntityException("Contact number already exists for another employee.");
		}

		// Validate expiry date if account type is temporary
		if ("temporary".equalsIgnoreCase(updatedDetails.getAccountType())) {
			if (updatedDetails.getExpiryDate() == null) {
				throw new InvalidEntityException("Expiry date must be provided for temporary accounts.");
			}
			if (updatedDetails.getExpiryDate().isBefore(LocalDate.now())) {
				throw new InvalidEntityException("Expiry date must be in the future.");
			}
		} else {
			// Clear expiry date for non-temporary accounts
			updatedDetails.setExpiryDate(null);
		}
	}

	/**
	 * Updates the details of an existing employee with the provided updated
	 * details.
	 */
	private void applyUpdatedDetails(Employee existingEmployee, Employee updatedDetails) {
		existingEmployee.setFullName(updatedDetails.getFullName());
		existingEmployee.setContactNumber(updatedDetails.getContactNumber());
		existingEmployee.setDob(updatedDetails.getDob());
		existingEmployee.setAccountType(updatedDetails.getAccountType());
		existingEmployee.setExpiryDate(updatedDetails.getExpiryDate());
	}

	/**
	 * Prepares the email body for the employee.
	 */
	private String prepareEmployeeEmailBody(Employee existingEmployee, Employee updatedEmployee) {
		return String.format("""
				Dear %s,

				Your account details for Employee ID: %s have been updated to:

				Employee Name: %s
				Email ID: %s
				Contact Number: %s
				DOB: %s
				Account Type: %s
				Expiry Date: %s

				Best Regards,
				System
				""", existingEmployee.getFullName(), existingEmployee.getEmployeeId(), updatedEmployee.getFullName(),
				updatedEmployee.getEmailId(), updatedEmployee.getContactNumber(), updatedEmployee.getDob(),
				updatedEmployee.getAccountType(),
				updatedEmployee.getExpiryDate() != null ? updatedEmployee.getExpiryDate() : "N/A");
	}

	/**
	 * Prepares the email body for the admin.
	 */
	private String prepareAdminEmailBody(Employee updatedEmployee) {
		return String.format("""
				The following details of the employee have been updated:

				Employee ID: %s
				Name: %s
				Email: %s
				Contact Number: %s
				Expiry Date: %s

				Best Regards,
				System
				""", updatedEmployee.getEmployeeId(), updatedEmployee.getFullName(), updatedEmployee.getEmailId(),
				updatedEmployee.getContactNumber(),
				updatedEmployee.getExpiryDate() != null ? updatedEmployee.getExpiryDate() : "N/A");
	}

	@Override
	public Employee deleteEmployee(Long employeeId) throws InvalidEntityException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEntityException("Employee not found with ID: " + employeeId));

		employeeRepository.delete(employee);
		return employee;
	}

	@Override
	public Employee getEmployeeById(Long employeeId) throws InvalidEntityException {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new InvalidEntityException("Employee not found with ID: " + employeeId));
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	private String generateDefaultPassword(Employee employee) {
		String firstLetter = employee.getAccountType().substring(0, 1).toUpperCase();
		String dobFormatted = employee.getDob().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		int nameLength = employee.getFullName().length();
		return firstLetter + dobFormatted + nameLength;
	}

	private boolean emailExists(String emailId) {
		return employeeRepository.findByEmailId(emailId) != null;
	}

	private boolean contactNumberExists(String contactNumber) {
		return employeeRepository.findByContactNumber(contactNumber) != null;
	}

	// Scheduler to check and deactivate expired temporary accounts
//	@Scheduled(cron = "*/5 * * * * ?") // Runs every 5 sec
	@Override
	@Scheduled(cron = "0 0 * * * ?") //runs every one hr
	public void deactivateExpiredAccounts() {
		List<Employee> expiredEmployees = employeeRepository.findAll().stream()
				.filter(employee -> "temporary".equalsIgnoreCase(employee.getAccountType())
						&& employee.getExpiryDate().isBefore(LocalDate.now()))
				.toList();

		for (Employee employee : expiredEmployees) {
			// Send deactivation email to employee
			String subjectToEmployee = "Account Deactivation Notification";
			String bodyToEmployee = "Dear " + employee.getFullName() + ",\n\n"
					+ "Your account has been deactivated as of " + employee.getExpiryDate() + ".\n"
					+ "If you have any questions, please contact support.\n\n" + "Best Regards,\nTeam";

			emailService.sendEmail(employee.getEmailId(), subjectToEmployee, bodyToEmployee);

			// Send deactivation email to admin
			String subjectToAdmin = "Employee Account Deactivated";
			String bodyToAdmin = """
					The following temporary employee account has been deactivated:
					Employee ID: """ + employee.getEmployeeId() + "\n" + "Name: " + employee.getFullName() + "\n"
					+ "Email: " + employee.getEmailId() + "\n" + "Contact Number: " + employee.getContactNumber() + "\n"
					+ "Expiry Date: " + employee.getExpiryDate() + "\n\n" + "Best Regards,\nSystem";

			emailService.sendEmail("springboardmentor430@gmail.com", subjectToAdmin, bodyToAdmin);

			// Remove or deactivate the employee
			employeeRepository.delete(employee); // Alternatively, you can update a status field
		}
	}

}
