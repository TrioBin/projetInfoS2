package fr.triobin.workshop.general;

import fr.triobin.workshop.Memory;

public class Product {
    private String productCode;
    private String dProduct;
    private OPList operations;

    public Product(String productCode, String dProduct, OPList operations) {
        this.productCode = productCode;
        this.dProduct = dProduct;
        this.operations = operations;
        Memory.saveFile();
    }

    public void print() {
        System.out.println("- Product: " + productCode + " Description: " + dProduct);
        operations.print();
    }
    
    public void modifier(OPList operation) {
        this.operations = operation;
        Memory.saveFile();
    }

    public void remove(){
        operations.removeOperation(0);
        Memory.saveFile();
    }

    public String getName() {
        return dProduct;
    }

    public OPList getOperations() {
        return operations;
    }

    public String getId() {
        return productCode;
    }

    public void setOperations(OPList operations) {
        this.operations = operations;
        Memory.saveFile();
    }
}
