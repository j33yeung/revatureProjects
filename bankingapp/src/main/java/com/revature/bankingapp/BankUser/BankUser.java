package com.revature.bankingapp.BankUser;

import java.util.Objects;

/**
 * POJO model for bank user - Created by: Jason Yeung - Edited on: April 19, 2022
 */

public class BankUser {

    private int id;
    private String userName;
    private String password;
    private String fullName;
    private char role;

    /**
     * No-args constructor
     */
    public BankUser() {
    }

    /**
     * Bank user will have a 4-parameter overloaded constructor as follows:
     * @param id The id of the user to differentiate user from another
     * @param userName The username of the bank user required for login
     * @param password The password associated to the bank user for login
     * @param fullName The full name of the bank user
     */
    public BankUser(int id, String userName, String password, String fullName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        role = 'c'; //c for customer, e for employee
    }

    /**
     * Bank will have a 5-parameter overloaded constructor as follows:
     * @param id The id of the user to differentiate user from another
     * @param userName The username of user required for login
     * @param password The password associated to the bank user for login
     * @param fullName The full name of bank user
     * @param role The role of bank user to differentiate user or employee ('c' for customer, 'e' for employee)
     */
    public BankUser(int id, String userName, String password, String fullName, char role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    /**
     * Getter and setter for id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter and setter for userName
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter and setter for password
     */
    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter and setter for fullName
     */
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter and setter for role
     */
    public char getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = role;
    }

    /**
     * Overridden equals function
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankUser bankUser = (BankUser) o;
        return id == bankUser.id && userName.equals(bankUser.userName) && password.equals(bankUser.password) && fullName.equals(bankUser.fullName);
    }

    /**
     * Overridden hashCode function
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, fullName);
    }

    /**
     * Overridden toString function
     */
    @Override
    public String toString() {
        return "BankUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
