package com.company.adminapi.viewmodel;

import com.company.adminapi.models.Product;

import java.util.Objects;

public class InventoryViewModel {

    private int inventoryId;
    private Product product;
    private int quantity;



    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryViewModel that = (InventoryViewModel) o;
        return getInventoryId() == that.getInventoryId() &&
                getQuantity() == that.getQuantity() &&
                Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProduct(), getQuantity());
    }
}
