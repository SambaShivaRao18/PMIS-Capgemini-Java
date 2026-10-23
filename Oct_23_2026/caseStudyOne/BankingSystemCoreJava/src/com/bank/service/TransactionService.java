
package com.bank.service;

import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAO();

    public BigDecimal deposit(
            String accountNumber,
            int customerId,
            BigDecimal amount) throws SQLException {

        validateAmount(amount);

        return executeTransaction(
                accountNumber, customerId, amount, true);
    }

    public BigDecimal withdraw(
            String accountNumber,
            int customerId,
            BigDecimal amount) throws SQLException {

        validateAmount(amount);

        return executeTransaction(
                accountNumber, customerId, amount, false);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero.");
        }

        if (amount.scale() > 2) {
            throw new IllegalArgumentException(
                    "Amount cannot have more than two decimal places.");
        }
    }

    private BigDecimal executeTransaction(
            String accountNumber,
            int customerId,
            BigDecimal amount,
            boolean isDeposit) throws SQLException {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Account number cannot be empty.");
        }

        String updateSql = isDeposit
                ? """
                  UPDATE accounts
                  SET balance = balance + ?
                  WHERE account_number = ?
                    AND customer_id = ?
                    AND status = 'ACTIVE'
                  """
                : """
                  UPDATE accounts
                  SET balance = balance - ?
                  WHERE account_number = ?
                    AND customer_id = ?
                    AND status = 'ACTIVE'
                    AND balance >= ?
                  """;

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                // Lock the account row to serialize concurrent transactions.
                Account account = lockOwnedAccount(
                        connection, accountNumber, customerId);

                if (account == null) {
                    throw new IllegalArgumentException(
                            "Account not found or does not belong to you.");
                }

                if (!"ACTIVE".equals(account.getStatus())) {
                    throw new IllegalArgumentException(
                            "This account is not active.");
                }

                try (PreparedStatement ps =
                             connection.prepareStatement(updateSql)) {

                    ps.setBigDecimal(1, amount);
                    ps.setString(2, accountNumber);
                    ps.setInt(3, customerId);

                    if (!isDeposit) {
                        ps.setBigDecimal(4, amount);
                    }

                    int rowsUpdated = ps.executeUpdate();

                    if (rowsUpdated != 1) {
                        throw new IllegalArgumentException(
                                "Insufficient balance for withdrawal.");
                    }
                }

                String type = isDeposit ? "DEPOSIT" : "WITHDRAWAL";
                String description = isDeposit
                        ? "Cash deposit"
                        : "Cash withdrawal";

                long transactionId = transactionDAO.createTransaction(
                        connection, type, amount, description);

                transactionDAO.linkTransactionToAccount(
                        connection,
                        account.getAccountId(),
                        transactionId,
                        isDeposit ? "CREDIT" : "DEBIT");

                BigDecimal newBalance = getCurrentBalance(
                        connection, account.getAccountId());

                connection.commit();
                return newBalance;

            } catch (SQLException | RuntimeException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // The connection will be closed by try-with-resources.
                }
            }
        }
    }

    private Account lockOwnedAccount(
            Connection connection,
            String accountNumber,
            int customerId) throws SQLException {

        String sql = """
                SELECT account_id, customer_id, account_number, balance, status
                FROM accounts
                WHERE account_number = ? AND customer_id = ?
                FOR UPDATE
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setInt(2, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("account_id"),
                            rs.getInt("customer_id"),
                            rs.getString("account_number"),
                            rs.getBigDecimal("balance"),
                            rs.getString("status"));
                }
            }
        }

        return null;
    }

    private BigDecimal getCurrentBalance(
            Connection connection,
            int accountId) throws SQLException {

        String sql = "SELECT balance FROM accounts WHERE account_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }
        }

        throw new SQLException("Could not retrieve updated balance.");
    }
}
