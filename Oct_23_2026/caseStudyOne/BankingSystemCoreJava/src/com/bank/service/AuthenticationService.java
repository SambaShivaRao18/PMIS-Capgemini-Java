
package com.bank.service;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;
import com.bank.util.PasswordUtil;

import java.sql.SQLException;

public class AuthenticationService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public Customer login(String username, String password)
            throws SQLException {

        if (username == null || username.isBlank()
                || password == null || password.isEmpty()) {
            return null;
        }

        Customer customer = customerDAO.findByUsername(username.trim());

        if (customer == null) {
            return null;
        }

        boolean valid = PasswordUtil.verifyPassword(
                password, customer.getPasswordHash());

        return valid ? customer : null;
    }
}
