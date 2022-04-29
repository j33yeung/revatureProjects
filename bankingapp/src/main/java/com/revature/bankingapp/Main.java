package com.revature.bankingapp;

import com.revature.bankingapp.BankUser.*;
import com.revature.bankingapp.Connector.DBConnector;
import com.revature.bankingapp.Connector.MySQLConnector;
import com.revature.bankingapp.Exceptions.*;
import com.revature.bankingapp.dao.BankAccount;
import com.revature.bankingapp.dao.CreateAccountDAO;
import com.revature.bankingapp.dao.LoginDAO;
import com.revature.bankingapp.dao.TransferDAO;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main (String[] args){

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<CustomerBankUser> users;
        List<EmployeeBankUser> employees;
        List<BankAccount> j33yeung_accounts;
        List<BankAccount> k44yeung_accounts;

        users = new ArrayList<CustomerBankUser>();
        employees = new ArrayList<EmployeeBankUser>();
        j33yeung_accounts= new ArrayList<BankAccount>();
        k44yeung_accounts= new ArrayList<BankAccount>();

        j33yeung_accounts.add(new BankAccount(1, "chequing", new BigDecimal("25.00")));
        j33yeung_accounts.add(new BankAccount(2, "savings", new BigDecimal("50.00")));

        k44yeung_accounts.add(new BankAccount(1, "chequing", new BigDecimal("60.00")));
        k44yeung_accounts.add(new BankAccount(2, "savings", new BigDecimal("20.50")));

        users.add(new CustomerBankUser(1, "j33yeung", "pass123", "Jason Yeung", j33yeung_accounts, 'c'));
        users.add(new CustomerBankUser(2, "k44yeung", "pass456", "Kevin Yeung", k44yeung_accounts, 'c'));
        users.add(new CustomerBankUser(3, "babu", "pass789", "Babu Babu", j33yeung_accounts, 'e'));
        users.add(new CustomerBankUser(4, null, null, null, null));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DBConnector mysqlDB = new MySQLConnector("root", "Ccc12345!!!", "jdbc:mysql://34.130.53.121:3306/BankRepository");
        CustomerBankUserService customerBankUserService = new CustomerBankUserServiceImpl();
        EmployeeBankUserService employeeBankUserService = new EmployeeBankUserServiceImpl();
        Logger logger = LoggerFactory.getLogger(Main.class);

//        Repository<Integer, BankUser> bankAccountRepository = new BankAccountRepository(mysqlDB);
//        CustomerBankUserServiceImpl userService = new CustomerBankUserServiceImpl(bankAccountRepository);

        Javalin app = Javalin.create().start(8080);

        //get user
        app.get("/users/{id}", (ctx) -> {
            logger.debug("Retrieving user:");
            int id = Integer.parseInt(ctx.pathParam("id"));

            CustomerBankUser user = users.get(id-1);

            if(user != null) {
                ctx.status(200);
                logger.debug("User has logged in");
                ctx.contentType("application/json");
                ctx.json(user);
            } else {
                ctx.status(404);
                logger.debug("User was not found");
                ctx.result("user not found");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /***
         * CustomerBankUser Functions
         */

        //login - Login System to verify if credentials are correct
        app.put("/customerbankuser/{id}/login", (ctx) ->{
            ctx.contentType("application/json");
            int id = Integer.parseInt(ctx.pathParam("id"));
            LoginDAO dao = ctx.bodyAsClass(LoginDAO.class);

            try{
               logger.debug("Login startup");
               char user = customerBankUserService.login(users.get(id-1),dao.getUsername(), dao.getPassword());
               ctx.status(200);
               ctx.result("Login successful");
            } catch (UserNotFoundException ex){
               ctx.status(404);
               ctx.result("User not found");
            } catch (IllegalStateException ex){
               ctx.status(400);
               ctx.result("Username or password cannot be null");
            }
        });

        //viewBalance - A method used to help customers view the total balance across all accounts
        app.get("/customerbankuser/{id}/viewbalance", (ctx) ->{
            ctx.contentType("application/json");
            int id = Integer.parseInt(ctx.pathParam("id"));

            try{
                BigDecimal totalBalance = customerBankUserService.viewBalance(users.get(id-1));
                ctx.status(200);
                ctx.json(totalBalance);
            } catch (AccountNotExistException e) {
                ctx.status(404);
                ctx.result("There is no existing account to view balance");
            }
        });

        //depositMoney - A method used to help customers deposit money into one of their accounts
        app.put("/customerbankuser/{id}/depositmoney", (ctx) -> {
            ctx.contentType("application/json");
            BankAccount account = ctx.bodyAsClass(BankAccount.class);
            int id = Integer.parseInt(ctx.pathParam("id"));

            try {
                if (users.get(id - 1).getRole() == 'e') {
                    throw new NotAuthorizedException();
                }
                customerBankUserService.depositMoney(users.get(id - 1), account.getBalance(), account.getAccountType());
                ctx.status(204);
            } catch(NotAuthorizedException e){
                ctx.status(401);
                ctx.json("Not authorized");
            } catch (AccountNotExistException e) {
                ctx.status(404);
                ctx.json("The account does not exist");
            } catch (BalanceBelowZeroException e) {
                ctx.status(400);
                ctx.json("The deposited amount cannot be below zero");
            }
        });

        //withdrawMoney - A method used to help customers withdraw money from one of their accounts
        app.put("/customerbankuser/{id}/withdrawmoney", (ctx) ->{
            ctx.contentType("application/json");
            BankAccount account = ctx.bodyAsClass(BankAccount.class);
            int id = Integer.parseInt(ctx.pathParam("id"));

            try{
                if (users.get(id - 1).getRole() == 'e') {
                    throw new NotAuthorizedException();
                }
                customerBankUserService.withdrawMoney(users.get(id-1), account.getBalance(), account.getAccountType());
                ctx.status(204);
            } catch(NotAuthorizedException e){
                ctx.status(401);
                ctx.json("Not authorized");
            } catch (AccountNotExistException e) {
                ctx.status(404);
                ctx.json("The account does not exist");
            } catch (BalanceBelowZeroException e) {
                ctx.status(400);
                ctx.json("Withdraw amount cannot be negative or exceed account balance");
            }
        });

        //transferMoney - A method used to help customers transfer money from one of their accounts to another if it exist
        app.put("/customerbankuser/{id}/transfermoney/", (ctx) ->{
            ctx.contentType("application/json");
            TransferDAO dao = ctx.bodyAsClass(TransferDAO.class);
            int id = Integer.parseInt(ctx.pathParam("id"));

            try{
                if (users.get(id - 1).getRole() == 'e') {
                    throw new NotAuthorizedException();
                }
                customerBankUserService.transferMoney(users.get(id-1), dao.getBalance(), dao.getAccountTypeTo(), dao.getAccountTypeFrom());
                ctx.status(204);
            } catch(NotAuthorizedException e){
                ctx.status(401);
                ctx.json("Not authorized");
            } catch (AccountNotExistException e) {
                ctx.status(404);
                ctx.json("The account does not exist");
            } catch (BalanceBelowZeroException e) {
                ctx.status(400);
                ctx.json("The transfer amount or the transferring account cannot be below zero");
            }
        });

        //applyForLOC - A method used to help customers apply for line of credit
        app.put("/customerbankuser/{id}/applyforloc/", (ctx) ->{
            ctx.contentType("application/json");
            int id = Integer.parseInt(ctx.pathParam("id"));

            try{
                if (users.get(id - 1).getRole() == 'e') {
                    throw new NotAuthorizedException();
                }
                customerBankUserService.applyForLOC(users.get(id-1));
                ctx.status(204);
            } catch(NotAuthorizedException e){
                ctx.status(401);
                ctx.json("Not authorized");
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /***
         * EmployeeBankUser Functions
         */

        //reviewCreditRequest - A method used to help employees review bank users' line of credits
        app.put("/employeebankuser/{id}/reviewcreditrequest/", (ctx) ->{
            String creditRating;
            ctx.contentType("application/json");
            int id = Integer.parseInt(ctx.pathParam("id"));
            LoginDAO dao = ctx.bodyAsClass(LoginDAO.class);

            try{
                if (customerBankUserService.login(users.get(id - 1), dao.getUsername(), dao.getPassword()) != 'e') {
                    throw new NotAuthorizedException();
                }

                creditRating = employeeBankUserService.reviewCreditRequest(users.get(dao.getCustomer_id() - 1));
                ctx.status(200);
                ctx.result(creditRating);
            } catch (NotAuthorizedException e){
                ctx.json(401);
                ctx.json("You are not authorized to use this functionality");
            } catch (AccountNotExistException e) {
                ctx.status(404);
                ctx.json("You have no accounts with this bank");
            } catch (ApplicationNotSentException e) {
                ctx.status(500);
                ctx.json("You have not applied for a line of credit");
            }
        });

        //createNewAccount - A method used to help employees create new accounts for bank users
        app.put("/employeebankuser/{id}/createaccount/", (ctx) -> {
            String creditRating = null;
            ctx.contentType("application/json");
            int id = Integer.parseInt(ctx.pathParam("id"));
            CreateAccountDAO dao = ctx.bodyAsClass(CreateAccountDAO.class);

            try {
                if (customerBankUserService.login(users.get(id - 1), dao.getUsername(), dao.getPassword()) != 'e') {
                    throw new NotAuthorizedException();
                }
                employeeBankUserService.createNewAccount(users.get(dao.getCustomer_id() - 1), dao.getAccountType());
                ctx.status(204);
            } catch(NotAuthorizedException e){
                ctx.status(401);
                ctx.json("You are not authorized to use this functionality");
            } catch (UserNotFoundException e) {
                ctx.status(404);
                ctx.json("Cannot attach account to a non-existing user");
            } catch (DuplicateFoundException e) {
                ctx.status(500);
                ctx.result("Cannot create new account until duplicate account IDs are resolved");
            }
        });
    }


}
