package org.infosys.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.infosys.exception.InvalidEntityException;
import org.infosys.exception.InvalidLoginException;
import org.infosys.model.Employee;
import org.infosys.service.IEmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@PostMapping("/register")
	public ResponseEntity<Employee> registerEmployee(@RequestBody @Validated Employee employee) {
		return new ResponseEntity<>(employeeService.registerEmployee(employee), HttpStatus.OK);
	}

	@PostMapping("/login/{emailId}/{password}")
	public ResponseEntity<Employee> login(@PathVariable String emailId, @PathVariable String password) throws InvalidLoginException {
		return new ResponseEntity<>(employeeService.login(emailId, password), HttpStatus.OK);
	}

	@PostMapping("/update-password/{emailId}/{newPassword}")
	public ResponseEntity<Employee> updatePassword(@PathVariable String emailId, @PathVariable String newPassword)
			throws InvalidEntityException {
		return new ResponseEntity<>(employeeService.updatePassword(emailId, newPassword), HttpStatus.OK);
	}

	// Get employee by employeeId
	@GetMapping("/getEmployeeById/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) throws InvalidEntityException {
		return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
	}

	// Get all employees
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
	}

	// Update employee details by employeeId
	@PutMapping("/updateEmployee/{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId,
			@RequestBody @Validated Employee employee) throws InvalidEntityException {
		return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employee), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{employeeId}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable Long employeeId) throws InvalidEntityException {
		return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
	}

	@PostMapping("/test-expiration")
	public ResponseEntity<String> testExpiration() {
		employeeService.deactivateExpiredAccounts();
		return ResponseEntity.ok("Expiration check triggered.");
	}

}