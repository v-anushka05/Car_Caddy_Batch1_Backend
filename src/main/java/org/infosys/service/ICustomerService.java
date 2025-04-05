package org.infosys.service;

import java.util.List;



import org.infosys.model.Customer;
import org.infosys.exception.InvalidEntityException;

public interface ICustomerService {
    Customer addCustomer(Customer customer) throws InvalidEntityException;

    
    Customer updateCustomer(Customer customer) throws InvalidEntityException ;

    public List<Customer> getAllCustomers() throws InvalidEntityException;

    Customer getCustomerById(Long id) throws InvalidEntityException;

   void deleteCustomer(Long id) throws InvalidEntityException;

   Customer updateLoyaltyPoints(Long id, int points) throws InvalidEntityException;

   List<Customer> getCustomersByBlacklistStatus(boolean status);

   Customer login(String email, String password) throws InvalidEntityException;


    Customer updateCustomerById(Long id, Customer customer) throws InvalidEntityException;
    
    Integer getLoyaltyPointsByCustomerId(Long customerId) throws InvalidEntityException;
    
    
}
