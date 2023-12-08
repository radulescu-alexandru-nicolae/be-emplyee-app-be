package com.example.beemplyeeapp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

 class CNPValidatorTest {



    @Test
    void testInvalidCNP_Length() {
        assertFalse(CNPValidator.isValidCNP("123"));          // Too short
        assertFalse(CNPValidator.isValidCNP("12345678901234")); // Too long
    }

    @Test
    void testInvalidCNP_NonNumeric() {
        assertFalse(CNPValidator.isValidCNP("12A4567890123")); // Contains non-numeric characters
        assertFalse(CNPValidator.isValidCNP("2950612-45678"));  // Contains non-numeric characters
    }

    @Test
    void testInvalidCNP_InvalidDate() {
        assertFalse(CNPValidator.isValidCNP("1234567890120")); // Invalid month (00)
        assertFalse(CNPValidator.isValidCNP("1234567890132")); // Invalid month (13)
        assertFalse(CNPValidator.isValidCNP("1234567890110")); // Invalid day (00)
        assertFalse(CNPValidator.isValidCNP("1234567890132")); // Invalid day (32)
    }

    @Test
    void testInvalidCNP_InvalidChecksum() {
        assertFalse(CNPValidator.isValidCNP("1234567890124")); // Invalid checksum
        assertFalse(CNPValidator.isValidCNP("2950612345679")); // Invalid checksum
        assertFalse(CNPValidator.isValidCNP("6000101234568")); // Invalid checksum
    }

    @Test
    void testInvalidCNP_InvalidControlDigit() {
        assertFalse(CNPValidator.isValidCNP("1234567890129")); // Invalid control digit
        assertFalse(CNPValidator.isValidCNP("2950612345670")); // Invalid control digit
        assertFalse(CNPValidator.isValidCNP("6000101234562")); // Invalid control digit
    }
}
