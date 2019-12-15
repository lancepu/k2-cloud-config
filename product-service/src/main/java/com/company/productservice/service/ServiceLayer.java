package com.company.productservice.service;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.dto.Product;
import com.company.productservice.viewmodel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    private ProductDao dao;

    @Autowired
    public ServiceLayer(ProductDao dao) {
        this.dao = dao;
    }


    public ProductViewModel addProduct(ProductViewModel productViewModel){
        Product product = buildModelFromVm(productViewModel);
        product = dao.addProduct(product);
        productViewModel.setProductId(product.getProductId());
        return productViewModel;
    }

    public ProductViewModel getProduct(int productId){
        Product product = dao.getProduct(productId);
        if(product==null){
            throw new IllegalArgumentException("This id for product was not found...");
        } else {
            return buildProductViewModel(product);
        }
    }

    public List<ProductViewModel> getAllProducts(){
        List<Product> products = dao.getAllProducts();
        List<ProductViewModel> productViewModels = new ArrayList<>();

        products.forEach(product -> productViewModels.add(buildProductViewModel(product)));

        return productViewModels;
    }

    @Transactional
    public ProductViewModel updateProduct(ProductViewModel productViewModel){
        getProduct(productViewModel.getProductId());
        dao.addProduct(buildModelFromVm(productViewModel));
        return getProduct(productViewModel.getProductId());
    }

    @Transactional
    public String deleteProduct(int productId){
        getProduct(productId);
        dao.deleteProduct(productId);
        return "Deleted Product ID: "+ productId;
    }


    private Product buildModelFromVm(ProductViewModel productViewModel){
        Product product = new Product();
        product.setProductId(productViewModel.getProductId());
        product.setProductName(productViewModel.getProductName());
        product.setProductDescription(productViewModel.getProductDescription());
        product.setListPrice(productViewModel.getListPrice());
        product.setUnitCost(productViewModel.getUnitCost());
        return product;
    }

    private ProductViewModel buildProductViewModel(Product product){
        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductId(product.getProductId());
        productViewModel.setProductName(product.getProductName());
        productViewModel.setProductDescription(product.getProductDescription());
        productViewModel.setListPrice(product.getListPrice());
        productViewModel.setUnitCost(product.getUnitCost());
        return productViewModel;
    }
}
