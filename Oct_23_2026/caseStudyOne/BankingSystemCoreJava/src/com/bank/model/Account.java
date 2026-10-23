
package com.bank.model;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private int customerId;
    private String accountNumber;
    private BigDecimal balance;
    private String status;

    public Account(int accountId, int customerId,
                   String accountNumber,
                   BigDecimal balance, String status) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }

    public Account(int customerId, String accountNumber) {
        this(0, customerId, accountNumber,
                BigDecimal.ZERO, "ACTIVE");
    }

    public int getAccountId() {
        return accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }
}
