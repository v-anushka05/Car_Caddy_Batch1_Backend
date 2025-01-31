package org.infosys.service;

import java.math.BigDecimal;
import java.util.List;

import org.infosys.exception.InvalidEntityException;
import org.infosys.model.Car;

public interface CarService {
	
	public Car addCar(Car car);

	public Car updateCar(Car car) throws InvalidEntityException;

	public Car getCar(Long carId) throws InvalidEntityException;

	public List<Car> getAllCars() throws InvalidEntityException;

	public Car getCarByRegistrationNumber(String registrationNumber)  throws InvalidEntityException;
	
	public List<Car> filtering() throws InvalidEntityException;


}