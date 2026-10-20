package com.bank.model;

public class BankAccount {
    private double balance;
    public String name;
    public String accNo;

    public BankAccount(String name, String accNo) {
        this.name = name;
        this.accNo = accNo;
    }

    public void deposit(double val) {
        if (val <= 0) {
            System.out.println("Invalid Amount");
        } else {
            balance += val;
            System.out.println(val + " Credited to your Account!!");
        }
    }

    public void withDraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid Amount");
        } else if (balance < amount) {
            System.out.println("Insufficient Funds!!");
        } else {
            balance -= amount;
            System.out.println(
                amount + " Deducted from your account. Remaining balance: " + balance
            );
        }
    }

    public void getbal() {
        System.out.println(balance);
    }
}