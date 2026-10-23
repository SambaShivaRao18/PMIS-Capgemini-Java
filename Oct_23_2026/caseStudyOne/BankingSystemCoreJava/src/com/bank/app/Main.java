
package com.bank.app;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.service.AccountService;
import com.bank.service.AuthenticationService;
import com.bank.service.CustomerService;
import com.bank.service.TransactionService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AuthenticationService auth = new AuthenticationService();
        CustomerService customerService = new CustomerService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        try (Scanner sc = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println("\n===== WELCOME TO BANKING SYSTEM =====");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                String choice = sc.nextLine().trim();

                try {

                    switch (choice) {

                        // CUSTOMER REGISTRATION
                        case "1": {
                            System.out.println(
                                    "\n===== CUSTOMER REGISTRATION =====");

                            System.out.print("Enter full name: ");
                            String fullName = sc.nextLine().trim();

                            System.out.print("Enter email: ");
                            String email = sc.nextLine().trim();

                            System.out.print("Choose a username: ");
                            String newUsername = sc.nextLine().trim();

                            System.out.print(
                                    "Choose a password (at least 8 characters): ");
                            String newPassword = sc.nextLine();

                            int customerId =
                                    customerService.registerCustomer(
                                            fullName,
                                            email,
                                            newUsername,
                                            newPassword);

                            System.out.println(
                                    "\nRegistration successful!");
                            System.out.println(
                                    "Your Customer ID: " + customerId);
                            System.out.println(
                                    "You can now log in using your credentials.");

                            break;
                        }

                        // CUSTOMER LOGIN
                        case "2": {
                            System.out.println("\n===== CUSTOMER LOGIN =====");

                            System.out.print("Username: ");
                            String username = sc.nextLine().trim();

                            System.out.print("Password: ");
                            String password = sc.nextLine();

                            Customer customer =
                                    auth.login(username, password);

                            if (customer == null) {
                                System.out.println(
                                        "Invalid username or password.");
                                break;
                            }

                            System.out.println(
                                    "\nLogin successful! Welcome, "
                                            + customer.getFullName());

                            boolean loggedIn = true;

                            while (loggedIn) {

                                System.out.println(
                                        "\n===== BANKING SYSTEM =====");
                                System.out.println("1. Create Bank Account");
                                System.out.println("2. View My Accounts");
                                System.out.println("3. Check Balance");
                                System.out.println("4. Deposit Money");
                                System.out.println("5. Withdraw Money");
                                System.out.println("6. Logout");
                                System.out.print("Choose an option: ");

                                String menuChoice =
                                        sc.nextLine().trim();

                                try {

                                    switch (menuChoice) {

                                        // CREATE BANK ACCOUNT
                                        case "1": {
                                            Account account =
                                                    accountService.createAccount(
                                                            customer.getCustomerId());

                                            System.out.println(
                                                    "\nAccount created successfully!");
                                            System.out.println(
                                                    "Account Number: "
                                                            + account.getAccountNumber());
                                            System.out.println(
                                                    "Balance: ₹"
                                                            + account.getBalance());
                                            System.out.println(
                                                    "Status: "
                                                            + account.getStatus());

                                            break;
                                        }

                                        // VIEW CUSTOMER ACCOUNTS
                                        case "2": {
                                            List<Account> accounts =
                                                    accountService.getMyAccounts(
                                                            customer.getCustomerId());

                                            if (accounts.isEmpty()) {
                                                System.out.println(
                                                        "You have no bank accounts yet.");
                                            } else {
                                                System.out.println(
                                                        "\n===== MY ACCOUNTS =====");

                                                for (Account acc : accounts) {
                                                    System.out.println(
                                                            "Account Number: "
                                                                    + acc.getAccountNumber());
                                                    System.out.println(
                                                            "Balance: ₹"
                                                                    + acc.getBalance());
                                                    System.out.println(
                                                            "Status: "
                                                                    + acc.getStatus());
                                                    System.out.println(
                                                            "----------------------");
                                                }
                                            }

                                            break;
                                        }

                                        // CHECK BALANCE
                                        case "3": {
                                            System.out.print(
                                                    "Enter account number: ");
                                            String accountNumber =
                                                    sc.nextLine().trim();

                                            Account account =
                                                    accountService.checkBalance(
                                                            accountNumber,
                                                            customer.getCustomerId());

                                            System.out.println(
                                                    "Available balance: ₹"
                                                            + account.getBalance());

                                            break;
                                        }

                                        // DEPOSIT MONEY
                                        case "4": {
                                            System.out.print(
                                                    "Enter account number: ");
                                            String accountNumber =
                                                    sc.nextLine().trim();

                                            System.out.print(
                                                    "Enter deposit amount: ₹");
                                            BigDecimal amount =
                                                    new BigDecimal(
                                                            sc.nextLine().trim());

                                            BigDecimal newBalance =
                                                    transactionService.deposit(
                                                            accountNumber,
                                                            customer.getCustomerId(),
                                                            amount);

                                            System.out.println(
                                                    "\nDeposit successful!");
                                            System.out.println(
                                                    "Deposited: ₹" + amount);
                                            System.out.println(
                                                    "Updated balance: ₹"
                                                            + newBalance);

                                            break;
                                        }

                                        // WITHDRAW MONEY
                                        case "5": {
                                            System.out.print(
                                                    "Enter account number: ");
                                            String accountNumber =
                                                    sc.nextLine().trim();

                                            System.out.print(
                                                    "Enter withdrawal amount: ₹");
                                            BigDecimal amount =
                                                    new BigDecimal(
                                                            sc.nextLine().trim());

                                            BigDecimal newBalance =
                                                    transactionService.withdraw(
                                                            accountNumber,
                                                            customer.getCustomerId(),
                                                            amount);

                                            System.out.println(
                                                    "\nWithdrawal successful!");
                                            System.out.println(
                                                    "Withdrawn: ₹" + amount);
                                            System.out.println(
                                                    "Updated balance: ₹"
                                                            + newBalance);

                                            break;
                                        }

                                        // LOGOUT
                                        case "6": {
                                            loggedIn = false;
                                            System.out.println(
                                                    "You have logged out.");
                                            break;
                                        }

                                        default:
                                            System.out.println(
                                                    "Invalid option. Please try again.");
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println(
                                            "Invalid amount. Enter a number "
                                                    + "such as 500 or 1250.50.");

                                } catch (IllegalArgumentException e) {
                                    System.out.println(
                                            "Error: " + e.getMessage());
                                }
                            }

                            break;
                        }

                        // EXIT APPLICATION
                        case "3": {
                            running = false;
                            System.out.println(
                                    "Thank you for using our Banking System!");
                            break;
                        }

                        default:
                            System.out.println(
                                    "Invalid option. Please try again.");
                    }

                } catch (SQLException e) {

                    if (isDuplicateEntry(e)) {
                        System.out.println(
                                "That username or email is already registered. "
                                        + "Please use a different one.");
                    } else {
                        System.out.println(
                                "Database error: " + e.getMessage());
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Registration or input error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Unexpected error: " + e.getMessage());
        }
    }

    private static boolean isDuplicateEntry(SQLException e) {
        // MySQL error code 1062 indicates a duplicate UNIQUE value.
        return e.getErrorCode() == 1062;
    }
}
