package com.company.productservice.dao;

import com.company.productservice.dto.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    ProductDao dao;

    @Before
    public void setUp() throws Exception {
        List<Product> products = dao.getAllProducts();
        products.forEach(product -> dao.deleteProduct(product.getProductId()));
    }

    @Test
    public void addGetProduct() {

        Product product = new Product();
        product.setProductName("Sony Series X");
        product.setProductDescription("perfect Sony console");
        product.setListPrice(new BigDecimal("399.99").setScale(2));
        product.setUnitCost(new BigDecimal("300.00").setScale(2));

        Product fromAdd = dao.addProduct(product);
        Product fromGet = dao.getProduct(fromAdd.getProductId());
        assertEquals(fromAdd, fromGet);
    }

    @Test
    public void findAll() {

        Product product = new Product();
        product.setProductName("Sony Series X");
        product.setProductDescription("perfect Sony console");
        product.setListPrice(new BigDecimal("399.99").setScale(2));
        product.setUnitCost(new BigDecimal("300.00").setScale(2));
        product = dao.addProduct(product);

        assertEquals(dao.getAllProducts().size(),1);
        assertEquals(dao.getAllProducts().get(0),product);
    }

    @Test
    public void save() {
        Product product = new Product();
        product.setProductName("Sony Series X");
        product.setProductDescription("perfect Sony console");
        product.setListPrice(new BigDecimal("399.99").setScale(2));
        product.setUnitCost(new BigDecimal("300.00").setScale(2));
        product = dao.addProduct(product);

        product.setProductName("Sony Series eXtreme!");
        dao.addProduct(product);

        assertEquals(dao.getProduct(product.getProductId()),product);
    }

    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setProductName("Sony Series X");
        product.setProductDescription("perfect Sony console");
        product.setListPrice(new BigDecimal("399.99").setScale(2));
        product.setUnitCost(new BigDecimal("300.00").setScale(2));
        product = dao.addProduct(product);

        dao.deleteProduct(product.getProductId());

        assertNull(dao.getProduct(product.getProductId()));
    }
}