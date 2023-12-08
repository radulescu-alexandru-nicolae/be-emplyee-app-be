package com.example.beemplyeeapp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class PasswordEncryptionTest {



     @Test
     void testEncryptAndDecryptInvalidPassword() {
         // Encrypt and decrypt with a null password
         String encryptedPasswordNull = PasswordEncryption.encryptPassword(null);
         assertNull(encryptedPasswordNull);

         // Attempt to decrypt an invalid encrypted password
         String decryptedInvalidPassword = PasswordEncryption.decryptPassword("invalidEncryptedPassword");
         assertNull(decryptedInvalidPassword);

         // Encrypt and decrypt with an empty password
         String encryptedPasswordEmpty = PasswordEncryption.encryptPassword("");
         assertNull(encryptedPasswordEmpty);

         // Decrypt the empty password
         String decryptedPasswordEmpty = PasswordEncryption.decryptPassword(encryptedPasswordEmpty);
         assertNull(decryptedPasswordEmpty);
     }


     @Test
    void testDecryptInvalidEncryptedPassword() {
        // Attempt to decrypt an invalid encrypted password
        String decryptedInvalidPassword = PasswordEncryption.decryptPassword("invalidEncryptedPassword");
        assertNull(decryptedInvalidPassword);
    }
}
