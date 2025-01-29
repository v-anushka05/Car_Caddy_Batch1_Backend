package org.infosys.repository;

import org.infosys.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

	List<Rental> findByEmployee_EmployeeId(Long employeeId);
	List<Rental> findByEndDateAfter(LocalDate today);
}
