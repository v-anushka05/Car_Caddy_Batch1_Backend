package org.infosys.service;

import java.util.List;



import org.infosys.model.Customer;
import org.infosys.exception.InvalidEntityException;

public interface ICustomerService {
    Customer addCustomer(Customer customer);

    
    Customer updateCustomer(Long id, Customer customer) throws InvalidEntityException;

    public List<Customer> getAllCustomers() throws InvalidEntityException;

    Customer getCustomerById(Long id) throws InvalidEntityException;

   void deleteCustomer(Long id) throws InvalidEntityException;

   Customer updateLoyaltyPoints(Long id, int points) throws InvalidEntityException;

   List<Customer> getCustomersByBlacklistStatus(boolean status);
}
