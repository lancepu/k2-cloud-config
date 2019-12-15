package com.company.adminapi.util.feign;

import com.company.adminapi.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {


    @RequestMapping(value = "/products", method = RequestMethod.POST)
    Product createProduct(@RequestBody @Valid Product product);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    Product getProduct(@PathVariable int id);

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    List<Product> getAllProducts();

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    Product updateProduct(@RequestBody @Valid Product product, @PathVariable int id);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    String deleteProduct(@PathVariable int id);

}
