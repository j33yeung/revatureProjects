package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.dao.BankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * POJO model for CustomerBankUser - Created by: Jason Yeung - Edited on: April 19, 2022
 */
public class CustomerBankUser extends BankUser {

    List<BankAccount> accounts;
    private boolean lineOfCredit;
    private final int creditScore;
    private Random ran = new Random();

    /**
     * No args constructor
     */
    public CustomerBankUser() {
        super();
        accounts = new ArrayList<BankAccount>();
        lineOfCredit = false;
        creditScore = ran.nextInt(1000);
    }

    /**
     * Bank user will have a 4-parameter overloaded constructor as follows:
     * <p>
     *     The id, username, password, and full name are overloaded from its super, BankUser class
     *     @param id The id of the user to differentiate user from another
     *     @param userName The username of the bank user required for login
     *     @param password The password associated to the bank user for login
     *     @param fullName The full name of the bank user
     * </p>
     */
    public CustomerBankUser(int id, String userName, String password, String fullName) {
        super(id, userName, password, fullName);
        accounts = new ArrayList<BankAccount>();
        lineOfCredit = false;
        creditScore = ran.nextInt(1000);
    }

    /**
     * Bank user will have a 5-parameter overloaded constructor as follows:
     * <p>
     *     The id, username, password, and full name are overloaded from its super, BankUser class
     *     @param id The id of the user to differentiate user from another
     *     @param userName The username of the bank user required for login
     *     @param password The password associated to the bank user for login
     *     @param fullName The full name of the bank user
     *     @param accounts The list of bank accounts owned by the bank user
     * </p>
     */
    public CustomerBankUser(int id, String userName, String password, String fullName, List<BankAccount> accounts) {
        super(id, userName, password, fullName);
        this.accounts = accounts;
        lineOfCredit = false;
        creditScore = ran.nextInt(1000);
    }

    /**
     * Bank user will have a 6-parameter overloaded constructor as follows:
     * <p>
     *     The id, username, password, and full name are overloaded from its super, BankUser class
     *     @param id The id of the user to differentiate user from another
     *     @param userName The username of the bank user required for login
     *     @param password The password associated to the bank user for login
     *     @param fullName The full name of the bank user
     *     @param accounts The list of bank accounts owned by the bank user
     *     @param role The role of the bank user ('c' for customer, 'e' for employee)
     * </p>

     */
    public CustomerBankUser(int id, String userName, String password, String fullName, List<BankAccount> accounts, char role) {
        super(id, userName, password, fullName, role);
        this.accounts = accounts;
        lineOfCredit = false;
        creditScore = ran.nextInt(1000); //when initialized, creditScore will be randomized
    }

    /**
     * Getter and setter for accounts
     */
    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    /**
     * Getter and setter for lineOfCredit
     */
    public boolean getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(boolean lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    /**
     * Getter for creditScore (only way to set creditScore is through EmployeeBankUserServiceImpl.reviewCreditRequest)
     */
    public int getCreditScore() {
        return creditScore;
    }

    /**
     * Overridden equals function
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBankUser that = (CustomerBankUser) o;
        return Objects.equals(accounts, that.accounts);
    }

    /**
     * Overridden hashCode function
     */
    @Override
    public int hashCode() {
        return Objects.hash(accounts);
    }

    /**
     * Overridden toString function
     */
    @Override
    public String toString() {
        return "CustomerBankUser{" +
                "accounts=" + accounts +
                '}';
    }
}
