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
        List<Seller> sellers = new ArrayList<>(); // Creamos una lista para almacenar los vendedores generados

        for (int i = 0; i < numberOfSellers; i++) { // Iteramos para crear el número especificado de vendedores
            Random random = new Random();

            String documentType = random.nextBoolean() ? "CC" : "CE"; // Generamos aleatoriamente el tipo de documento
            long documentNumber = 100000 + random.nextInt(900000); // Generamos aleatoriamente el número de documento

            String firstName = sellersFirstName[random.nextInt(sellersFirstName.length)]; // Seleccionamos aleatoriamente un nombre de la lista de nombres disponibles
            String lastName = sellersLastName[random.nextInt(sellersLastName.length)]; // Seleccionamos aleatoriamente un apellido de la lista de apellidos disponibles

            Seller seller = new Seller(); // Creamos una instancia de vendedor
            seller.setDocumentType(documentType); // Establecemos el tipo de documento
            seller.setDocumentNumber(documentNumber); // Establecemos el número de documento
            seller.setFirstName(firstName); // Establecemos el nombre
            seller.setLastName(lastName); // Establecemos el apellido

            sellers.add(seller); // Agregamos el vendedor a la lista
        }

        return sellers; // Devolvemos la lista de vendedores generada
    }

    public static void assignProductsToSellers(List<Seller> sellers) {
        Random random = new Random();

        for (Seller seller : sellers) {
            Map<Product, Integer> productsSold = new HashMap<>(); // Mapa para rastrear los productos vendidos por cantidad

            int numberOfProductsSold = random.nextInt(PRODUCTS.size()) + 1; // Número aleatorio de productos vendidos

            for (int i = 0; i < numberOfProductsSold; i++) {
                int productIndex = random.nextInt(PRODUCTS.size());
                Product product = PRODUCTS.get(productIndex);
                int quantitySold = random.nextInt(10) + 1; // Cantidad aleatoria vendida del producto

                // Actualiza el mapa de productos vendidos
                productsSold.put(product, productsSold.getOrDefault(product, 0) + quantitySold);
            }

            seller.setProductSold(productsSold); // Establece los productos vendidos por el vendedor
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

    // Método para generar el archivo "lista de vendedores.txt"
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

    // Método para generar el reporte de ventas de cada vendedor


    // Método para generar el reporte de ventas por vendedor en formato CSV
    public static void generateSalesReportCSV(List<Seller> sellers) {
        String fileName = "sales_Report_Sellers.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Seller Name,Total Sold\n");

            // Ordenar los vendedores por el total vendido de mayor a menor
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
                double totalSales = 0; // Inicializa el total de ventas del vendedor

                // Itera sobre los productos vendidos por el vendedor
                for (Map.Entry<Product, Integer> entry : seller.getProductSold().entrySet()) {
                    Product product = entry.getKey();
                    int quantitySold = entry.getValue();
                    double productTotalSales = quantitySold * product.getPrice();

                    // Escribe la información del producto vendido en el archivo
                    writer.write("Product: " + product.getProductName() + "\n");
                    writer.write("Quantity sold: " + quantitySold + "\n");
                    writer.write("Total sold: " + productTotalSales + "\n\n");

                    // Actualiza el total de ventas del vendedor
                    totalSales += productTotalSales;
                }

                // Actualiza el total vendido por el vendedor
                seller.setTotalSales(totalSales);

                // Escribe el total vendido por el vendedor
                writer.write("Total sold by " + seller.getFirstName() + " " + seller.getLastName() + ": " + totalSales + "\n");

                System.out.println("File generated successfully: " + fileName);
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }

    // Método para generar el reporte de cantidad vendida de todos los productos en formato CSV
    public static void generateProductSalesReportCSV(List<Product> products, List<Seller> sellers) {
        String fileName = "Quantity_Sold_Products_Report.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Product Name, Unit price, Quantity Sold, Total Sold\n");

            for (Product product : products) {
                // Inicializa la cantidad total vendida y el total vendido para este producto
                int totalQuantitySold = 0;
                double totalSales = 0.0;

                // Itera sobre todos los vendedores
                for (Seller seller : sellers) {
                    // Obtiene la cantidad vendida de este producto por este vendedor
                    int quantitySoldBySeller = seller.getProductSold().getOrDefault(product, 0);
                    // Agrega la cantidad vendida por este vendedor a la cantidad total vendida
                    totalQuantitySold += quantitySoldBySeller;
                }

                // Calcula el total vendido para este producto
                totalSales = totalQuantitySold * product.getPrice();

                // Escribe la información en el archivo CSV
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

        // Asignar productos a los vendedores
        assignProductsToSellers(sellers);

        // Generar archivos
        generateProductsListFile(PRODUCTS);
        generateSellersListFile(sellers);
        generateSalesReportBySeller(sellers);
        generateSalesReportCSV(sellers);
        generateProductSalesReportCSV(PRODUCTS, sellers);
    }
}
