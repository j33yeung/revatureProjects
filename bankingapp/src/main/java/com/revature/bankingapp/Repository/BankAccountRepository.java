package com.revature.bankingapp.Repository;

import com.revature.bankingapp.dao.BankAccount;
import com.revature.bankingapp.Connector.DBConnector;
import com.revature.bankingapp.SequentialIDGenerator;

import java.util.ArrayList;

public class BankAccountRepository implements Repository<Integer, BankAccount> {

    private DBConnector dbConnector;
    private ArrayList<BankAccount> users = new ArrayList<>();
    private SequentialIDGenerator idGen;

    public BankAccountRepository(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public ArrayList<BankAccount> getAll() {
        return users;
    }

    @Override
    public BankAccount getById(Integer id) {
        return users.stream().filter(account -> account.getId()==id).findFirst().get();
    }

    @Override
    public Integer save(BankAccount obj) {
        return null;
    }

    @Override
    public void delete(BankAccount obj) {

    }
}
