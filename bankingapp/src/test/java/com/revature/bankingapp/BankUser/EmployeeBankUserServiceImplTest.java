package com.revature.bankingapp.BankUser;

import com.revature.bankingapp.dao.BankAccount;
import com.revature.bankingapp.Exceptions.AccountNotExistException;
import com.revature.bankingapp.Exceptions.ApplicationNotSentException;
import com.revature.bankingapp.Exceptions.DuplicateFoundException;
import com.revature.bankingapp.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBankUserServiceImplTest {

    private List<CustomerBankUser> users;
    private List<BankAccount> accounts;
    private CustomerBankUserService customerBankUser;
    private EmployeeBankUserService employeeBankUser;


    @BeforeEach
    public void initEachTest() {
//        System.out.println("Initializing before test");
        customerBankUser = new CustomerBankUserServiceImpl();
        employeeBankUser = new EmployeeBankUserServiceImpl();
        users = new ArrayList<CustomerBankUser>();
        accounts = new ArrayList<BankAccount>();

        accounts.add(new BankAccount(1, "chequing", new BigDecimal("25.00")));
        accounts.add(new BankAccount(2, "savings", new BigDecimal("50.00")));

        users.add(new CustomerBankUser(1, "j33yeung", "pass123", "Jason Yeung", accounts));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /***
     * ReviewCreditRequests JUnit Tests
     * The following three tests validate if reviewCreditRequests is working as intended:
     * An account exist to be credit reviewed, a LOC application was sent, and check if a credit review is conducted when successful
     */
    @Test
    void shouldReceiveCreditRatingWithRespectiveCreditScore() {

        String creditReview;
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        customerBankUser.applyForLOC(user);
        creditReview = employeeBankUser.reviewCreditRequest(user);

        if(user.getCreditScore() < 300){
            Assertions.assertEquals("Due to your bad credit, you are denied a line of credit", creditReview, "Method didn't throw with null username");
        }
        else if(user.getCreditScore() <600){
            Assertions.assertEquals("Due to your poor credit, your line of credit needs a review and interest will be set at 20%", creditReview, "Method didn't throw with null username");
        }
        else if(user.getCreditScore() <900){
            Assertions.assertEquals("Due to your good credit, your line of credit is automatically approved and interest will be set at 10%", creditReview, "Method didn't throw with null username");
        }
        else if(user.getCreditScore() >= 900 || user.getCreditScore() == 100){
            Assertions.assertEquals("Due to your excellent credit, your line of credit is automatically approved and interest will be set at 7%", creditReview, "Method didn't throw with null username");
        }
        else{
            Assertions.assertEquals("You have no accounts with this bank, or you have not applied for a line of credit", creditReview, "Review credit request successful");
        }
    }

    @Test
    void shouldThrowAccountNotExistExceptionWithEmptyCustomer() {

        CustomerBankUser user2 = new CustomerBankUser();

        AccountNotExistException ex = Assertions.assertThrows(AccountNotExistException.class, () -> {
            employeeBankUser.reviewCreditRequest(user2);
        });
        Assertions.assertEquals("You have no accounts with this bank", ex.getMessage(), "Credit review failed due to no account");
    }

    @Test
    void shouldThrowApplicationNotSentExceptionWhenNotApplyForLineOfCredit() {

        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();

        ApplicationNotSentException ex = Assertions.assertThrows(ApplicationNotSentException.class, () -> {
            employeeBankUser.reviewCreditRequest(user);
        });
        Assertions.assertEquals("You have not applied for a line of credit", ex.getMessage(), "Credit review failed due to no line of credit application sent");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /***
     * CreateNewAccount JUnit Tests
     * The following three tests validate if createNewAccount is working as intended:
     * No account is created when user is null, no account is created when duplicate account IDs is found,and check
     * if one account is precisely added to the end of the list of bankAccounts when successfully executed
     */

    @Test
    void shouldCreateNewAccount() {

        String newAccountName = "TFSA";
        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();
        int accountsCountBefore = user.getAccounts().size();
        int accountsCountAfter = 0;

        employeeBankUser.createNewAccount(user, newAccountName);
        accountsCountAfter = user.getAccounts().size();

        /***
         * Check to see if new account was added by:
         * 1) if account is appended in the back (by checking if it has the largest ID)
         * 2) if only one additional account is created after createNewAccount successfully executes
         */
        Assertions.assertEquals(1, (accountsCountAfter - accountsCountBefore), "Precisely one account was created after one successful execution");
        Assertions.assertEquals(3, user.getAccounts().get(accountsCountAfter-1).getId(), "New account was appended at the back of the list");
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIsNull() {

        String newAccountName = "TFSA";

        UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () -> {
            employeeBankUser.createNewAccount(null, newAccountName);
        });

        Assertions.assertEquals("Cannot attach account to a non-existing user", ex.getMessage(), "Account creation failed due to null user");
    }

    @Test
    void shouldCheckAllUserAccountsHaveDifferentIDsWhenCreatingNewAccount() {

        accounts.add(new BankAccount(3, "TFSA", new BigDecimal("25.00")));
        accounts.add(new BankAccount(3, "RRSP", new BigDecimal("50.00")));

        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        DuplicateFoundException ex = Assertions.assertThrows(DuplicateFoundException.class, () -> {
            employeeBankUser.createNewAccount(user, "Smart Account");
        });

        Assertions.assertEquals("Cannot create new account until duplicate account IDs are resolved", ex.getMessage(), "Duplicate IDs found in user's list of bankAccounts");
    }

    @Test
    void shouldCheckAllUserAccountsHaveDifferentAccountTypesWhenCreatingNewAccount() {

        accounts.add(new BankAccount(3, "TFSA", new BigDecimal("25.00")));
        accounts.add(new BankAccount(4, "TFSA", new BigDecimal("50.00")));

        CustomerBankUser user = users.stream().filter(g -> g.getUserName().equals("j33yeung")).findFirst().get();


        DuplicateFoundException ex = Assertions.assertThrows(DuplicateFoundException.class, () -> {
            employeeBankUser.createNewAccount(user, "Smart Account");
        });

        Assertions.assertEquals("Cannot create new account until duplicate account names are resolved", ex.getMessage(), "Duplicate IDs found in user's list of bankAccounts");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
