package com.repository;

import com.model.CartItem;
import java.util.HashMap;
import java.util.Map;

public class CartRepository {

    private final Map<Integer, CartItem> cart = new HashMap<>();

    public void addToCart(int productId, String name, double price, int qty) {

        if (cart.containsKey(productId)) {
            CartItem item = cart.get(productId);
            item.setQuantity(item.getQuantity() + qty);
        } else {
            cart.put(productId, new CartItem(productId, name, price, qty));
        }

        System.out.println("Added to cart");
    }

    public void viewCart() {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        System.out.println("ID | NAME | PRICE | QTY | TOTAL");

        for (CartItem item : cart.values()) {
            System.out.println(
                item.getProductId() + " | " +
                item.getProductName() + " | " +
                item.getPrice() + " | " +
                item.getQuantity() + " | " +
                (item.getPrice() * item.getQuantity())
            );
        }
    }

    public Map<Integer, CartItem> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
        System.out.println("Cart cleared");
    }
}

