package org.infosys.repository;

import org.infosys.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find a customer by email (unique field)
    Optional<Customer> findByEmail(String email);

    // Find a customer by phone number (unique field)
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    // Find customers by blacklist status
    List<Customer> findByBlacklistStatus(Boolean blacklistStatus);

    // Find customers with loyalty points greater than or equal to a specific value
    List<Customer> findByLoyaltyPointsGreaterThanEqual(Integer points);

    // Find customers with loyalty points less than a specific value
    List<Customer> findByLoyaltyPointsLessThan(Integer points);
}
