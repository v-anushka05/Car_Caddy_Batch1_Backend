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
import org.springframework.web.bind.annotation.RestController;

import org.infosys.model.Customer;
import org.infosys.exception.InvalidEntityException;
import org.infosys.service.EmailService;
import org.infosys.service.ICustomerService;

@RestController
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private EmailService emailService;
    
    public CustomerController() {
        System.out.println("CustomerController instantiated!");
    }
    
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Welcome to the Car Rental System API!", HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Validated Customer customer) throws InvalidEntityException {
        Customer savedCustomer = customerService.addCustomer(customer);
        emailService.sendEmail(customer.getEmail(), "Welcome to Car Rental Automation Application", "Dear " + customer.getName() + ", Thank you for registering with us!");
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody @Validated Customer customer) 
            throws InvalidEntityException {
        return new ResponseEntity<>(customerService.updateCustomer(id, customer), HttpStatus.OK);
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) throws InvalidEntityException {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() throws InvalidEntityException {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) throws InvalidEntityException {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/updateLoyaltyPoints/{id}/{points}")
    public ResponseEntity<Customer> updateLoyaltyPoints(@PathVariable Long id, @PathVariable int points) 
            throws InvalidEntityException {
        return new ResponseEntity<>(customerService.updateLoyaltyPoints(id, points), HttpStatus.OK);
    }

    @GetMapping("/getCustomersByBlacklistStatus/{status}")
    public ResponseEntity<List<Customer>> getCustomersByBlacklistStatus(@PathVariable boolean status) {
        return new ResponseEntity<>(customerService.getCustomersByBlacklistStatus(status), HttpStatus.OK);
    }
}
