package com.revature.bankingapp.dao;

import java.math.BigDecimal;

public class TransferDAO {
    private int id;
    private String accountTypeTo;
    private String accountTypeFrom;
    private BigDecimal balance;

    public TransferDAO() {
    }

    public TransferDAO(int id, String accountTypeTo, String accountTypeFrom, BigDecimal balance) {
        this.id = id;
        this.accountTypeTo = accountTypeTo;
        this.accountTypeFrom = accountTypeFrom;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountTypeTo() {
        return accountTypeTo;
    }

    public void setAccountTypeTo(String accountTypeTo) {
        this.accountTypeTo = accountTypeTo;
    }

    public String getAccountTypeFrom() {
        return accountTypeFrom;
    }

    public void setAccountTypeFrom(String accountTypeFrom) {
        this.accountTypeFrom = accountTypeFrom;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
