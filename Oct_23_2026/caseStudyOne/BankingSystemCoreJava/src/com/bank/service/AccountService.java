
package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.model.Account;

import java.sql.SQLException;
import java.util.UUID;
import java.util.List;

public class AccountService {


    public Account checkBalance(String accountNumber, int customerId)
            throws SQLException {

        Account account =
                accountDAO.findByAccountNumberAndCustomerId(
                        accountNumber, customerId);

        if (account == null) {
            throw new IllegalArgumentException(
                    "Account not found or does not belong to you.");
        }

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new IllegalArgumentException(
                    "This account is not active.");
        }

        return account;
    }



    public List<Account> getMyAccounts(int customerId)
            throws SQLException {

        return accountDAO.findByCustomerId(customerId);
    }


    private final AccountDAO accountDAO = new AccountDAO();

    public Account createAccount(int customerId)
            throws SQLException {

        if (customerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID.");
        }

        String accountNumber;

        Account account;

        do {
            accountNumber = generateAccountNumber();

            account = accountDAO.findByAccountNumber(accountNumber);

        } while (account != null);

        Account newAccount = new Account(
                customerId,
                accountNumber
        );

        boolean created = accountDAO.createAccount(newAccount);

        if (!created) {
            throw new SQLException("Account creation failed.");
        }

        return accountDAO.findByAccountNumber(accountNumber);
    }

    private String generateAccountNumber() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16)
                .toUpperCase();
    }
}
