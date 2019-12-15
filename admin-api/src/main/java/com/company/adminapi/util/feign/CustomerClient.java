package com.company.adminapi.util.feign;

import com.company.adminapi.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {



    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    Customer createCustomer(@RequestBody @Valid Customer customer);

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    Customer getCustomer(@PathVariable int id);

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    List<Customer> getAllCustomers();

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    Customer updateCustomer(@RequestBody @Valid Customer customer, @PathVariable int id);

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    String deleteCustomer(@PathVariable int id);
}
