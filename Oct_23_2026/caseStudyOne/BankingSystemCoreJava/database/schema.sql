
CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

-- 1. Customers
CREATE TABLE IF NOT EXISTS customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Accounts: one customer can own multiple accounts
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_accounts_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT chk_account_balance CHECK (balance >= 0)
);

-- 3. Beneficiaries: one customer can register multiple beneficiaries
CREATE TABLE IF NOT EXISTS beneficiaries (
    beneficiary_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    beneficiary_name VARCHAR(100) NOT NULL,
    beneficiary_account_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_beneficiaries_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- 4. Transactions: one transaction can involve multiple accounts
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_transaction_amount CHECK (amount > 0)
);

-- 5. Junction table: accounts <-> transactions
CREATE TABLE IF NOT EXISTS account_transactions (
    account_id INT NOT NULL,
    transaction_id BIGINT NOT NULL,
    entry_type VARCHAR(10) NOT NULL,
    PRIMARY KEY (account_id, transaction_id),
    CONSTRAINT fk_at_account
        FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_at_transaction
        FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);
