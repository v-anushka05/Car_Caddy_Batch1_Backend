package org.infosys.model;

import java.util.List;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;





@Entity
public class Car {
	
	public Car() {}
	
	public Car(Long carId) {this.carId = carId;}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Registration number cannot be empty.")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Z0-9]+$", 
        message = "Registration number must be a combination of capital letters and numbers, and cannot include special characters."
    )
    private String registrationNumber;

    @Column(nullable = false)
    @NotEmpty(message = "Model cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Model must contain only alphanumeric characters and spaces.")
    private String model;

    @Column(nullable = false)
    @NotEmpty(message = "Company name cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Company name must contain only alphabets and spaces.")
    private String company;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2, message = "Mileage must be a valid decimal number.")
    @Positive(message = "Mileage must be a positive number.")
    private BigDecimal mileage;
    
    @Column(nullable = false)
    @NotEmpty(message = "Color cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Color must contain only alphabets and spaces.")
    private String color;


    @Column(nullable = false)
    @Min(value = 1, message = "Seating capacity must be greater than 0.")
    private Integer seatingCapacity;

    @Column(nullable = false)
    @NotEmpty(message = "Fuel type cannot be empty.")
    private String fuelType; 

    @Column(nullable = false)
    @NotEmpty(message = "Insurance number cannot be empty.")
    @Pattern(
        regexp = "^INS\\d{10}$",
        message = "Insurance number must be in the format: INS followed by 10 digits."
    )
    private String insuranceNumber;

    @Column(nullable = false)
    @NotEmpty(message = "Car condition cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Car condition must not contain special characters except spaces.")
    private String carCondition;

    @Column(nullable = false)
    @NotEmpty(message = "Current status cannot be empty.")
    private String currentStatus;



    @Digits(integer = 10, fraction = 2, message = "Rental rate must be a valid decimal number.")
    @Positive(message = "Rental rate must be a positive number.")
    private BigDecimal rentalRate;
    
    @NotEmpty(message = "Location cannot be empty.")
    private String location;

    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("car")
    private List<Rental> bookings;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("car")
    private List<Maintenance> maintenance;

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getMileage() {
		return mileage;
	}

	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(Integer seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getCarCondition() {
		return carCondition;
	}

	public void setCarCondition(String carCondition) {
		this.carCondition = carCondition;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public BigDecimal getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Rental> getBookings() {
		return bookings;
	}

	public void setBookings(List<Rental> bookings) {
		this.bookings = bookings;
	}

	public List<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(List<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}
    
   

}
