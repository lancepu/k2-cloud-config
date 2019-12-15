package com.company.productservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Table(name = "product")
//@Proxy(lazy = false)
public class Product {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @NotEmpty(message = "")
    private String productName;
    @NotEmpty(message = "")
    private String productDescription;
    @NotNull(message = "")
    private BigDecimal listPrice;
    @NotNull(message = "")
    private BigDecimal unitCost;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId() &&
                Objects.equals(getProductName(), product.getProductName()) &&
                Objects.equals(getProductDescription(), product.getProductDescription()) &&
                Objects.equals(getListPrice(), product.getListPrice()) &&
                Objects.equals(getUnitCost(), product.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getUnitCost());
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName='" + productName + '\'' + ", productDescription='" + productDescription + '\'' + ", listPrice=" + listPrice + ", unitCost=" + unitCost + '}';
    }
}
