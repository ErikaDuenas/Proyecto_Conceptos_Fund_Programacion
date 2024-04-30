package org.eduenas;

import org.eduenas.models.Seller;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Accede a la lista de vendedores de GenerateInfoFiles
        List<Seller> sellers = GenerateInfoFiles.SELLERS;

        // Genera los reportes de ventas
        GenerateInfoFiles.generateSalesReportCSV(sellers);
        GenerateInfoFiles.generateProductSalesReportCSV(GenerateInfoFiles.PRODUCTS, sellers);

    }
}
