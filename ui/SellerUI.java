package com.ui;

import com.repository.ProductRepository;
import com.repository.OrderRepository;
import java.util.Scanner;

public class SellerUI {

    private ProductRepository productRepo;
    private OrderRepository orderRepo;
    private Scanner sc = new Scanner(System.in);
    private long sellerId;

    public SellerUI(ProductRepository productRepo, long sellerId) {
        this.productRepo = productRepo;
        this.orderRepo = new OrderRepository();
        this.sellerId = sellerId;
    }

    public void showSellerMenu() {
        while (true) {
            System.out.println("\n===== SELLER MENU =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Stock");
            System.out.println("4. Remove Product");
            System.out.println("5. View Orders");
            System.out.println("6. Set Discount & MRP");
            System.out.println("7. Set Inventory Threshold");
            System.out.println("8. View Stock Alerts");
            System.out.println("9. Logout");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addProduct();
                case 2 -> productRepo.viewProductsBySeller(sellerId);   // ✅ seller view
                case 3 -> updateStock();
                case 4 -> removeProduct();
                case 5 -> orderRepo.viewOrdersForSellerProducts(sellerId);
                case 6 -> setDiscount();
                case 7 -> setThreshold();
                case 8 -> productRepo.checkLowStock();
                case 9 -> { 
                    System.out.println("Logged out");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addProduct() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = sc.nextDouble();

        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        productRepo.addProduct(name, price, stock, sellerId);
        System.out.println("Product added successfully!");
    }

    private void updateStock() {
        System.out.print("Product ID: ");
        long id = sc.nextLong();

        System.out.print("New Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        boolean success = productRepo.updateStock(id, stock, sellerId);
        System.out.println(success ? "Stock updated!" : "Failed to update stock.");
    }

    private void removeProduct() {
        System.out.print("Product ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        boolean success = productRepo.removeProduct(id, sellerId);
        System.out.println(success ? "Product removed!" : "Failed to remove product.");
    }

    private void setDiscount() {
        System.out.print("Product ID: ");
        int id = sc.nextInt();

        System.out.print("MRP: ");
        double mrp = sc.nextDouble();

        System.out.print("Discount Price: ");
        double dp = sc.nextDouble();
        sc.nextLine();

        productRepo.setDiscountAndMRP(id, mrp, dp);
        System.out.println("Discount & MRP set successfully!");
    }

    private void setThreshold() {
        System.out.print("Product ID: ");
        int id = sc.nextInt();

        System.out.print("Stock Threshold: ");
        int t = sc.nextInt();
        sc.nextLine();

        productRepo.setStockThreshold(id, t);
        System.out.println("Stock threshold set successfully!");
    }
}




