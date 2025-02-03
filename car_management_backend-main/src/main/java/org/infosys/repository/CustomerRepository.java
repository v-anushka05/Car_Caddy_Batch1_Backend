package org.infosys.repository;

import org.infosys.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Query("SELECT c.loyaltyPoints FROM Customer c WHERE c.id = :customerId")
    Optional<Integer> findLoyaltyPointsByCustomerId(@Param("customerId") Long customerId);
    
   
}
