package org.infosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.infosys.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Employee findByEmailId(String emailId);

	Employee findByContactNumber(String contactNumber);

	boolean existsByContactNumberAndEmployeeIdNot(String contactNumber, Long employeeId);
}
