
package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {

    public long createTransaction(
            Connection connection,
            String transactionType,
            BigDecimal amount,
            String description) throws SQLException {

        String sql = """
                INSERT INTO transactions
                    (transaction_type, amount, description, transaction_time)
                VALUES (?, ?, ?, CURRENT_TIMESTAMP)
                """;

        try (PreparedStatement ps = connection.prepareStatement(
                sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, transactionType);
            ps.setBigDecimal(2, amount);
            ps.setString(3, description);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        throw new SQLException("Could not create transaction record.");
    }

    public void linkTransactionToAccount(
            Connection connection,
            int accountId,
            long transactionId,
            String entryType) throws SQLException {

        String sql = """
                INSERT INTO account_transactions
                    (account_id, transaction_id, entry_type)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setLong(2, transactionId);
            ps.setString(3, entryType);
            ps.executeUpdate();
        }
    }
}
