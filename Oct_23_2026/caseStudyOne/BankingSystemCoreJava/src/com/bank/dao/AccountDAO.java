
package com.bank.dao;

import com.bank.model.Account;
import com.bank.util.DBConnection;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {


    public Account findByAccountNumberAndCustomerId(
            String accountNumber, int customerId)
            throws SQLException {

        String sql = """
            SELECT account_id, customer_id, account_number,
                   balance, status
            FROM accounts
            WHERE account_number = ?
              AND customer_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            ps.setInt(2, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("account_id"),
                            rs.getInt("customer_id"),
                            rs.getString("account_number"),
                            rs.getBigDecimal("balance"),
                            rs.getString("status")
                    );
                }
            }
        }

        return null;
    }



    public List<Account> findByCustomerId(int customerId)
            throws SQLException {

        String sql = """
            SELECT account_id, customer_id, account_number,
                   balance, status
            FROM accounts
            WHERE customer_id = ?
            ORDER BY account_id
            """;

        List<Account> accounts = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account(
                            rs.getInt("account_id"),
                            rs.getInt("customer_id"),
                            rs.getString("account_number"),
                            rs.getBigDecimal("balance"),
                            rs.getString("status")
                    );

                    accounts.add(account);
                }
            }
        }

        return accounts;
    }


    public boolean createAccount(Account account)
            throws SQLException {

        String sql = """
                INSERT INTO accounts
                (customer_id, account_number, balance, status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, account.getCustomerId());
            ps.setString(2, account.getAccountNumber());
            ps.setBigDecimal(3, account.getBalance());
            ps.setString(4, account.getStatus());

            return ps.executeUpdate() == 1;
        }
    }

    public Account findByAccountNumber(String accountNumber)
            throws SQLException {

        String sql = """
                SELECT account_id, customer_id, account_number,
                       balance, status
                FROM accounts
                WHERE account_number = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("account_id"),
                            rs.getInt("customer_id"),
                            rs.getString("account_number"),
                            rs.getBigDecimal("balance"),
                            rs.getString("status")
                    );
                }
            }
        }

        return null;
    }
}
