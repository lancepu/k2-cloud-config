package com.company.customerservice.controller;

import com.company.customerservice.service.ServiceLayer;
import com.company.customerservice.viewmodel.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"customers"})
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomers(){
        return serviceLayer.getAllCustomers();
    }

    @CachePut(key = "#result.getCustomerId()")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerViewModel createCustomer(@RequestBody @Valid CustomerViewModel customerViewModel){
        return serviceLayer.addCustomer(customerViewModel);
    }

    @Cacheable
    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerViewModel getCustomer(@PathVariable int id) {
        System.out.println("fetching from DB...");
        return serviceLayer.getCustomer(id);
    }

    @CacheEvict(key = "#id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public CustomerViewModel updateCustomer(@RequestBody @Valid CustomerViewModel customerViewModel, @PathVariable int id) {
        if(id!=customerViewModel.getCustomerId()){
            throw new IllegalArgumentException("Customer ID in path must match with request body!");
        }
        return serviceLayer.updateCustomer(customerViewModel);
    }

    @CacheEvict
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCustomer(@PathVariable int id) {
        return serviceLayer.deleteCustomer(id);
    }
}