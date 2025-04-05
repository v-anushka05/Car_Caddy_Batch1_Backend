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
import org.infosys.model.Car;
import org.infosys.model.Customer;
import org.infosys.model.Employee;
import org.infosys.model.Rental;
import org.infosys.service.CarService;
import org.infosys.service.EmployeeService;
import org.infosys.service.ICustomerService;
import org.infosys.service.RentalService;
@RestController
@RequestMapping("/rentals")
public class RentalController {

	@Autowired
	private RentalService rentBookingService;

	@Autowired
	private CarService carService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ICustomerService customerService;

	@PostMapping("/bookCar")
	public ResponseEntity<Rental> createBooking(@Validated @RequestBody Rental booking)
			throws InvalidEntityException {
		return new ResponseEntity<>(rentBookingService.addBooking(booking), HttpStatus.OK);
	}

	@GetMapping("/viewBookingById/{id}")
	public ResponseEntity<Rental> getBookingById(@PathVariable long id) throws InvalidEntityException {
		return new ResponseEntity<>(rentBookingService.getBookingById(id), HttpStatus.OK);
	}

	@GetMapping("/viewAllBookings")
	public ResponseEntity<?> getAllBookings() throws InvalidEntityException {
		return new ResponseEntity<>(rentBookingService.getAllBookings(), HttpStatus.OK);
	}
	
	

	@PutMapping("/updateBooking/{id}")
	public ResponseEntity<Rental> updateBooking(@PathVariable long id,
			@Validated @RequestBody Rental updatedBooking) throws InvalidEntityException {
		return new ResponseEntity<>(rentBookingService.updateBooking(id, updatedBooking), HttpStatus.OK);
	}

	@DeleteMapping("/cancelBooking/{id}")
	public ResponseEntity<String> deleteBooking(@PathVariable long id) throws InvalidEntityException {
		rentBookingService.deleteBooking(id);
		return ResponseEntity.ok("Booking deleted successfully!");
	}
	
	

//	@GetMapping("/cars/{id}")
//	public ResponseEntity<Car> getCarById(@PathVariable int id) throws InvalidEntityException {
//		return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
//	}
//
//	@GetMapping("/viewAllCars")
//	public ResponseEntity<?> getAllCars() throws InvalidEntityException {
//		return new ResponseEntity<>(carService.getAllBookings(), HttpStatus.OK);
//	}
//
//	@GetMapping("/employees/{id}")
//	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) throws InvalidEntityException {
//		return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
//	}
//
//	@GetMapping("/customers/{id}")
//	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) throws InvalidEntityException {
//		return new ResponseEntity<>(customerService.findByCustomerId(id), HttpStatus.OK);
//	}
}
