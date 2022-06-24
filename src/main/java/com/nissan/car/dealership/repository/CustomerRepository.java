package com.nissan.car.dealership.repository;

import com.nissan.car.dealership.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByMobile(String mobile);
}
