package com.repository;


import java.sql.*;
import com.config.ConnectionManager;
import com.model.CartItem;

public class ProductRepository {

    //  VIEW ALL PRODUCTS (BUYER)
    public void viewProducts() {
        String sql =
            "SELECT p.id, p.name, p.description, p.price, p.stock, " +
            "IFNULL(ROUND(AVG(r.rating),1),0) AS rating, " +
            "IFNULL(GROUP_CONCAT(r.review SEPARATOR ' | '), 'No reviews') AS reviews " +
            "FROM products p " +
            "LEFT JOIN reviews r ON p.id = r.product_id " +
            "GROUP BY p.id, p.name, p.description, p.price, p.stock";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | NAME | DESCRIPTION | PRICE | STOCK | RATING | REVIEWS");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id")+" | "+
                    rs.getString("name")+" | "+
                    rs.getString("description")+" | "+
                    rs.getDouble("price")+" | "+
                    rs.getInt("stock")+" | "+
                    rs.getDouble("rating")+" | "+
                    rs.getString("reviews")
                );
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    //  VIEW PRODUCTS BY SELLER 
    public void viewProductsBySeller(long sellerId) {
        String sql =
            "SELECT p.id, p.name, p.description, p.price, p.stock, " +
            "IFNULL(ROUND(AVG(r.rating),1),0) AS rating, " +
            "IFNULL(GROUP_CONCAT(r.review SEPARATOR ' | '), 'No reviews') AS reviews " +
            "FROM products p " +
            "LEFT JOIN reviews r ON p.id = r.product_id " +
            "WHERE p.seller_id=? " +
            "GROUP BY p.id, p.name, p.description, p.price, p.stock";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, sellerId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nID | NAME | DESCRIPTION | PRICE | STOCK | RATING | REVIEWS");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id")+" | "+
                    rs.getString("name")+" | "+
                    rs.getString("description")+" | "+
                    rs.getDouble("price")+" | "+
                    rs.getInt("stock")+" | "+
                    rs.getDouble("rating")+" | "+
                    rs.getString("reviews")
                );
            }
        } catch (Exception e) {
            System.out.println("Error loading seller products: " + e.getMessage());
        }
    }

    // SET DISCOUNT & MRP 
    public void setDiscountAndMRP(int id, double mrp, double discount) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE products SET mrp=?, discount_price=? WHERE id=?");
            ps.setDouble(1, mrp);
            ps.setDouble(2, discount);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Discount & MRP updated");
        } catch (Exception e) {
            System.out.println("Error updating discount: " + e.getMessage());
        }
    }

    //  SET STOCK THRESHOLD 
    public void setStockThreshold(int id, int t) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE products SET stock_threshold=? WHERE id=?");
            ps.setInt(1, t);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Stock threshold set");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //  CHECK LOW STOCK 
    public void checkLowStock() {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT name, stock FROM products WHERE stock <= stock_threshold");
            ResultSet rs = ps.executeQuery();

            System.out.println("\n⚠ LOW STOCK ALERT ⚠");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(rs.getString("name") + " → " + rs.getInt("stock"));
            }
            if (!found) System.out.println("All products sufficiently stocked");
        } catch (Exception e) {
            System.out.println("Error checking stock: " + e.getMessage());
        }
    }

    //  ADD PRODUCT 
    public boolean addProduct(String name, double price, int stock, long sellerId) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO products(name,price,stock,seller_id) VALUES(?,?,?,?)");
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, stock);
            ps.setLong(4, sellerId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    // UPDATE STOCK 
    public boolean updateStock(long pid, int stock, long sellerId) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE products SET stock=? WHERE id=? AND seller_id=?");
            ps.setInt(1, stock);
            ps.setLong(2, pid);
            ps.setLong(3, sellerId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error updating stock: " + e.getMessage());
            return false;
        }
    }
    
    public CartItem getProductById(int id) {

        String sql = "SELECT id, name, price FROM products WHERE id=?";

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new CartItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    0   // quantity will be set in cart
                );
            }
        } catch (Exception e) {
            System.out.println("Error fetching product: " + e.getMessage());
        }
        return null;
    }


    // REMOVE PRODUCT
    public boolean removeProduct(long pid, long sellerId) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM products WHERE id=? AND seller_id=?");
            ps.setLong(1, pid);
            ps.setLong(2, sellerId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error removing product: " + e.getMessage());
            return false;
        }
    }
}


