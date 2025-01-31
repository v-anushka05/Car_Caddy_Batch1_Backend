package org.infosys.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "maintenance")
public class Maintenance {
	
	public Maintenance() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;
    
    @ManyToOne
    @JoinColumn(name="carId")
	@JsonIgnoreProperties("maintenance")
    private Car car;

    @NotNull(message = "Maintenance type is required.")
    @Size(min = 3, max = 50, message = "Maintenance type must be between 3 and 50 characters.")
    @Column(length = 50)
    private String maintenanceType;

    private LocalDate date;

    @Positive
    private Double maintenanceCost;

    @NotNull(message = "Maintenance status is required.")
    @Size(min = 3, max = 50, message = "Maintenance status must be between 3 and 50 characters.")
    @Column(length = 50)
    
    private String maintenanceStatus;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

	public Long getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(Long maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getMaintenanceCost() {
		return maintenanceCost;
	}

	public void setMaintenanceCost(Double maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}

	public String getMaintenanceStatus() {
		return maintenanceStatus;
	}

	public void setMaintenanceStatus(String maintenanceStatus) {
		this.maintenanceStatus = maintenanceStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
