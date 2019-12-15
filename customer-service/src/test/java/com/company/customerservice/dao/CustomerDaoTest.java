package com.company.customerservice.dao;

import com.company.customerservice.dto.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoTest {

    @Autowired
    private CustomerDao dao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = dao.getAllCustomers();
        customerList.forEach(customer -> dao.deleteCustomer(customer.getCustomerId()));
    }

    @Test
    public void addGetCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setEmail("test2@test.com");
        customer.setPhone("123-456-7890");

        Customer fromAdd = dao.addCustomer(customer);
        Customer fromGet = dao.getCustomer(fromAdd.getCustomerId());

        assertEquals(fromAdd, fromGet);
    }

    @Test
    public void getAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setEmail("test2@test.com");
        customer.setPhone("123-456-7890");
        customer = dao.addCustomer(customer);

        assertEquals(dao.getAllCustomers().size(),1);
        assertEquals(dao.getAllCustomers().get(0),customer);
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setEmail("test@test.com");
        customer.setPhone("123-456-7890");
        customer = dao.addCustomer(customer);

        customer.setEmail("test2@test.com");
        dao.addCustomer(customer);

        assertEquals(dao.getCustomer(customer.getCustomerId()),customer);
    }

    @Test
    public void deleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Chris");
        customer.setLastName("Zevallos");
        customer.setStreet("101 Times Square");
        customer.setCity("New York");
        customer.setZip("10001");
        customer.setEmail("test@test.com");
        customer.setPhone("123-456-7890");
        customer = dao.addCustomer(customer);

        dao.deleteCustomer(customer.getCustomerId());

        assertNull(dao.getCustomer(customer.getCustomerId()));

    }
}