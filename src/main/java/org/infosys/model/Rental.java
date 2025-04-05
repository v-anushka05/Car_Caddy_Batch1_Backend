package org.infosys.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Rental {
	
	public Rental() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	@NotEmpty(message = "Provide value for Pickup Location")
	@Column(length = 25)
	private String pickupLocation;

	@NotEmpty(message = "Provide value for Drop Location")
	@Column(length = 25)
	private String dropLocation;

	@FutureOrPresent(message = "Start date should be either current or future date")
	private LocalDate startDate;

	@FutureOrPresent(message = "End date should be either current or future date")
	private LocalDate endDate;

	@Positive(message = "Fare price should be greater than zero")
	private double fare;

	@Positive(message = "Discount price should be greater than zero")
	private double discount;

	@NotEmpty(message = "Provide value for Booking Status")
	@Column(length = 25)
	private String bookingStatus;

//	@ManyToOne
//	@JoinColumn(name = "employee_id", nullable = false)
//	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "employeeId")
	@JsonIgnoreProperties("rentals")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "carId")
	@JsonIgnoreProperties("bookings")
    private Car car;
    
    @ManyToOne
    @JoinColumn(name="id")
	@JsonIgnoreProperties("rentals")
	private Customer customer;

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(String dropLocation) {
		this.dropLocation = dropLocation;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}