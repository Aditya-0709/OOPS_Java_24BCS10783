package com.bank.loans;

public class Loans {

    double loanAmount;
    double interestRate;
    int tenure;

    public Loans(double loanAmount, double interestRate, int tenure){
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
    }

    public void calculateEMI(){

        double emi = (loanAmount * interestRate * tenure) / 100;

        System.out.println("EMI is: " + emi);
    }

}