package com.company.adminapi.controller;

import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        return serviceLayer.getAllCustomers();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer){
        return serviceLayer.addCustomer(customer);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id) {
        return serviceLayer.getCustomerById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Customer updateCustomer(@RequestBody @Valid Customer customer, @PathVariable int id) {
        if(id!= customer.getCustomerId()){
            throw new IllegalArgumentException("This customer ID was not found...");
        }
        return serviceLayer.updateCustomer(customer);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCustomer(@PathVariable int id) {
        return serviceLayer.deleteCustomer(id);
    }
}
