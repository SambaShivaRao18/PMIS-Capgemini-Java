package com.bank.app;

import com.bank.model.BankAccount;
import com.bank.service.AccountServices;

public class main {

    public static void main(String[] args) {
        BankAccount acc =
            new BankAccount("Samba Shiva", "123456789098");

        AccountServices ser = new AccountServices();

        ser.aadharSeeding(acc, 123456);
    }
}