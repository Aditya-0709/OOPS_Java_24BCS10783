package com.bank.customer;

import com.bank.account.Account;

public class Customers {

    int customerId;
    String name;
    Account account;

    public Customers(int customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public void linkAccount(Account account){
        this.account = account;
    }

    public void displayCustomerDetails(){

        System.out.println("Customer ID: " + customerId);
        System.out.println("Customer Name: " + name);

        if(account != null){
            System.out.println("Account Number: " + account.accountNumber);
            System.out.println("Balance: " + account.balance);
        }

    }

}