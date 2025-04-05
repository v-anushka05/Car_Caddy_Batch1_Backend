package org.infosys.service;

import java.util.List;

import org.infosys.exception.InvalidEntityException;
import org.infosys.exception.InvalidLoginException;
import org.infosys.model.Employee;

public interface IEmployeeService {
	Employee registerEmployee(Employee employee);

	Employee login(String emailId, String password) throws InvalidLoginException;

	Employee updatePassword(String emailId, String newPassword)  throws InvalidEntityException;

	Employee getEmployeeById(Long employeeId) throws InvalidEntityException;

	List<Employee> getAllEmployees();

	Employee updateEmployee(Long employeeId, Employee employee) throws InvalidEntityException;

	Employee deleteEmployee(Long employeeId) throws InvalidEntityException;

	void deactivateExpiredAccounts(); // Add this method
}
