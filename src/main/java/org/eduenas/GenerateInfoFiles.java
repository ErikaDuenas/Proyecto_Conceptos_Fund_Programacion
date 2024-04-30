package org.eduenas;

import org.eduenas.models.Product;
import org.eduenas.models.Seller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GenerateInfoFiles {
    private static final String[] sellersFirstName = { "Juan", "Maria", "Pedro", "Ana", "Jose",
            "Camila", "David", "Laura", "Miguel", "Sofia"};                                                             //Creating arrays containing predefined first and last names for sellers.

    private static final String[] sellersLastName = {"Perez", "Gómez", "Martínez", "López", "García",
            "Rodriguez", "Hernández", "Ramírez", "Sanchez", "Díaz"};                                                    //These will be used later to randomly assign first and last names to the generated sellers.
    public static List<Seller> SELLERS = new ArrayList<>();                                                     //We create a list of 'Seller' objects, which we will later use to store the document types and document numbers for each seller.
    public static final List<Product> PRODUCTS = new ArrayList<>();                                                    //Creating a list of 'Product' objects
    static {
        PRODUCTS.add(new Product("01", "Cellphone", 599.35));                                      //We initialize a list of products with ID, product name and price
        PRODUCTS.add(new Product("02", "Laptop", 355.42));
        PRODUCTS.add(new Product("03", "Tablet", 260.15));
        PRODUCTS.add(new Product("04", "wireless headphones", 98.14));
        PRODUCTS.add(new Product("05", "Smartwatch", 320.50));
        PRODUCTS.add(new Product("06", "4K TV", 687.84));
    }

    public static List<Seller> sellerGenerator(int numberOfSellers) {
        List<Seller> sellers = new ArrayList<>(); // We create a list to store the generated sellers

        for (int i = 0; i < numberOfSellers; i++) { // We iterate to create the specified number of sellers
            Random random = new Random();

            String documentType = random.nextBoolean() ? "CC" : "CE"; // We randomly generate the type of document
            long documentNumber = 100000 + random.nextInt(900000); // We randomly generate the document number

            String firstName = sellersFirstName[random.nextInt(sellersFirstName.length)]; // We randomly select a name from the list of available names
            String lastName = sellersLastName[random.nextInt(sellersLastName.length)]; // We randomly select a surname from the list of available surnames

            Seller seller = new Seller(); //We create a seller instance
            seller.setDocumentType(documentType); // We establish the type of document
            seller.setDocumentNumber(documentNumber); // We establish the document number
            seller.setFirstName(firstName); //We set the name
            seller.setLastName(lastName); // We establish the last name

            sellers.add(seller); // We add the seller to the list
        }

        return sellers; // We return the generated seller list
    }

    public static void assignProductsToSellers(List<Seller> sellers) {
        Random random = new Random();

        for (Seller seller : sellers) {
            Map<Product, Integer> productsSold = new HashMap<>(); //Map to track products sold by quantity

            int numberOfProductsSold = random.nextInt(PRODUCTS.size()) + 1; // Random number of products sold

            for (int i = 0; i < numberOfProductsSold; i++) {
                int productIndex = random.nextInt(PRODUCTS.size());
                Product product = PRODUCTS.get(productIndex);
                int quantitySold = random.nextInt(10) + 1; // Random quantity sold of the product

                //Update the map of sold products
                productsSold.put(product, productsSold.getOrDefault(product, 0) + quantitySold);
            }

            seller.setProductSold(productsSold); // Sets the products sold by the seller
        }
    }


    public static int randomIntGenerator(){
        int min = 0;
        int max = 9;
        Random random =new Random();
        return random.nextInt(max-min +1) + min;
    }

    public static void generateProductsListFile(List<Product> products) {
        String fileName = "products.txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Product product : products) {
                writer.write("ID: " + product.getId() + "\n");
                writer.write("Name: " + product.getProductName() + "\n");
                writer.write("Price: " + product.getPrice() + "\n\n");
            }
            System.out.println("File generated successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // Method to generate the "seller list.txt" file
    public static void generateSellersListFile(List<Seller> sellers) {
        String fileName = "sellers.txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Seller seller : sellers) {
                writer.write("ID Type: " + seller.getDocumentType() + "\n");
                writer.write("ID number : " + seller.getDocumentNumber() + "\n");
                writer.write("First Name: " + seller.getFirstName() + "\n");
                writer.write("Last Name: " + seller.getLastName() + "\n\n");
            }
            System.out.println("File generated successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // Method to generate the sales report for each seller


    // Method to generate the sales report by seller in CSV format
    public static void generateSalesReportCSV(List<Seller> sellers) {
        String fileName = "sales_Report_Sellers.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Seller Name,Total Sold\n");

            // Sort sellers by total sold from highest to lowest
            sellers.sort((s1, s2) -> Double.compare(s2.getTotalSales(), s1.getTotalSales()));

            for (Seller seller : sellers) {
                writer.write(seller.getFirstName() + " " + seller.getLastName() + "," + seller.getTotalSales() + "\n");
            }
            System.out.println("File generated successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    public static void generateSalesReportBySeller(List<Seller> sellers) {

        int i = 1;
        for (Seller seller : sellers) {
            String fileName = "seller_" + (i++) +"_"+  seller.getFirstName() + "_" + seller.getLastName() + ".txt";

            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write("=== " + seller.getFirstName() + " " + seller.getLastName() + " sales report ===\n\n");
                double totalSales = 0; // Initializes the seller's sales total

                // Iterates over the products sold by the seller
                for (Map.Entry<Product, Integer> entry : seller.getProductSold().entrySet()) {
                    Product product = entry.getKey();
                    int quantitySold = entry.getValue();
                    double productTotalSales = quantitySold * product.getPrice();

                    // Write the information of the sold product to the file
                    writer.write("Product: " + product.getProductName() + "\n");
                    writer.write("Quantity sold: " + quantitySold + "\n");
                    writer.write("Total sold: " + productTotalSales + "\n\n");

                    // Update the seller's sales total
                    totalSales += productTotalSales;
                }

                // Updates the total sold by the seller
                seller.setTotalSales(totalSales);

                // Write the total sold by the seller
                writer.write("Total sold by " + seller.getFirstName() + " " + seller.getLastName() + ": " + totalSales + "\n");

                System.out.println("File generated successfully: " + fileName);
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }

    //Method to generate the sold quantity report of all products in CSV format
    public static void generateProductSalesReportCSV(List<Product> products, List<Seller> sellers) {
        String fileName = "Quantity_Sold_Products_Report.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Product Name, Unit price, Quantity Sold, Total Sold\n");

            for (Product product : products) {
                // Initializes the total quantity sold and total sold for this product
                int totalQuantitySold = 0;
                double totalSales = 0.0;

                // Iterate over all sellers
                for (Seller seller : sellers) {
                    // Gets the quantity sold of this product by this seller
                    int quantitySoldBySeller = seller.getProductSold().getOrDefault(product, 0);
                    // Add the quantity sold by this seller to the total quantity sold
                    totalQuantitySold += quantitySoldBySeller;
                }

                // Calculate the total sold for this product
                totalSales = totalQuantitySold * product.getPrice();

                // Write the information to the CSV file
                writer.write(product.getProductName() + "," + product.getPrice() + ","
                        + totalQuantitySold + "," + totalSales + "\n");
            }
            System.out.println("File generated successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        List<Seller> sellers = sellerGenerator(10);

        // Assign products to sellers
        assignProductsToSellers(sellers);

        // Generate files
        generateProductsListFile(PRODUCTS);
        generateSellersListFile(sellers);
        generateSalesReportBySeller(sellers);
        generateSalesReportCSV(sellers);
        generateProductSalesReportCSV(PRODUCTS, sellers);
    }
}
