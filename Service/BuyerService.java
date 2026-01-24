package com.service;


import java.util.Scanner;

public class BuyerService {

    private final Scanner sc = new Scanner(System.in);

    public void buyerMenu() {

        while (true) {
            System.out.println("\n========== BUYER MENU ==========");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Place Order");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> searchProduct();
                case 3 -> addToCart();
                case 4 -> viewCart();
                case 5 -> placeOrder();
                case 6 -> {
                    System.out.println(" Logged out");
                    return;
                }
                default -> System.out.println(" Invalid choice");
            }
        }
    }

    private void viewProducts() {
        System.out.println(" Showing all products (DB logic comes here)");
    }

    private void searchProduct() {
        System.out.println(" Searching product (DB logic comes here)");
    }

    private void addToCart() {
        System.out.println(" Product added to cart");
    }

    private void viewCart() {
        System.out.println(" Viewing cart");
    }

    private void placeOrder() {
        System.out.println(" Order placed successfully");
    }
}

