package com.nissan.car.dealership.controller;

import com.nissan.car.dealership.request.CustomerRequest;
import com.nissan.car.dealership.response.CommonResponse;
import com.nissan.car.dealership.response.CustomerResponse;
import com.nissan.car.dealership.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/save")
    public ResponseEntity<CommonResponse> saveCustomer(@RequestBody CustomerRequest customerRequest){
        return new ResponseEntity<>(customerService.saveCustomer(customerRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<CommonResponse> editCustomer(@RequestBody CustomerRequest customerRequest, @PathVariable Long id){
        return new ResponseEntity<>(customerService.editCustomer(customerRequest,id), HttpStatus.OK);
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<CommonResponse> deleteCustomer(@PathVariable Long id){
        return  new ResponseEntity<>(customerService.deleteCustomer(id),HttpStatus.OK);
    }

    @GetMapping(value = "/getAllCustomers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomer(),HttpStatus.OK);
    }

    @GetMapping(value = "/findCustomer/{mobile}")
    public ResponseEntity<CustomerResponse> findCustomerByMobile(@PathVariable String mobile){
        return new ResponseEntity<>(customerService.findByCustomerMobile(mobile),HttpStatus.OK);
    }
}
