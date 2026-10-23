
package com.bank.dao;

import java.sql.ResultSet;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {


    public Customer findByUsername(String username)
            throws SQLException {

        String sql = """
            SELECT customer_id, full_name, email,
                   username, password_hash
            FROM customers
            WHERE username = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password_hash")
                    );
                }
            }
        }

        return null;
    }


    public int registerCustomer(Customer customer)
            throws SQLException {

        String sql = """
                INSERT INTO customers
                (full_name, email, username, password_hash)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getUsername());
            ps.setString(4, customer.getPasswordHash());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted == 1) {
                try (var keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Customer registration failed.");
    }
}
