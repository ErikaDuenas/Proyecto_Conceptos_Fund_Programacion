package org.eduenas.models;

public class Product {

    private String id;
    private String productName;
    private double price;
    private int totalSoldQuantity;

    public Product(String id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.totalSoldQuantity = 0; // Inicializamos la cantidad total vendida como 0
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalSoldQuantity() {
        return totalSoldQuantity;
    }

    public void setTotalSoldQuantity(int totalSoldQuantity) {
        this.totalSoldQuantity = totalSoldQuantity;
    }

    public void updateTotalSoldQuantity(int quantity) {
        totalSoldQuantity += quantity;
    }



    @Override
    public String toString() {
        return id + ", " + productName + ", " + price;
    }
}
