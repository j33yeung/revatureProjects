package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.Main;
import com.revature.bankingapp.dao.BankAccount;
import com.revature.bankingapp.Connector.DBConnector;
import com.revature.bankingapp.Exceptions.AccountNotExistException;
import com.revature.bankingapp.Exceptions.BalanceBelowZeroException;
import com.revature.bankingapp.Repository.BankAccountRepository;
import com.revature.bankingapp.Exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service Implementation of CustomerBankUser - Created by: Jason Yeung - Edited on: April 19, 2022
 */

public class CustomerBankUserServiceImpl implements CustomerBankUserService {

    final Logger logger = LoggerFactory.getLogger(Main.class);
//    private BankAccountRepository repo;
//    private DBConnector dbConnector;

//    public CustomerBankUserServiceImpl(BankAccountRepository repo) {
//        this.repo = repo;
//    }

    public CustomerBankUserServiceImpl() {
    }

    /**
     * Login Authentication to verify bank user has correct credentials to access services
     * @param username username associated to bank user
     * @param password password associated to bank user
     * @return user if correct credentials are added
     * @throws UserNotFoundException if user entered incorrect credentials (i.e. wrong username or password)
     * @throws IllegalStateException if user entered null for either username or password
     */
    @Override
    public char login(CustomerBankUser user, String username, String password) throws UserNotFoundException, IllegalStateException {

        //Null check - Check if username or password is null
        if(username == null || password == null) {
            throw new IllegalStateException("Username or password cannot be null");
        }

        //If username and password match the parameters, return user (login successful)
        if (user.getUserName().equals(username) && user.getPassword().equals(password)){
            logger.debug("username and password match");
            return user.getRole();
        }

        //If user still not found after the loop, it means username or password was incorrect
        throw new UserNotFoundException("User not found");
    }

    /**
     * viewBalance - A method used to retrieve total balance across all bank accounts for a particular user
     * @param user customer bank user object whose balance will be read
     * @return BigDecimal if at least one account exist
     * @throws AccountNotExistException if user does not own any accounts or have null accounts
     */
    @Override
    public BigDecimal viewBalance(CustomerBankUser user) throws AccountNotExistException {

        //Null check
        if( user.getAccounts() == null || user.getAccounts().isEmpty()){
            throw new AccountNotExistException("The account does not exist");
        }

        /**
         * totalBalance (variable that will collect balance from all accounts) will go through a list of all the
         * banks accounts for a specific user, and add them all up before returning itself
         */
        BigDecimal totalBalance = new BigDecimal("0.00");
        for(int i = 0; i < user.getAccounts().size(); i++){
            totalBalance = totalBalance.add(user.getAccounts().get(i).getBalance());
        }

        logger.debug("balance: "+totalBalance);
        return totalBalance;
    }

    /**
     * depositMoney - A method used to allow customerBankUsers to deposit money into one of their accounts
     * @param user customerBankUser object that will deposit an amount of money
     * @param amount amount of money deposited into an account
     * @param accountType the name/type of the account (ex. chequing, saving, TFSA, etc.)
     * @throws AccountNotExistException if user does not have any accounts or null accounts
     * @throws BalanceBelowZeroException if deposit amount is below zero (since you cannot deposit negative money)
     */
    @Override
    public void depositMoney(CustomerBankUser user, BigDecimal amount, String accountType) throws AccountNotExistException, BalanceBelowZeroException {

        BankAccount bankAccount = null;

        /**
         * In the list of bank accounts, if one of the accounts' name match the accountType parameter, the user will
         * deposit money into that account
         */
        for(int i=0; i<user.getAccounts().size(); i++){
            if(user.getAccounts().get(i).getAccountType().equals(accountType)){
                bankAccount = user.getAccounts().get(i);
                break;
            }
        }


        //Throw exceptions if bank account is null or deposit amount if below 0; otherwise, add amount to balance
        if(bankAccount == null){
            throw new AccountNotExistException("The account does not exist");
        }
        else if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new BalanceBelowZeroException("The deposited amount cannot be below zero");
        } else{
            bankAccount.setBalance(bankAccount.getBalance().add(amount));
            logger.debug("new balance has been applied");
        }
    }

    /**
     * withdrawMoney - A method used to allow customerBankUsers to withdraw money from one of their accounts
     * @param user customerBankUser object that will withdraw an amount of money
     * @param amount amount of money withdrew from an account
     * @param accountType the name/type of the account (ex. chequing, saving, TFSA, etc.)
     * @throws AccountNotExistException if user does not have any accounts or null accounts
     * @throws BalanceBelowZeroException if withdraw amount is below zero (since you cannot withdraw negative money) or
     * if withdraw amount exceeds your account balance (ex. you can't withdraw $20 from an account balance of $15)
     */
    @Override
    public void withdrawMoney(CustomerBankUser user, BigDecimal amount, String accountType) throws AccountNotExistException, BalanceBelowZeroException {

        BankAccount bankAccount = null;

        /**
         * In the list of bank accounts, if one of the accounts' name match the accountType parameter, the user will
         * withdraw money from that account
         */
        for(int i=0; i<user.getAccounts().size(); i++){
            if(accountType != null && user.getAccounts().get(i).getAccountType().equals(accountType)){
                bankAccount = user.getAccounts().get(i);
                break;
            }
        }

        /**
         * Throw exceptions if bank account is null, withdraw amount if below 0, or withdraw amount exceed
         * balance amount; otherwise, subtract amount from balance
         */
        if(bankAccount == null){
            throw new AccountNotExistException("The account does not exist");
        }
        else if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new BalanceBelowZeroException("The withdraw amount cannot be below zero");
        }
        else if(bankAccount.getBalance().compareTo(amount) < 0){
            throw new BalanceBelowZeroException("Withdraw amount cannot exceed account balance");
        } else{
            bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
            logger.debug("new balance has been applied");
        }
    }

    /**
     * transferMoney - A method used to allow customerBankUsers to transfer money from one of their accounts to another
     * @param user customerBankUser object that will transfer an amount of money
     * @param amount amount of money transferred from one account to another
     * @param accountTo the name/type of the account which will receive the transfer
     * @param accountFrom the name/type of the account which will send the transfer
     * @throws AccountNotExistException if user does not have any accounts or null accounts
     * @throws BalanceBelowZeroException if transfer amount is below zero (since you cannot transfer negative money) or
     * if the transfer amount exceeds the outgoing account's balance (ex. you can't transfer $10 from an account that
     * only has $5)
     */
    @Override
    public void transferMoney(CustomerBankUser user, BigDecimal amount, String accountTo, String accountFrom) throws AccountNotExistException, BalanceBelowZeroException {
        BankAccount bankAccountTo = null, bankAccountFrom = null;
        boolean to = false, from = false;

        /**
         * In the list of bank accounts, if accountTo and accountFrom both match the parameters accountTo and
         * accountFrom respectively, the user will transfer money from the outgoing account (accountFrom) to the
         * receiving account (accountTo)
         */
        if(accountTo != null && accountFrom != null){
            for(int i=0; i<user.getAccounts().size(); i++){
                if(user.getAccounts().get(i).getAccountType().equals(accountTo)){
                    to = true;
                    bankAccountTo = user.getAccounts().get(i);
                    break;
                }
            }
            for(int i=0; i<user.getAccounts().size(); i++){
                if(user.getAccounts().get(i).getAccountType().equals(accountFrom)){
                    from = true;
                    bankAccountFrom = user.getAccounts().get(i);
                    break;
                }
            }
        }

        /**
         * Throw exceptions if bank account is null, transfer amount if below 0, or transfer amount exceed
         * the outgoing account's balance; otherwise, subtract amount from outgoing account to receiving account
         */
        if(bankAccountTo == null || bankAccountFrom == null){
            throw new AccountNotExistException("The account does not exist");
        }
        else if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new BalanceBelowZeroException("The transfer amount cannot be below zero");
        }
        else if(bankAccountFrom.getBalance().compareTo(amount) < 0){
            throw new BalanceBelowZeroException("Insufficient funds to transfer");
        } else {
            bankAccountFrom.setBalance(bankAccountFrom.getBalance().subtract(amount));
            bankAccountTo.setBalance(bankAccountTo.getBalance().add(amount));
            logger.debug("new balances has been applied to both accounts");
        }
    }

    /**
     * applyForLOC - A method used to apply for a line of credit. Quite simply, the customerBankUser will be able to
     * apply for a LOC with this method, and turn the "lineOfCredit" reference variable to true.
     * <p>
     *     Later, in the employeeBankUserServiceImpl class,an employeeBankUser will then take the LOC application,
     *     give a credit rating, and turn the "lineOfCredit" reference variable back to false.
     * </p>
     * @param user customerBankUser will have a boolean reference variable indicating if he has applied or not applied
     *             for a line of credit
     */
    @Override
    public void applyForLOC(CustomerBankUser user) {
        user.setLineOfCredit(true);
        logger.debug("new application for LOC has been sent");
    }
}