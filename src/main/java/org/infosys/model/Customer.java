package org.infosys.model;

import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "customer")
public class Customer {
	
	public Customer() {}
	public Customer(Long id) {this.id = id;}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be blank")
    private String name;

    @NotEmpty(message = "Provide value for contact number")
    @Column(length=25)
    private String phoneNumber;

    @NotEmpty(message = "Address cannot be blank")
    private String address;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Min(value = 0, message = "Loyalty points cannot be negative")
    private Integer loyaltyPoints = 0;

    private Boolean blacklistStatus = false;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotEmpty(message = "Driving license is required")
    private String drivingLicense;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("customer")
    private List<Rental> rentals;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public Boolean getBlacklistStatus() {
		return blacklistStatus;
	}

	public void setBlacklistStatus(Boolean blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}
   
}