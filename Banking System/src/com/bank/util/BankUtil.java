package com.bank.util;

import java.util.Random;

public class BankUtil {

    public static int generateAccountNumber() {
        Random r = new Random();
        int accNo = 100000 + r.nextInt(900000);
        return accNo;
    }

    public static void validateMinimumBalance(double balance) throws Exception {

        if(balance < 1000){
            throw new Exception("Minimum balance should be 1000");
        }

    }

}