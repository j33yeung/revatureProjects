package com.revature.bankingapp;

public class SequentialIDGenerator {
    private int initialValue;

    public SequentialIDGenerator(int initialValue) {
        this.initialValue = initialValue;
    }

    public int currentID(){
        return initialValue;
    }

    public int nextID(){
        return ++initialValue;
    }

}
