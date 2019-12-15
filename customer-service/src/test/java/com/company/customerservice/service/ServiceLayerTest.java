package com.company.customerservice.service;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dto.Customer;
import com.company.customerservice.viewmodel.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    @MockBean
    private ServiceLayer serviceLayer;
    private CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        setUpCustomerDaoMock();
        serviceLayer = new ServiceLayer(customerDao);
    }

    @Test
    public void addCustomer() {
        CustomerViewModel customerVm = new CustomerViewModel();
        customerVm.setFirstName("Chris");
        customerVm.setLastName("Zevallos");
        customerVm.setStreet("101 Times Square");
        customerVm.setCity("New York");
        customerVm.setZip("10001");
        customerVm.setEmail("test@test.com");
        customerVm.setPhone("123-456-7890");

        CustomerViewModel fromService = serviceLayer.addCustomer(customerVm);
        customerVm.setCustomerId(1);
        assertEquals(customerVm, fromService);
    }

    @Test
    public void getCustomer() {
        CustomerViewModel customerVm = new CustomerViewModel();
        customerVm.setCustomerId(1);
        customerVm.setFirstName("Chris");
        customerVm.setLastName("Zevallos");
        customerVm.setStreet("101 Times Square");
        customerVm.setCity("New York");
        customerVm.setZip("10001");
        customerVm.setEmail("test@test.com");
        customerVm.setPhone("123-456-7890");

        assertEquals(customerVm, serviceLayer.getCustomer(1));
    }

    @Test
    public void getAllCustomers() {
        CustomerViewModel customerVm = new CustomerViewModel();
        customerVm.setCustomerId(1);
        customerVm.setFirstName("Chris");
        customerVm.setLastName("Zevallos");
        customerVm.setStreet("101 Times Square");
        customerVm.setCity("New York");
        customerVm.setZip("10001");
        customerVm.setEmail("test@test.com");
        customerVm.setPhone("123-456-7890");

        assertEquals(1,serviceLayer.getAllCustomers().size());
        assertEquals(customerVm, serviceLayer.getAllCustomers().get(0));

    }

    @Test
    public void updateCustomer() {

        CustomerViewModel customerVm = new CustomerViewModel();
        customerVm.setCustomerId(1);
        customerVm.setFirstName("Chris");
        customerVm.setLastName("Zevallos");
        customerVm.setStreet("101 Times Square");
        customerVm.setCity("New York");
        customerVm.setZip("10001");
        customerVm.setEmail("test@test.com");
        customerVm.setPhone("123-456-7890");

        System.out.println(customerVm);
        assertEquals(customerVm, serviceLayer.updateCustomer(customerVm));

        CustomerViewModel customerVmTest = new CustomerViewModel();
        customerVmTest.setCustomerId(1); //change ID to target an id that does not exist to confirm function
        customerVmTest.setFirstName("Chris");
        customerVmTest.setLastName("Zevallos");
        customerVmTest.setStreet("101 Times Square");
        customerVmTest.setCity("New York");
        customerVmTest.setZip("10001");
        customerVmTest.setEmail("test@test.com");
        customerVmTest.setPhone("123-456-7890");

        try{
            serviceLayer.updateCustomer(customerVmTest);
        } catch (Exception e){
            throw new IllegalArgumentException("No product with that Id to update");
        }

        System.out.println(customerVmTest);

    }


    private void setUpCustomerDaoMock() {
        customerDao = mock(CustomerDao.class);

        Customer fromDb = new Customer();
        fromDb.setCustomerId(1);
        fromDb.setFirstName("Chris");
        fromDb.setLastName("Zevallos");
        fromDb.setStreet("101 Times Square");
        fromDb.setCity("New York");
        fromDb.setZip("10001");
        fromDb.setEmail("test@test.com");
        fromDb.setPhone("123-456-7890");

        Customer toDb = new Customer();
        toDb.setFirstName("Chris");
        toDb.setLastName("Zevallos");
        toDb.setStreet("101 Times Square");
        toDb.setCity("New York");
        toDb.setZip("10001");
        toDb.setEmail("test@test.com");
        toDb.setPhone("123-456-7890");

        List<Customer> customers = new ArrayList<>();
        customers.add(fromDb);

        doReturn(fromDb).when(customerDao).addCustomer(toDb);
        doReturn(fromDb).when(customerDao).getCustomer(1);
        doReturn(customers).when(customerDao).getAllCustomers();
        //doNothing().when(customerDao).save(toDb);
    }
}