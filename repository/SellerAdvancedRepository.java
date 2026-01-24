package com.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.config.ConnectionManager;

public class ReviewRepository {

    // ================= ADD REVIEW =================
    public void addReview(int productId, String email, int rating, String review) {

        String userSql = "SELECT user_id FROM users WHERE email=?";
        String reviewSql =
                "INSERT INTO reviews(product_id, user_id, rating, review) VALUES (?,?,?,?)";

        try (Connection con = ConnectionManager.getConnection()) {

            // 🔹 1. Get user_id from email
            PreparedStatement userPs = con.prepareStatement(userSql);
            userPs.setString(1, email);

            ResultSet rs = userPs.executeQuery();
            if (!rs.next()) {
                System.out.println(" User not found");
                return;
            }

            long userId = rs.getLong("user_id");

            // 🔹 2. Insert review
            PreparedStatement ps = con.prepareStatement(reviewSql);
            ps.setInt(1, productId);
            ps.setLong(2, userId);
            ps.setInt(3, rating);
            ps.setString(4, review);

            ps.executeUpdate();
            System.out.println(" Review submitted successfully");

        } catch (Exception e) {
            System.out.println(" Failed to submit review");
        }
    }

    // ================= VIEW REVIEWS =================
    public void viewReviews(int productId) {

        String sql = """
                SELECT r.rating, r.review, u.email
                FROM reviews r
                JOIN users u ON r.user_id = u.user_id
                WHERE r.product_id = ?
                """;

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nRATING | REVIEW | USER");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("rating") + " | " +
                        rs.getString("review") + " | " +
                        rs.getString("email")
                );
            }

        } catch (Exception e) {
            System.out.println(" Failed to load reviews");
        }
    }
}

