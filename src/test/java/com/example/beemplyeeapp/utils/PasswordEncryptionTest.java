package com.example.beemplyeeapp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class PasswordEncryptionTest {

     @Test
     void testEncryptWithNullPassword() {
         String encryptedPassword = PasswordEncryption.encryptPassword(null);
         assertNull(encryptedPassword);
     }

     @Test
     void testDecryptWithInvalidPassword() {
         // Assuming an invalid encrypted password
         String invalidEncryptedPassword = "InvalidEncryptedPassword";
         String decryptedPassword = PasswordEncryption.decryptPassword(invalidEncryptedPassword);
         assertNull(decryptedPassword);
     }
}
