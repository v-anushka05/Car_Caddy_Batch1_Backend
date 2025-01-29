package org.infosys.service;
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

    @Scheduled(cron = "0 * * * * ?")
    public void checkMaintenanceDue() {
        // Fetch all cars from the database
        List<Car> cars = carRepository.findAll();
        System.out.println("Called");

        for (Car car : cars) {
            // Get the most recent maintenance record for the car
            Optional<Maintenance> recentMaintenanceOpt = maintenanceRepository.findTopByCarIdOrderByDateDesc(car);

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
}
