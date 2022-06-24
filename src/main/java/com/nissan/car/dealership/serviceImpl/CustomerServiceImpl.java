package com.nissan.car.dealership.serviceImpl;

import com.nissan.car.dealership.entity.Customer;
import com.nissan.car.dealership.exception.ResourceNotCreatedException;
import com.nissan.car.dealership.exception.ResourceNotFoundException;
import com.nissan.car.dealership.repository.CustomerRepository;
import com.nissan.car.dealership.request.CustomerRequest;
import com.nissan.car.dealership.response.CommonResponse;
import com.nissan.car.dealership.response.CustomerResponse;
import com.nissan.car.dealership.service.CustomerService;
import com.nissan.car.dealership.utils.DealershipConstants;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CommonResponse saveCustomer(CustomerRequest customerRequest) {
        CommonResponse response = new CommonResponse();
        Customer customer = new Customer();
        try{
            mapCustomerRequest(customer,customerRequest);
            customerRepository.save(customer);
            response.setMessage(DealershipConstants.CUSTOMER_SAVE);
        }catch (NullPointerException| ConstraintViolationException| DataIntegrityViolationException exception){
            throw  new ResourceNotCreatedException(exception.getMessage());
        }
        return response;
    }

    private void mapCustomerRequest(Customer customer, CustomerRequest customerRequest) {

        if(Objects.nonNull(customerRequest.getCustomerName())){
            customer.setCustomerName(customerRequest.getCustomerName());
        }
        if(Objects.nonNull(customerRequest.getAddress())){
            customer.setAddress(customerRequest.getAddress());
        }
        if(Objects.nonNull(customerRequest.getMobile())){
            customer.setMobile(customerRequest.getMobile());
        }
    }

    @Override
    public CommonResponse editCustomer(CustomerRequest customerRequest, Long id) {
        CommonResponse response = new CommonResponse();
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                mapCustomerRequest(customer, customerRequest);
                customerRepository.save(customer);
                response.setMessage(DealershipConstants.CUSTOMER_UPDATE);
            } else {
                throw new ResourceNotFoundException(DealershipConstants.CUSTOMER_NOT_FOUND);
            }
        }
          catch(ConstraintViolationException|DataIntegrityViolationException exception){
                throw new ResourceNotCreatedException(exception.getMessage());
            }

        return response;
    }

    @Override
    public CommonResponse deleteCustomer(Long id) {
        CommonResponse response = new CommonResponse();
        try{
            customerRepository.deleteById(id);
            response.setMessage(DealershipConstants.CUSTOMER_DELETE);
        }
        catch (NullPointerException | DataIntegrityViolationException | EmptyResultDataAccessException exception){
            throw new ResourceNotCreatedException(exception.getMessage());
        }
        return response;
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<CustomerResponse> customerList = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            CustomerResponse customerResponse = new CustomerResponse();
            mapToCustomerResponse(customer,customerResponse);
            customerList.add(customerResponse);
        });
        if(customerList.isEmpty()){
            throw new ResourceNotFoundException(DealershipConstants.CUSTOMER_NOT_FOUND);
        }
        else{
            return customerList;
        }
    }

    private void mapToCustomerResponse(Customer customer, CustomerResponse customerResponse) {
        customerResponse.setCustomerId(customer.getCustomerId());
        customerResponse.setCustomerName(customer.getCustomerName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setMobile(customer.getMobile());
    }

    @Override
    public CustomerResponse findByCustomerMobile(String mobile) {
        CustomerResponse customerResponse = new CustomerResponse();
        try{
            Customer customer= customerRepository.findByMobile(mobile);
            if(Objects.nonNull(customer)){
                mapToCustomerResponse(customer,customerResponse);
                return customerResponse;
            }
            else {
                throw new ResourceNotFoundException(DealershipConstants.CUSTOMER_NOT_FOUND);
            }
        }catch (NullPointerException | DataIntegrityViolationException | EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException(DealershipConstants.CUSTOMER_NOT_FOUND);
        }
    }
}
