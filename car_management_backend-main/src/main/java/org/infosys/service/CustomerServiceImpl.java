package org.infosys.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.infosys.model.Customer;
import org.infosys.exception.InvalidEntityException;
import org.infosys.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws InvalidEntityException {
        if (!customerRepository.existsById(id)) {
            throw new InvalidEntityException("Customer not found");
        }
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() throws InvalidEntityException {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) throws InvalidEntityException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new InvalidEntityException("Customer not found"));
    }

    @Override
    public void deleteCustomer(Long id) throws InvalidEntityException {
        if (!customerRepository.existsById(id)) {
            throw new InvalidEntityException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Customer updateLoyaltyPoints(Long id, int points) throws InvalidEntityException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new InvalidEntityException("Customer not found"));
        customer.setLoyaltyPoints(points);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomersByBlacklistStatus(boolean status) {
        return customerRepository.findByBlacklistStatus(status);
    }
}