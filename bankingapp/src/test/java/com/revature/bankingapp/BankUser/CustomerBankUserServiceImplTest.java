package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.dao.BankAccount;
import com.revature.bankingapp.Exceptions.AccountNotExistException;
import com.revature.bankingapp.Exceptions.BalanceBelowZeroException;
import com.revature.bankingapp.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerBankUserServiceImplTest {

    private List<CustomerBankUser> users;
    private List<BankAccount> accounts;
    private CustomerBankUserService customerBankUser;


    @BeforeEach
    public void initEachTest() {
        customerBankUser = new CustomerBankUserServiceImpl();
        users = new ArrayList<CustomerBankUser>();
        accounts = new ArrayList<BankAccount>();

        accounts.add(new BankAccount(1, "chequing", new BigDecimal("25.00")));
        accounts.add(new BankAccount(2, "savings", new BigDecimal("50.00")));

        users.add(new CustomerBankUser(1, "j33yeung", "pass123", "Jason Yeung", accounts));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * Login System JUnit Tests
     * These three tests are used to check if login system works.
     */
    @Test
    void shouldThrowIllegalStateException() {
        String username = null;
        String password = null;

        IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            // the test code
            customerBankUser.login(users.get(0), null, null);
        });

        Assertions.assertEquals("Username or password cannot be null", ex.getMessage(), "Method didn't throw with both null parameters");

        ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            // the test code
            customerBankUser.login(users.get(0), "j33yeung", null);
        });

        Assertions.assertEquals("Username or password cannot be null", ex.getMessage(), "Method didn't throw with null password");

        ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            // the test code
            customerBankUser.login(users.get(0), null, "pass123");
        });

        Assertions.assertEquals("Username or password cannot be null", ex.getMessage(), "Method didn't throw with null username");
    }

    @Test
    void shouldReturnUserWithCorrectCredentials() {
        String username = "j33yeung";
        String password = "pass123";

        char user = customerBankUser.login(users.get(0), username, password);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(password, users.get(0).getPassword(), "User password doesn't match");
        Assertions.assertEquals(username, users.get(0).getUserName(), "Username doesn't match");
    }

    @Test
    void shouldThrowUserNotFoundWithIncorrectCredentials() {
        String username1 = "august.duet";
        String password1 = "password123";

        String username2 = "august.due";
        String password2 = "p@$$w0rd123";

        // correct username, wrong password
        UserNotFoundException uex = Assertions.assertThrows(UserNotFoundException.class, () -> {
            customerBankUser.login(users.get(0), username1, password1);
        });

        Assertions.assertEquals("User not found", uex.getMessage(), "Return user with incorrect password");

        // wrong username, correct password
        uex = Assertions.assertThrows(UserNotFoundException.class, () -> {
            customerBankUser.login(users.get(0), username2, password2);
        });
        Assertions.assertEquals("User not found", uex.getMessage(), "Return user with incorrect username");

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * ViewBalance JUnit Test
     * The following two tests just simply test if the total balance across all accounts is correct, and if any accounts
     * are null, an AccountNotExistException will be thrown
     */
    @Test
    void shouldViewCorrectBalance(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        BigDecimal totalBalance = customerBankUser.viewBalance(user);

        Assertions.assertEquals(new BigDecimal("75.00"), totalBalance, "The balance shows the proper number");
    }

    @Test
    void shouldThrowAccountNotExistExceptionWhenAccountIsNull(){
        //New user will be a CustomerBankUser with a null bank account
        users.add(new CustomerBankUser(2, null, null, null, null));
        CustomerBankUser user = users.get(1);


        AccountNotExistException ex = Assertions.assertThrows(AccountNotExistException.class, () -> {
            customerBankUser.viewBalance(user);
        });

        Assertions.assertEquals("The account does not exist", ex.getMessage(), "Deposited amount is negative");
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * DepositMoney JUnit Tests
     * The following three tests validate if depositMoney is working as intended:
     * No negative deposits, check if account does not exist, and check if deposit amount is correctly applied when successful
     */
    @Test
    void shouldDepositMoney(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        customerBankUser.depositMoney(user, new BigDecimal("24.00"), "chequing");

        Assertions.assertEquals(new BigDecimal("49.00"), users.get(0).getAccounts().get(0).getBalance());

    }

    @Test
    void shouldThrowBalanceBelowZeroExceptionWithInvalidDeposit(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        BalanceBelowZeroException ex = Assertions.assertThrows(BalanceBelowZeroException.class, () -> {
            customerBankUser.depositMoney(user, new BigDecimal("-24.00"), "chequing");
        });

        Assertions.assertEquals("The deposited amount cannot be below zero", ex.getMessage(), "Deposited amount is negative");
    }

    @Test
    void shouldThrowAccountNotExistExceptionWithInvalidDeposit(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        AccountNotExistException ex = Assertions.assertThrows(AccountNotExistException.class, () -> {
            customerBankUser.depositMoney(user, new BigDecimal("24.00"), "mutualFund");
        });

        Assertions.assertEquals("The account does not exist", ex.getMessage(), "Deposited amount is negative");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * WithdrawMoney JUnit Tests
     * The following four tests validate if withdrawMoney is working as intended:
     * No negative withdraws, check if account does not exist, check if withdraw amount exceed account balance, and
     * check if withdraw amount is correctly applied when successful
     */
    @Test
    void shouldWithdrawMoney(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        customerBankUser.withdrawMoney(user, new BigDecimal("24.00"), "chequing");

        Assertions.assertEquals(new BigDecimal("1.00"), users.get(0).getAccounts().get(0).getBalance());
    }

    @Test
    void shouldThrowBalanceBelowZeroExceptionWithNegativeWithdraw(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        BalanceBelowZeroException ex = Assertions.assertThrows(BalanceBelowZeroException.class, () -> {
            customerBankUser.withdrawMoney(user, new BigDecimal("-24.00"), "chequing");
        });

        Assertions.assertEquals("The withdraw amount cannot be below zero", ex.getMessage(), "Deposited amount is negative");
    }

    @Test
    void shouldThrowBalanceBelowZeroExceptionWithInvalidWithdrawAmount(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        BalanceBelowZeroException ex = Assertions.assertThrows(BalanceBelowZeroException.class, () -> {
            customerBankUser.withdrawMoney(user, new BigDecimal("26.00"), "chequing");
        });

        Assertions.assertEquals("Withdraw amount cannot exceed account balance", ex.getMessage(), "Withdraw amount is too high");
    }

    @Test
    void shouldThrowAccountNotExistExceptionWithInvalidWithdraw(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();
        accounts.add(null);


        AccountNotExistException ex = Assertions.assertThrows(AccountNotExistException.class, () -> {
            customerBankUser.withdrawMoney(user, new BigDecimal("24.00"), null);
        });

        Assertions.assertEquals("The account does not exist", ex.getMessage(), "Deposited amount is negative");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * TransferMoney JUnit Tests
     * The following four tests validate if transferMoney is working as intended:
     * No negative transfer amounts, check if account does not exist, check if transfer amount exceed the sending
     * account balance, and check if transfer amount is correctly applied when successful
     */
    @Test
    void shouldTransferMoney(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        customerBankUser.transferMoney(user, new BigDecimal("5.00"), "chequing", "savings");

        Assertions.assertEquals(new BigDecimal("30.00"), users.get(0).getAccounts().get(0).getBalance());
        Assertions.assertEquals(new BigDecimal("45.00"), users.get(0).getAccounts().get(1).getBalance());
    }

    @Test
    void shouldThrowBalanceBelowZeroExceptionWithNegativeAmountTransfer(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        BalanceBelowZeroException ex = Assertions.assertThrows(BalanceBelowZeroException.class, () -> {
            customerBankUser.transferMoney(user, new BigDecimal("-24.00"), "chequing", "savings");
        });

        Assertions.assertEquals("The transfer amount cannot be below zero", ex.getMessage(), "Transfer amount is negative");
    }

    @Test
    void shouldThrowBalanceBelowZeroExceptionWithInvalidTransferAmount(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        BalanceBelowZeroException ex = Assertions.assertThrows(BalanceBelowZeroException.class, () -> {
            customerBankUser.transferMoney(user, new BigDecimal("26.00"), "savings", "chequing");
        });

        Assertions.assertEquals("Insufficient funds to transfer", ex.getMessage(), "Transfer amount is too high");
    }

    @Test
    void shouldThrowAccountNotExistExceptionWithInvalidTransfer(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();
        accounts.add(null);


        AccountNotExistException ex = Assertions.assertThrows(AccountNotExistException.class, () -> {
            customerBankUser.transferMoney(user, new BigDecimal("24.00"), null, null);
        });

        Assertions.assertEquals("The account does not exist", ex.getMessage(), "Deposited amount is negative");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * TransferMoney JUnit Tests
     * The following test validate if the application for line of credit works as intended
     */
    @Test
    void shouldApplyForLOC(){
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        customerBankUser.applyForLOC(user);

        Assertions.assertEquals(true, user.getLineOfCredit());
    }



}
