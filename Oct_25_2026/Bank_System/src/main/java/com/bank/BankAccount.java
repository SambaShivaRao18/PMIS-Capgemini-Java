
package com.bank;

public class BankAccount {

    public int deposit(int balance, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit amount must be positive"
            );
        }

        return balance + amount;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        int balance = 1000;
        balance = account.deposit(balance, 500);

        System.out.println("Updated balance: ₹" + balance);
    }
}
