package com.revature.bankingapp.dao;

public class LoginDAO {
    private String username;
    private String password;
    private int customer_id;

    public LoginDAO() {
    }

    public LoginDAO(String username, String password, int customer_id) {
        this.username = username;
        this.password = password;
        this.customer_id = customer_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
