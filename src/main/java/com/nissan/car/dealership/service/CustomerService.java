package com.nissan.car.dealership.service;

import com.nissan.car.dealership.request.CustomerRequest;
import com.nissan.car.dealership.response.CommonResponse;
import com.nissan.car.dealership.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

      CommonResponse saveCustomer(CustomerRequest customerRequest);

      CommonResponse editCustomer(CustomerRequest customerRequest, Long id);

      CommonResponse deleteCustomer(Long id);

      List<CustomerResponse> getAllCustomer();

      CustomerResponse findByCustomerMobile(String mobile);
}
