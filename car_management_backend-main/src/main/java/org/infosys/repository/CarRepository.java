package org.infosys.repository;



import java.math.BigDecimal;
import java.util.List;

import java.util.Optional;

import org.infosys.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;






public interface CarRepository extends JpaRepository<Car, Long> {

	
	Optional<Car> findByRegistrationNumber(String registrationNumber);

	Optional<Car> findByCarId(Long carId);
	
	List<Car> findAllByCurrentStatus(String currentStatus);
    
	@Query("SELECT c.rentalRate FROM Car c WHERE c.carId = :carId")
	Optional<BigDecimal> findRentalRateByCarId(@Param("carId") Long carId);

    
}