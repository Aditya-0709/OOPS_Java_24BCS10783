package com;

import com.bank.account.SavingsAccount;
import com.bank.customer.Customers;
import com.bank.loans.Loans;
import com.bank.util.BankUtil;
import com.bank.exception.InsufficientBalanceException;

public class BankApplication {

    public static void main(String[] args) {

        try{

            int accNo = BankUtil.generateAccountNumber();

            BankUtil.validateMinimumBalance(5000);

            SavingsAccount acc = new SavingsAccount(accNo, 5000, 5);

            Customers c1 = new Customers(1, "Aditya");

            c1.linkAccount(acc);

            acc.deposit(2000);

            acc.withdraw(1000);

            acc.calculateInterest();

            Loans loan = new Loans(100000,5,2);

            loan.calculateEMI();

            c1.displayCustomerDetails();

        }

        catch(InsufficientBalanceException e){
            System.out.println(e.getMessage());
        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}