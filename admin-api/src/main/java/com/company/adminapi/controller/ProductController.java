package com.company.adminapi.controller;

import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getAllProducts(){
        return serviceLayer.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid Product product){
        return serviceLayer.addProduct(product);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProduct(@PathVariable int id) {
        return serviceLayer.getProductById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Product updateProduct(@RequestBody @Valid Product product, @PathVariable int id) {
        if(id!= product.getProductId()){
            throw new IllegalArgumentException("This product ID was not found...");
        }
        return serviceLayer.updateProduct(product);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteProduct(@PathVariable int id) {
        return serviceLayer.deleteProduct(id);
    }
}
