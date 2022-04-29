package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.Exceptions.AccountNotExistException;
import com.revature.bankingapp.Exceptions.ApplicationNotSentException;
import com.revature.bankingapp.Exceptions.DuplicateFoundException;
import com.revature.bankingapp.Exceptions.UserNotFoundException;

/**
 * Interface for CustomerBankUserService - Created by: Jason Yeung - Edited on: April 17, 2022
 */
public interface EmployeeBankUserService {

    String reviewCreditRequest(CustomerBankUser user) throws AccountNotExistException, ApplicationNotSentException;
    void createNewAccount(CustomerBankUser user, String newAccount) throws UserNotFoundException, DuplicateFoundException;

}
