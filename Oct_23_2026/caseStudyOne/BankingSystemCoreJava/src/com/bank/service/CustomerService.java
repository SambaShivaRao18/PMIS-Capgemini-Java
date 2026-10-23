
package com.bank.service;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.util.PasswordUtil;

import java.sql.SQLException;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();


    public int registerCustomer(
            String fullName,
            String email,
            String username,
            String password) throws SQLException {

        if (fullName == null || fullName.isBlank()
                || email == null || email.isBlank()
                || username == null || username.isBlank()
                || password == null || password.length() < 8) {

            throw new IllegalArgumentException(
                    "Enter all required details. Password must be at least 8 characters.");
        }

        String passwordHash = PasswordUtil.hashPassword(password);

        Customer customer = new Customer(
                fullName.trim(),
                email.trim(),
                username.trim(),
                passwordHash
        );

        return customerDAO.registerCustomer(customer);
    }
}
