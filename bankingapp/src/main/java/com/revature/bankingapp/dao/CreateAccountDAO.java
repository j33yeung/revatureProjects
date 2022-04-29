package com.revature.bankingapp.dao;

public class CreateAccountDAO {
    private String username;
    private String password;
    private int customer_id;
    private String accountType;

    public CreateAccountDAO() {
    }

    public CreateAccountDAO(String username, String password, int customer_id, String accountType) {
        this.username = username;
        this.password = password;
        this.customer_id = customer_id;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
