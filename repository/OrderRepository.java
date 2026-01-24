package com.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.config.ConnectionManager;
import com.model.CartItem;

public class OrderRepository {

	public void placeOrder(Map<Integer, CartItem> cart) {

	    if (cart.isEmpty()) {
	        System.out.println("Cart is empty");
	        return;
	    }

	    try (Connection con = ConnectionManager.getConnection()) {

	        for (CartItem item : cart.values()) {

	            PreparedStatement ps = con.prepareStatement(
	                "INSERT INTO orders(product_id, quantity, total_price) VALUES(?,?,?)"
	            );

	            ps.setInt(1, item.getProductId());
	            ps.setInt(2, item.getQuantity());
	            ps.setDouble(3, item.getPrice() * item.getQuantity());

	            ps.executeUpdate();
	        }

	        System.out.println("Order placed successfully");

	    } catch (Exception e) {
	        System.out.println("Error placing order: " + e.getMessage());
	    }
	}


        // ===== VIEW ORDERS FOR SELLER PRODUCTS =====
        public void viewOrdersForSellerProducts(long sellerId) {

            String sql =
                "SELECT o.id AS order_id, o.product_id, p.name, o.quantity, o.total_price " +
                "FROM orders o " +
                "JOIN products p ON o.product_id = p.id " +
                "WHERE p.seller_id = ?";

            try (Connection con = ConnectionManager.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setLong(1, sellerId);
                ResultSet rs = ps.executeQuery();

                System.out.println("\nORDER_ID | PRODUCT | QTY | TOTAL");

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println(
                            rs.getInt("order_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getInt("quantity") + " | " +
                            rs.getDouble("total_price")
                    );
                }

                if (!found) System.out.println("No orders for your products yet");

            } catch (Exception e) {
                System.out.println("Error loading orders: " + e.getMessage());
            }
        }
    }

