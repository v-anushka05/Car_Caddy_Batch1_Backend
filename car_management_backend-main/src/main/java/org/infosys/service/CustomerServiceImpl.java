package org.infosys.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.infosys.model.Customer;
import org.infosys.exception.InvalidEntityException;
import org.infosys.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) throws InvalidEntityException {
    	
    	  if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
              throw new InvalidEntityException("Email is already registered.");
          }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws InvalidEntityException {
    	
    	if (!customerRepository.existsById(customer.getId())) {
			throw new InvalidEntityException("Car with ID " + customer.getId() + " does not exist.");
		}
      
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
    
    
    public Customer login(String email, String password) throws InvalidEntityException {
        Optional<Customer> userOpt = customerRepository.findByEmail(email);
        if (userOpt.isEmpty() || 
            !password.equals(userOpt.get().getPassword())) {
            throw new InvalidEntityException("Invalid email or password.");
        }
        return userOpt.get();
    }

	@Override
	public Customer updateCustomerById(Long id, Customer customer) throws InvalidEntityException {
		 if (!customerRepository.existsById(id)) {
	            throw new InvalidEntityException("Customer not found");
	        }
	        customer.setId(id);
	        return customerRepository.save(customer);
	}
	
	public Integer getLoyaltyPointsByCustomerId(Long customerId) throws InvalidEntityException {
        return customerRepository.findLoyaltyPointsByCustomerId(customerId)
                .orElseThrow(() -> new InvalidEntityException("Customer not found"));
    }

   
}