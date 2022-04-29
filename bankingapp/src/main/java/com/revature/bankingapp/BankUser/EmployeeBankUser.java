package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.dao.BankAccount;

/**
 * POJO model for CustomerBankUser - Created by: Jason Yeung - Edited on: April 19, 2022
 */
public class EmployeeBankUser extends BankUser {

    /**
     * No args constructor
     */
    public EmployeeBankUser() {
    }

    /**
     * Bank user will have a 4-parameter overloaded constructor as follows:
     * <p>
     *     The id, username, password, and full name are overloaded from its super, BankUser class
     *     @param id The id of the employee to differentiate user from another
     *     @param userName The username of the employee required for login
     *     @param password The password associated to the employee for login
     *     @param fullName The full name of the employee
     * </p>
     */
    public EmployeeBankUser(int id, String userName, String password, String fullName, BankAccount accounts) {
        super(id, userName, password, fullName);
    }

    /**
     * Bank user will have a 5-parameter overloaded constructor as follows:
     * <p>
     *     The id, username, password, and full name are overloaded from its super, BankUser class
     *     @param id The id of the employee to differentiate user from another
     *     @param userName The username of the employee required for login
     *     @param password The password associated to the employee for login
     *     @param fullName The full name of the employee
     *     @param role The role of the bank user ('c' for customer, 'e' for employee)
     * </p>
     */
    public EmployeeBankUser(int id, String userName, String password, String fullName, char role) {
        super(id, userName, password, fullName, role);
    }
}
