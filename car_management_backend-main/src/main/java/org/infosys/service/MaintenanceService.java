package org.infosys.service;
import org.infosys.exception.InvalidEntityException;
import org.infosys.model.Car;
import org.infosys.model.Maintenance;
import org.infosys.repository.CarRepository;
import org.infosys.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 5 * * ?")
    public void checkMaintenanceDue() {
        // Fetch all cars from the database
        List<Car> cars = carRepository.findAll();
        System.out.println("Called");

        for (Car car : cars) {
            // Get the most recent maintenance record for the car
            Optional<Maintenance> recentMaintenanceOpt = maintenanceRepository.findTopByCarOrderByDateDesc(car);

            if (recentMaintenanceOpt.isPresent()) {
                Maintenance recentMaintenance = recentMaintenanceOpt.get();

                // Calculate the difference in months between now and the maintenance date
                long monthsSinceMaintenance = ChronoUnit.MONTHS.between(recentMaintenance.getDate(), LocalDate.now());

                // If the difference exceeds 3 months, send an email notification
                if (monthsSinceMaintenance > 3) {
                    sendMaintenanceNotification(car, recentMaintenance);
                }
            }
        }
    }

    private void sendMaintenanceNotification(Car car, Maintenance recentMaintenance) {
        // Compose the email
        String subject = "Maintenance Due for Car: " + car.getRegistrationNumber();
        String message = String.format(
                "Dear Maintenance Team,%n%nThe car with registration number %s is due for maintenance.%n" +
                        "Last maintenance was performed on: %s.%n" +
                        "Please schedule the maintenance at your earliest convenience.%n%nThank you.%n",
                car.getRegistrationNumber(), recentMaintenance.getDate()
        );

        emailService.sendEmail("mockpanel1@gmail.com", subject, message);
      
        System.out.println("Maintenance notification sent for car: " + car.getRegistrationNumber());
    }
    


    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }
    public boolean createMaintenance(Maintenance maintainance) throws InvalidEntityException {
        if (maintainance == null || maintainance.getCar() == null) {
            throw new InvalidEntityException("Invalid maintenance record. Car ID is required.");
        }
       try {
    	   maintenanceRepository.save(maintainance);
           String subject = "Record Creation ";
           String body = "Dear Applicant your Maintanance record has been created Successfully";
           emailService.sendEmail("springboardmentor430@gmail.com",subject,body);
           return true;
       }
       catch (Exception e){
           System.out.println(e.getMessage());
           return false;
       }
    }

    public Maintenance getById(Long id) throws InvalidEntityException {
        Optional<Maintenance> maintainance = maintenanceRepository.findById(id);
        return maintainance.orElseThrow(() -> new InvalidEntityException("Maintenance record with ID " + id + " not found."));
    }

    public boolean deleteMaintenance(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            return false;
        }
        maintenanceRepository.deleteById(id); // Deletes the maintenance record by Id
        return true;
    }

    public void updateMaintenance(Maintenance record) throws InvalidEntityException {
        if (!maintenanceRepository.existsById(record.getMaintenanceId())) {
            throw new InvalidEntityException("Cannot update record. Maintenance ID " + record.getMaintenanceId() + " not found.");
        }
        maintenanceRepository.save(record); // Save the updated record
    }

    // Get a record by ID
    public Maintenance getMaintenanceById(Long id) throws InvalidEntityException {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new InvalidEntityException("Maintenance record not found for ID: " + id));
    }


}
