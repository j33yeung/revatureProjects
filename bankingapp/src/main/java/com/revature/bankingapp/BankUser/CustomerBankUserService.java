package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.Exceptions.AccountNotExistException;
import com.revature.bankingapp.Exceptions.BalanceBelowZeroException;
import com.revature.bankingapp.Exceptions.UserNotFoundException;

import java.math.BigDecimal;

/**
 * Interface for CustomerBankUserService - Created by: Jason Yeung - Edited on: April 17, 2022
 * <p>
 *     Interface used to maintain all of CustomerBankUser services - The application will interact with this interface
 *     CustomerBankUserService, but will use the business logic from service class CustomerBankUserServiceImpl (Service
 *     Implementation is useful to decouple the client from the service
 * </p>
 */

public interface CustomerBankUserService {

    char login (CustomerBankUser user, String username, String password) throws UserNotFoundException, IllegalStateException;

    BigDecimal viewBalance(CustomerBankUser user) throws AccountNotExistException;

    void depositMoney(CustomerBankUser user, BigDecimal balance, String accountType) throws AccountNotExistException, BalanceBelowZeroException;

    void withdrawMoney(CustomerBankUser user, BigDecimal balance, String accountType) throws AccountNotExistException, BalanceBelowZeroException;

    void transferMoney(CustomerBankUser user, BigDecimal balance, String accountTo, String accountFrom) throws AccountNotExistException, BalanceBelowZeroException;

    void applyForLOC(CustomerBankUser user);
}