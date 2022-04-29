package com.revature.bankingapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BankAccount {

    private int id;
    private String accountType;
    private BigDecimal balance;
    final Logger logger = LoggerFactory.getLogger(BankAccount.class);

    public BankAccount() {
    }

    public BankAccount(int id, String accountType) {
        this.id = id;
        this.accountType = accountType;
        this.balance = new BigDecimal("0.00");
    }

    public BankAccount(int id, String accountType, BigDecimal balance) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        logger.debug("balance is now 30");
    }

//    public static class EmployeeBankUser extends BankUser {
//
//
//
//        /***
//         * Bank user will have multiple properties that the BankUserService will access and manipulate
//         * @param userName - The username of the bank user required for login
//         * @param password - The password associated to the bank user for login
//         * @param fullName -  The full name of the bank user
//         * @param balance - The bank user's current total balance across all accounts
//         * @param role
//         */
//        public EmployeeBankUser(String userName, String password, String fullName, BigDecimal balance, char role) {
//            super(userName, password, fullName, balance, role);
//        }
//    }
//
//    public static class CustomerUser extends BankUser {
//
//        private int customerId;
//        private List<BankAccount> accounts;
//
//        /***
//         * Bank user will have multiple properties that the BankUserService will access and manipulate
//         * @param userName - The username of the bank user required for login
//         * @param password - The password associated to the bank user for login
//         * @param fullName -  The full name of the bank user
//         * @param balance - The bank user's current total balance across all accounts
//         * @param role
//         */
//        public CustomerUser(String userName, String password, String fullName, BigDecimal balance, char role, int customerId, List<BankAccount> accounts) {
//            super(userName, password, fullName, balance, role);
//            this.customerId = customerId;
//            this.accounts = accounts;
//        }
//
//        public int getCustomerId() {
//            return customerId;
//        }
//
//        public void setCustomerId(int customerId) {
//            this.customerId = customerId;
//        }
//
//        public List<BankAccount> getAccounts() {
//            return accounts;
//        }
//
//        public void setAccounts(List<BankAccount> accounts) {
//            this.accounts = accounts;
//        }
//    }
}
