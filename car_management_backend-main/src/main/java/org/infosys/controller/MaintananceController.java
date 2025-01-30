package org.infosys.controller;

import org.infosys.exception.InvalidEntityException;
import org.infosys.model.Maintenance;
import org.infosys.service.EmailService;
import org.infosys.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/maintenance")
public class MaintananceController {
    @Autowired
    private MaintenanceService maintananceService;
       @Autowired
    private EmailService emailService;

    @GetMapping("/data")
    public List<Maintenance> getAllMaintenance() {
        return maintananceService.getAllMaintenance();
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable Long id) {
        try {
        	Maintenance maintainance = maintananceService.getById(id);
            return new ResponseEntity<>(maintainance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveMaintenanceRecord(@RequestBody Maintenance record) {
        try {
            // Log the incoming maintenance record
            System.out.println("Received record: " + record.toString());

            // Check for existing record
            // Save the maintenance record
            maintananceService.createMaintenance(record);

//             Send email notification after successfully creating the record
            try {
            emailService.sendEmail("springboardmentor430@gmail.com", "Record Creation", "Record created successfully");
            System.out.println("Mail sent successfully");
            } catch (Exception e) {
                System.err.println("Failed to send mail: " + e.getMessage());
                e.printStackTrace();
            }

            // Return success response
            return new ResponseEntity<>(record, HttpStatus.CREATED);

        } catch (InvalidEntityException ex) {
            // Handle validation errors
            return new ResponseEntity<>("Validation error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Handle Update Submission
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateMaintenanceRecord(@PathVariable("id") Long id, @RequestBody Maintenance record) {
        try {
            if (id == null) {
                return new ResponseEntity<>("maintainence id is necessary", HttpStatus.NOT_FOUND);
            }
            record.setMaintenanceId(id);
            maintananceService.updateMaintenance(record);
            emailService.sendEmail("springboardmentor430@gmail.com", "Record Updation", "Record Updated successfully");
            return new ResponseEntity<>("Records updated", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while updating record", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteMaintenanceRecord(@PathVariable("id") Long id) {
        if (maintananceService.deleteMaintenance(id)) {
            emailService.sendEmail("springboardmentor430@gmail.com", "Record Deletion", "Record Deleted successfully");
            return new ResponseEntity<>("Record deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Record Not found", HttpStatus.NOT_FOUND);
    }
}
