package org.infosys.service;

import org.infosys.exception.InvalidEntityException;

import org.infosys.model.Customer;
import org.infosys.model.Employee;
import org.infosys.model.Rental;
import org.infosys.repository.CustomerRepository;
import org.infosys.repository.EmployeeRepository;
import org.infosys.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	public Rental addRental(Rental rental) {
		return rentalRepository.save(rental);
	}

	public List<Rental> getRentalsByEmployeeId(Long employeeId) {
		return rentalRepository.findByEmployee_EmployeeId(employeeId);
	}

	public Rental getRentalById(Long id) {
		return rentalRepository.findById(id).orElse(null);
	}

	public List<Rental> getAllRentals() {
		return rentalRepository.findAll();
	}

	public void deleteRental(Long id) {
		rentalRepository.deleteById(id);
	}

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmailService emailService;

	public Rental addBooking(Rental booking) throws InvalidEntityException {
		if (booking == null)
			throw new InvalidEntityException("Empty Data!");
		Customer customer = customerRepository.findById(booking.getCustomer().getId()).orElseThrow(
				() -> new InvalidEntityException("Customer not found with ID: " + booking.getCustomer().getId()));
		Employee employee = employeeRepository.findById(booking.getEmployee().getEmployeeId())
				.orElseThrow(() -> new InvalidEntityException(
						"Employee not found with ID: " + booking.getEmployee().getEmployeeId()));
		System.out.println(customer.getEmail() + " " + employee.getEmailId());
		emailService.sendEmail(customer.getEmail(), "🚗 Booking Confirmation: Your Ride Details",
				"Booking Summary: \n" + "Start Date: " + booking.getStartDate() + "\nEnd Date: " + booking.getEndDate()
						+ "\nDestination: " + booking.getPickupLocation());
		emailService.sendEmail(employee.getEmailId(), "New Ride Assignment",
				"Details: \n" + "\nDestination: " + booking.getPickupLocation() + "\nStart Date: "
						+ booking.getStartDate() + "\nEnd Date: " + booking.getEndDate());
		return rentalRepository.save(booking);
	}

	public List<Rental> getAllBookings() throws InvalidEntityException {
		List<Rental> bookings = rentalRepository.findAll();
		if (bookings.isEmpty()) {
			throw new InvalidEntityException("No bookings found.");
		}
		return bookings;
	}

	public Rental getBookingById(long id) throws InvalidEntityException {
		return rentalRepository.findById(id)
				.orElseThrow(() -> new InvalidEntityException("Booking with ID " + id + " not found."));
	}

	public Rental updateBooking(long id, Rental updatedBooking) throws InvalidEntityException {
		Rental existingBooking = rentalRepository.findById(id)
				.orElseThrow(() -> new InvalidEntityException("Booking not found with ID: " + id));

		Customer customer = customerRepository.findById(updatedBooking.getCustomer().getId())
				.orElseThrow(() -> new InvalidEntityException(
						"Customer not found with ID: " + updatedBooking.getCustomer().getId()));
		Employee employee = employeeRepository.findById(updatedBooking.getEmployee().getEmployeeId())
				.orElseThrow(() -> new InvalidEntityException(
						"Employee not found with ID: " + updatedBooking.getEmployee().getEmployeeId()));
		System.out.println(customer.getEmail() + " " + employee.getEmailId());

		existingBooking.setCar(updatedBooking.getCar());
		existingBooking.setCustomer(updatedBooking.getCustomer());
		existingBooking.setStartDate(updatedBooking.getStartDate());
		existingBooking.setEndDate(updatedBooking.getEndDate());
		existingBooking.setFare(updatedBooking.getFare());
		existingBooking.setBookingStatus(updatedBooking.getBookingStatus());
		existingBooking.setPickupLocation(updatedBooking.getPickupLocation());
		existingBooking.setEmployee(updatedBooking.getEmployee());
		existingBooking.setDiscount(updatedBooking.getDiscount());

		emailService.sendEmail(customer.getEmail(), "🚗 Booking Updated: Your Ride Details",
				"Booking Summary: \n" + "Start Date: " + updatedBooking.getStartDate() + "\nEnd Date: "
						+ updatedBooking.getEndDate() + "\nDestination: " + updatedBooking.getPickupLocation());
		emailService.sendEmail(employee.getEmailId(), "Rent " + updatedBooking.getBookingId() + " Updated",
				"Hi " + employee.getFullName() + ", \nRent Details: \n" + "\nDestination: "
						+ updatedBooking.getPickupLocation() + "\nStart Date: " + updatedBooking.getStartDate()
						+ "\nEnd Date: " + updatedBooking.getEndDate());
		return rentalRepository.save(existingBooking);
	}

	public void deleteBooking(long id) throws InvalidEntityException {
		if (!rentalRepository.existsById(id)) {
			throw new InvalidEntityException("Booking not found with ID: " + id);
		}
		rentalRepository.deleteById(id);
	}

	private boolean isProcessing = false;

	@Scheduled(cron = "0 0 10 * * ?")
	public String checkAndNotifyUpcomingEndDates() throws InvalidEntityException {
		if (isProcessing) {
			return "Processing already in progress";
		}

		try {
			isProcessing = true;
			LocalDate today = LocalDate.now();
			List<Rental> activeBookings = rentalRepository.findByEndDateAfter(today);
			boolean emailsSent = false;

			for (Rental booking : activeBookings) {
				long daysUntilEnd = ChronoUnit.DAYS.between(today, booking.getEndDate());

				if (daysUntilEnd <= 1 && daysUntilEnd > 0) {
					sendEndDateNotifications(booking, daysUntilEnd);
					emailsSent = true;
				}
			}
			return emailsSent ? "Mail Sent" : "No notifications needed";
		} finally {
			isProcessing = false;
		}
	}

	private void sendEndDateNotifications(Rental booking, long daysRemaining) throws InvalidEntityException {
		Customer customer = customerRepository.findById(booking.getCustomer().getId()).orElseThrow(
				() -> new InvalidEntityException("Customer not found with ID: " + booking.getCustomer().getId()));
		Employee employee = employeeRepository.findById(booking.getEmployee().getEmployeeId())
				.orElseThrow(() -> new InvalidEntityException(
						"Employee not found with ID: " + booking.getEmployee().getEmployeeId()));

		String customerSubject = "🚗 Reminder: Your Car Rental is Ending Soon";
		String customerBody = String.format("""
				Dear Customer,

				Your car rental booking is ending in %d day(s).

				Booking Details:
				End Date: %s
				Location: %s

				Please ensure timely return of the vehicle.

				Thank you for choosing our service!
				""", daysRemaining, booking.getEndDate(), booking.getPickupLocation());

		emailService.sendEmail(customer.getEmail(), customerSubject, customerBody);

		String employeeSubject = "Upcoming Rental End - Action Required";
		String employeeBody = String.format("""
				Dear Employee,
				The following rental booking is ending in %d day(s):

				End Date: %s
				Location: %s

				Please prepare for the vehicle return process.
				""", daysRemaining, booking.getEndDate(), booking.getPickupLocation());

		emailService.sendEmail(employee.getEmailId(), employeeSubject, employeeBody);
	}
}
