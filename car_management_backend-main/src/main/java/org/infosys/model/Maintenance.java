package org.infosys.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "maintenance")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;
    
    @ManyToOne
    @JoinColumn(name="carId")
	@JsonIgnoreProperties("maintenance")
    private Car carId;


    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description", nullable = false)
    private String description;

	public Long getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(Long maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	public Car getCarId() {
		return carId;
	}

	public void setCarId(Car carId) {
		this.carId = carId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
