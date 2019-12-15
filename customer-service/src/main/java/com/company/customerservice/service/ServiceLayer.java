package com.company.customerservice.service;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dto.Customer;

import com.company.customerservice.viewmodel.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    private CustomerDao dao;

    @Autowired
    public ServiceLayer(CustomerDao dao) {
        this.dao = dao;
    }


    public CustomerViewModel addCustomer(CustomerViewModel customerViewModel){
        Customer customer = buildModelFromViewModel(customerViewModel);
        customer = dao.addCustomer(customer);
        customerViewModel.setCustomerId(customer.getCustomerId());

        return customerViewModel;
    }

    public CustomerViewModel getCustomer(int customerId){
        Customer customer = dao.getCustomer(customerId);
        if(customer==null){
            throw new IllegalArgumentException("This id for customer was not found...");
        } else {
            return buildCustomerViewModel(customer);
        }
    }

    public List<CustomerViewModel> getAllCustomers(){
        List<Customer> customers = dao.getAllCustomers();
        List<CustomerViewModel> customerViewModels = new ArrayList<>();

        customers.forEach(customer -> customerViewModels.add(buildCustomerViewModel(customer)));

        return customerViewModels;
    }

    @Transactional
    public CustomerViewModel updateCustomer(CustomerViewModel customerViewModel){
        getCustomer(customerViewModel.getCustomerId());
        dao.addCustomer(buildModelFromViewModel(customerViewModel));
        return getCustomer(customerViewModel.getCustomerId());
    }

    @Transactional
    public String deleteCustomer(int customerId){
        getCustomer(customerId);
        dao.deleteCustomer(customerId);
        return "Deleted Customer ID: "+ customerId;
    }


    private Customer buildModelFromViewModel(CustomerViewModel customerViewModel){
        Customer customer = new Customer();
        customer.setCustomerId(customerViewModel.getCustomerId());
        customer.setFirstName(customerViewModel.getFirstName());
        customer.setLastName(customerViewModel.getLastName());
        customer.setStreet(customerViewModel.getStreet());
        customer.setCity(customerViewModel.getCity());
        customer.setZip(customerViewModel.getZip());
        customer.setEmail(customerViewModel.getEmail());
        customer.setPhone(customerViewModel.getPhone());
        return customer;
    }

    private CustomerViewModel buildCustomerViewModel(Customer customer){
        CustomerViewModel customerViewModel = new CustomerViewModel();
        customerViewModel.setCustomerId(customer.getCustomerId());
        customerViewModel.setFirstName(customer.getFirstName());
        customerViewModel.setLastName(customer.getLastName());
        customerViewModel.setStreet(customer.getStreet());
        customerViewModel.setCity(customer.getCity());
        customerViewModel.setZip(customer.getZip());
        customerViewModel.setEmail(customer.getEmail());
        customerViewModel.setPhone(customer.getPhone());
        return customerViewModel;
    }
}
