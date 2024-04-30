package org.eduenas.models;

import java.util.Map;

public class Seller {

    private String fileName;
    private String documentType;
    private long documentNumber;
    private String firstName;
    private String lastName;
    private double totalSales;
    private Map<Product, Integer> productSold;

    public Seller(String fileName, String documentType, long documentNumber) {
        this.fileName = fileName;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
    }

    public Seller() {
    }

    public String getFileName() {
        return fileName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public long getDocumentNumber() {
        return documentNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setDocumentNumber(long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public Map<Product, Integer> getProductSold() {
        return productSold;
    }

    public void setProductSold(Map<Product, Integer> productSold) {
        this.productSold = productSold;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "documentType='" + documentType + '\'' +
                ", documentNumber=" + documentNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '\n';
    }
}
