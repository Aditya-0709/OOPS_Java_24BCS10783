package com.bank.account;

public class SavingsAccount extends Account {

    public double interestRate;

    public SavingsAccount(int accountNumber, double balance, double interestRate){
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public void calculateInterest(){

        double interest = balance * interestRate / 100;

        System.out.println("Interest is: " + interest);
    }

}