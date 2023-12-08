package com.example.beemplyeeapp.utils;

public class CNPValidator {

    private CNPValidator() {
    }

    public static boolean isValidCNP(String cnp) {
        if (cnp.length() != 13) {
            return false;
        }
        if (!cnp.matches("\\d+")) {
            return false;
        }
        int month = Integer.parseInt(cnp.substring(3, 5));
        int day = Integer.parseInt(cnp.substring(5, 7));

        if (month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        int[] coefficients = { 2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9 };
        int checksum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Integer.parseInt(cnp.substring(i, i + 1));
            checksum += digit * coefficients[i];
        }
        int controlDigit = Integer.parseInt(cnp.substring(12));
        checksum %= 11;
        if (checksum == 10) {
            checksum = 1;
        }
        return checksum == controlDigit;
    }


}
