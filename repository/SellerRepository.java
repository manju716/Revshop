package com.repository;


import java.sql.*;

import com.config.ConnectionManager;


public class SellerRepository {

    private ConnectionManager connectionManager;

    public SellerRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void saveSeller(long userId, String shopName) throws SQLException {

        String sql = "INSERT INTO sellers (user_id, shop_name) VALUES (?, ?)";

        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, shopName);
            ps.executeUpdate();
        }
    }
}

