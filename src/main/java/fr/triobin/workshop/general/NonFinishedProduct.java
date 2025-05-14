package fr.triobin.workshop.general;

import fr.triobin.workshop.Memory;

public class NonFinishedProduct {
    private Product product;
    private ProductStatus status;

    public NonFinishedProduct(Product product) {
        this.product = product;
        this.status = ProductStatus.FREE;
        Memory.saveFile();
    }

    public enum ProductStatus {
        FREE, USED
    }

    public Product getProduct() {
        return product;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
        Memory.saveFile();
    }
}
