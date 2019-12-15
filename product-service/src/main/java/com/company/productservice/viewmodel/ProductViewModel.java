package com.company.productservice.viewmodel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductViewModel {

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
        ProductViewModel that = (ProductViewModel) o;
        return getProductId() == that.getProductId() &&
                Objects.equals(getProductName(), that.getProductName()) &&
                Objects.equals(getProductDescription(), that.getProductDescription()) &&
                Objects.equals(getListPrice(), that.getListPrice()) &&
                Objects.equals(getUnitCost(), that.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getUnitCost());
    }


    @Override
    public String toString() {
        return "ProductViewModel{" + "productId=" + productId + ", productName='" + productName + '\'' + ", productDescription='" + productDescription + '\'' + ", listPrice=" + listPrice + ", unitCost=" + unitCost + '}';
    }
}
