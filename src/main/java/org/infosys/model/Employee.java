package org.infosys.model;

import java.time.LocalDate;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee")
@JsonIgnoreProperties({ "rentals" })
public class Employee {
	
	public Employee() {}
	
	public Employee(Long employeeId) {this.employeeId = employeeId;}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;

	@NotEmpty(message = "Provide value for Account Type")
	@Column(length = 10)
	private String accountType;

	@NotEmpty(message = "Provide value for Contact Number")
	@Digits(integer = 10, fraction = 0, message = "Contact number must be a valid number with exactly 10 digits")
	@Size(min = 10, max = 10, message = "Contact number must be exactly 10 digits")
	private String contactNumber;

	@PastOrPresent(message = "Date of Birth should be either current or Past date")
	private LocalDate dob;

	@NotEmpty(message = "Provide value for Email Id")
	@Email(message = "Email should be in proper format")
	@Column(length = 30)
	private String emailId;

	@FutureOrPresent(message = "Expiry should be either current or Future date")
	private LocalDate expiryDate;

	@NotEmpty(message = "Provide value for Full Name")
	@Column(length = 30)
	private String fullName;

	private Boolean isFirstLogin;

	@Column(length = 25)
	private String password;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("employee")
	private List<Rental> rentals;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

//    @OneToMany(mappedBy = "employeeId", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("employeeId")
//    private List<Rental> bookings;

//	public List<Rental> getBookings() {
//		return bookings;
//	}
//
//	public void setBookings(List<Rental> bookings) {
//		this.bookings = bookings;
//	}

}
