package com.example.beemplyeeapp.controller;

import com.example.beemplyeeapp.dao.IAccoutnDao;
import com.example.beemplyeeapp.model.Account;
import com.example.beemplyeeapp.model.ResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private IAccoutnDao accountDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAccountHappyPath() {
        Account account = new Account();
        Mockito.when(accountDao.createAccount(account)).thenReturn(true);

        ResponseEntity<ResponseStatus> response = accountController.addAccount(account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Created Succesful", response.getBody().getMessage());
    }

    @Test
    public void testAddAccountUnhappyPath() {
        Account account = new Account();
        Mockito.when(accountDao.createAccount(account)).thenReturn(false);

        ResponseEntity<ResponseStatus> response = accountController.addAccount(account);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Created Unsucssesful", response.getBody().getMessage());
    }

    @Test
    public void testLoginHappyPath() {
        String email = "test@example.com";
        String password = "password";

        Mockito.when(accountDao.login(email, password)).thenReturn(true);

        ResponseEntity<ResponseStatus> response = accountController.login(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login Succesful", response.getBody().getMessage());
    }

    @Test
    public void testLoginUnhappyPath() {
        String email = "test@example.com";
        String password = "password";

        Mockito.when(accountDao.login(email, password)).thenReturn(false);

        ResponseEntity<ResponseStatus> response = accountController.login(email, password);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid Credentials", response.getBody().getMessage());
    }

    @Test
    public void testRemoveAccountHappyPath() {
        int accountId = 1;
        Mockito.doNothing().when(accountDao).removeAccount(accountId);

        ResponseEntity<ResponseStatus> response = accountController.remove(accountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted Succesful", response.getBody().getMessage());
    }

    @Test
    public void testRemoveAccountUnhappyPath() {
        int accountId = 1;
        Mockito.doThrow(EntityNotFoundException.class).when(accountDao).removeAccount(accountId);

        ResponseEntity<ResponseStatus> response = accountController.remove(accountId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found with ID 1", response.getBody().getMessage());
    }

    @Test
    public void testUpdateAccountHappyPath() {
        int accountId = 1;
        Account account = new Account();

        Mockito.doNothing().when(accountDao).updateAccount(accountId, account);

        ResponseEntity<ResponseStatus> response = accountController.update(accountId, account);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Update Succesful", response.getBody().getMessage());
    }

    @Test
    public void testUpdateAccountUnhappyPath() {
        int accountId = 1;
        Account account = new Account();

        Mockito.doThrow(EntityNotFoundException.class).when(accountDao).updateAccount(accountId, account);

        ResponseEntity<ResponseStatus> response = accountController.update(accountId, account);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found with ID 1", response.getBody().getMessage());
    }
}
