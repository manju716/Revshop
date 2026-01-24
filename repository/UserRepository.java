package com.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.config.ConnectionManager;

public class UserRepository {

    public boolean registerUser(String name, String phone, String email,
                                String password, String role, String shopName) {

        try (Connection con = ConnectionManager.getConnection()) {

            String userSql = "INSERT INTO users(name, phone, email, password, role) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);

            if (role.equals("SELLER")) {
                String sellerSql = "INSERT INTO sellers(user_id, shop_name) VALUES (?,?)";
                PreparedStatement sp = con.prepareStatement(sellerSql);
                sp.setLong(1, userId);
                sp.setString(2, shopName);
                sp.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            System.out.println(" Registration failed: " + e.getMessage());
            return false;
        }
    }

    public String login(String email, String password) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT role FROM users WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("role");
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public long getSellerIdByEmail(String email) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT s.user_id FROM sellers s JOIN users u ON s.user_id = u.user_id WHERE u.email=?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("user_id");
        } catch (Exception e) {
            System.out.println("Error fetching seller ID: " + e.getMessage());
        }
        return -1;
    }
}



