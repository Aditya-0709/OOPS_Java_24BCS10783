package com.bank.account;

import com.bank.exception.InsufficientBalanceException;

public class Account {

    public int accountNumber;
    public double balance;

    public Account(int accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount){
        balance = balance + amount;
        System.out.println("Deposit done: " + amount);
    }

    public void withdraw(double amount) throws InsufficientBalanceException{

        if(amount > balance){
            throw new InsufficientBalanceException("Balance is not enough");
        }

        balance = balance - amount;
        System.out.println("Withdraw done: " + amount);
    }

}