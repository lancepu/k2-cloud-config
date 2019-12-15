package com.company.productservice.service;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.dto.Product;
import com.company.productservice.viewmodel.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private ServiceLayer serviceLayer;
    private ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        setUpProductDaoMock();
        serviceLayer = new ServiceLayer(productDao);
    }

    @Test
    public void addProduct() {

        ProductViewModel input = new ProductViewModel();
        input.setProductName("Sony Series X");
        input.setProductDescription("perfect Sony console!");
        input.setListPrice(new BigDecimal("399.99").setScale(2));
        input.setUnitCost(new BigDecimal("300.00").setScale(2));

        ProductViewModel fromService = serviceLayer.addProduct(input);

        input.setProductId(1);

        assertEquals(input, fromService);
    }

    @Test
    public void findProduct() {
        ProductViewModel expectedOutput = new ProductViewModel();
        expectedOutput.setProductId(1);
        expectedOutput.setProductName("Sony Series X");
        expectedOutput.setProductDescription("perfect Sony console!");
        expectedOutput.setListPrice(new BigDecimal("399.99").setScale(2));
        expectedOutput.setUnitCost(new BigDecimal("300.00").setScale(2));

        assertEquals(expectedOutput, serviceLayer.getProduct(1));
    }

    @Test
    public void findAllProducts() {
        ProductViewModel expectedOutput = new ProductViewModel();
        expectedOutput.setProductId(1);
        expectedOutput.setProductName("Sony Series X");
        expectedOutput.setProductDescription("perfect Sony console!");
        expectedOutput.setListPrice(new BigDecimal("399.99").setScale(2));
        expectedOutput.setUnitCost(new BigDecimal("300.00").setScale(2));

        assertEquals(1,serviceLayer.getAllProducts().size());
        assertEquals(expectedOutput, serviceLayer.getAllProducts().get(0));

    }

    @Test
    public void updateProduct() {

        ProductViewModel productVm = new ProductViewModel();
        productVm.setProductId(1);
        productVm.setProductName("Sony Series X");
        productVm.setProductDescription("perfect Sony console!");
        productVm.setListPrice(new BigDecimal("399.99").setScale(2));
        productVm.setUnitCost(new BigDecimal("300.00").setScale(2));

        System.out.println(productVm);

        assertEquals(productVm, serviceLayer.updateProduct(productVm));


        ProductViewModel productVmTest = new ProductViewModel();
        productVmTest.setProductId(1); //change ID to target an id that does not exist to confirm function
        productVmTest.setProductName("Sony Series X");
        productVmTest.setProductDescription("perfect Sony console!");
        productVmTest.setListPrice(new BigDecimal("399.99").setScale(2));
        productVmTest.setUnitCost(new BigDecimal("300.00").setScale(2));

        try{
            serviceLayer.updateProduct(productVmTest);
        } catch (Exception e){
            throw new IllegalArgumentException("No product with that Id to update");
        }

        System.out.println(productVmTest);


    }



    private void setUpProductDaoMock() {

        productDao = mock(ProductDao.class);

        Product output = new Product();
        output.setProductId(1);
        output.setProductName("Sony Series X");
        output.setProductDescription("perfect Sony console!");
        output.setListPrice(new BigDecimal("399.99").setScale(2));
        output.setUnitCost(new BigDecimal("300.00").setScale(2));

        Product input = new Product();
        input.setProductName("Sony Series X");
        input.setProductDescription("perfect Sony console!");
        input.setListPrice(new BigDecimal("399.99").setScale(2));
        input.setUnitCost(new BigDecimal("300.00").setScale(2));

        List<Product> products = new ArrayList<>();
        products.add(output);

        doReturn(output).when(productDao).addProduct(input);
        doReturn(output).when(productDao).getProduct(1);
        doReturn(products).when(productDao).getAllProducts();
        //doNothing().when(productDao).save(input);
    }
}