package org.infosys.repository;



import java.util.List;

import java.util.Optional;

import org.infosys.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;






public interface CarRepository extends JpaRepository<Car, Long> {

	
	Optional<Car> findByRegistrationNumber(String registrationNumber);

	Optional<Car> findByCarId(Long carId);
	
	List<Car> findAllByCurrentStatus(String currentStatus);
    
}