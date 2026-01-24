package com.ui;


import com.repository.*;
import java.util.Scanner;

public class ConsoleUI {

    Scanner sc = new Scanner(System.in);

    UserRepository userRepo = new UserRepository();
    ProductRepository productRepo = new ProductRepository();
    CartRepository cartRepo = new CartRepository();
    OrderRepository orderRepo = new OrderRepository();
    ReviewRepository reviewRepo = new ReviewRepository();

    String loggedInEmail;

    public void start() {
        while (true) {
            System.out.println("\n========== REVSHOP ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) register();
            else if (choice == 2) login();
            else return;
        }
    }

    private void register() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String pwd = sc.nextLine();

        System.out.print("Role (1-Buyer 2-Seller): ");
        int r = sc.nextInt();
        sc.nextLine();

        String role = (r == 1) ? "BUYER" : "SELLER";
        String shop = null;

        if (role.equals("SELLER")) {
            System.out.print("Shop name: ");
            shop = sc.nextLine();
        }

        userRepo.registerUser(name, phone, email, pwd, role, shop);
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String pwd = sc.nextLine();

        String role = userRepo.login(email, pwd);

        if (role == null) {
            System.out.println(" Invalid credentials");
            return;
        }

        loggedInEmail = email;

        if (role.equals("BUYER")) {
            buyerMenu();
        } else {
            long sellerId = userRepo.getSellerIdByEmail(email);
            SellerUI sellerUI = new SellerUI(productRepo, sellerId);
            sellerUI.showSellerMenu();
        }
    }

    private void buyerMenu() {
        while (true) {
            System.out.println("\n===== BUYER MENU =====");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. Review Product");
            System.out.println("6. View Reviews");
            System.out.println("7. Logout");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> productRepo.viewProducts();
                case 2 -> addToCart();
                case 3 -> cartRepo.viewCart();
                case 4 -> placeOrder();
                case 5 -> addReview();
                case 6 -> viewReviews();
                case 7 -> { return; }
            }
        }
    }

    private void addToCart() {

        System.out.print("Product ID: ");
        int id = sc.nextInt();

        System.out.print("Qty: ");
        int q = sc.nextInt();

        // fetch product details from database
        var product = productRepo.getProductById(id);

        if (product == null) {
            System.out.println("Product not found");
            return;
        }

        cartRepo.addToCart(
            id,
            product.getProductName(),
            product.getPrice(),
            q
        );
    }

    private void placeOrder() {
        orderRepo.placeOrder(cartRepo.getCart());
        cartRepo.clearCart();
    }

    private void addReview() {
        System.out.print("Product ID: ");
        int pid = sc.nextInt();
        sc.nextLine();

        System.out.print("Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        System.out.print("Review: ");
        String review = sc.nextLine();

        reviewRepo.addReview(pid, loggedInEmail, rating, review);
    }

    private void viewReviews() {
        System.out.print("Product ID: ");
        int pid = sc.nextInt();
        reviewRepo.viewReviews(pid);
    }
}

