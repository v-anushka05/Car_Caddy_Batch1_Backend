package org.infosys.service;



import java.math.BigDecimal;
import java.util.List;

import org.infosys.exception.InvalidEntityException;
import org.infosys.model.Car;
import org.infosys.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private CarRepository repo;

	// Add a new car
	@Override
	public Car addCar(Car car) {
		return repo.save(car);
	}

	// Update existing car
	@Override
	public Car updateCar(Car car) throws InvalidEntityException {
		// Check if the car exists before updating
		if (!repo.existsById(car.getCarId())) {
			throw new InvalidEntityException("Car with ID " + car.getCarId() + " does not exist.");
		}
		return repo.save(car);
	}

	// Fetch car by ID
	@Override
	public Car getCar(Long carId) throws InvalidEntityException {
		return repo.findByCarId(carId)
				.orElseThrow(() -> new InvalidEntityException("Car with ID " + carId + " not found."));
	}

	// Fetch all cars
	@Override
	public List<Car> getAllCars() throws InvalidEntityException {
	    List<Car> cars = repo.findAll();
	    if (cars.isEmpty()) {
	        throw new InvalidEntityException("No cars available.");
	    }
	    return cars;
	}

	// Fetch car by registration number
	@Override
	public Car getCarByRegistrationNumber(String registrationNumber) throws InvalidEntityException {
		return repo.findByRegistrationNumber(registrationNumber)
				.orElseThrow(() -> new InvalidEntityException("Car with registration number " + registrationNumber + " not found."));
	}
	
	
	public List<Car> filtering() throws InvalidEntityException {
		List<Car> cars = repo.findAllByCurrentStatus("Available");
	    if (cars.isEmpty()) {
	        throw new InvalidEntityException("No cars available.");
	    }
	    return cars;
	}

	@Override
	public BigDecimal getCarRentalRate(Long carId) throws InvalidEntityException {
		// TODO Auto-generated method stub
		return repo.findRentalRateByCarId(carId)
				.orElseThrow(() -> new InvalidEntityException("Car with Id " + carId + " not found."));
	}
	
	
}