package com.example.beemplyeeapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.example.beemplyeeapp.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class AccoutnDAOTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private AccoutnDAO accoutnDAO;

    @Test
    void testCreateAccount() {
        // Mocking the EntityManager and Query
        long number=1L;
        Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        Mockito.when(query.setParameter(anyInt(), any())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(number);

        // Creating a sample Account
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setCnp("1234567890123");
        account.setPassword("password");

        // Testing the createAccount method
        boolean result = accoutnDAO.createAccount(account);

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(anyString());
        Mockito.verify(query, Mockito.times(2)).setParameter(anyInt(), any());
        Mockito.verify(query, Mockito.times(1)).getSingleResult();

        // Verifying the result
        assertFalse(result);
    }

    @Test
    void testFindByEmail() {
        // Mocking the EntityManager and TypedQuery
        TypedQuery<Account> query = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(anyString(), eq(Account.class))).thenReturn(query);
        Mockito.when(query.setParameter(anyString(), any())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(new Account());

        // Testing the findByEmail method
        Account result = accoutnDAO.findByEmail("test@example.com");

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).createQuery(anyString(), eq(Account.class));
        Mockito.verify(query, Mockito.times(1)).setParameter(anyString(), any());
        Mockito.verify(query, Mockito.times(1)).getSingleResult();

        // Verifying the result
        assertNotNull(result);
    }

    @Test
    void testLogin() {
        long number=1L;

        // Mocking the EntityManager and Query
        Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        Mockito.when(query.setParameter(anyInt(), any())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(number);
        // Testing the login method
        boolean result = accoutnDAO.login("test@example.com", "password");

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).createNativeQuery(anyString());
        Mockito.verify(query, Mockito.times(2)).setParameter(anyInt(), any());
        Mockito.verify(query, Mockito.times(1)).getSingleResult();

        // Verifying the result
        assertTrue(result);
    }
    @Test
    void testRemoveAccount() {
        // Mocking the EntityManager and Account
        Account account = Mockito.mock(Account.class);
        Mockito.when(entityManager.find(Account.class, 1)).thenReturn(account);

        // Testing the removeAccount method
        assertDoesNotThrow(() -> accoutnDAO.removeAccount(1));

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).find(Account.class, 1);
        Mockito.verify(entityManager, Mockito.times(1)).remove(account);
    }

    @Test
    void testRemoveAccount_AccountNotFound() {
        // Mocking the EntityManager
        Mockito.when(entityManager.find(Account.class, 1)).thenReturn(null);

        // Testing the removeAccount method for the case where Account is not found
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accoutnDAO.removeAccount(1));

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).find(Account.class, 1);

        // Verifying the exception message
        assertEquals("Account not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateAccount() {
        // Mocking the EntityManager and Account
        Account oldAccount = new Account();
        Mockito.when(entityManager.find(Account.class, 1)).thenReturn(oldAccount);

        // Creating a sample Account for update
        Account updatedAccount = new Account();
        updatedAccount.setEmail("updated@example.com");
        updatedAccount.setCnp("1234567890123");


        // Testing the updateAccount method
        assertDoesNotThrow(() -> accoutnDAO.updateAccount(1, updatedAccount));

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).find(Account.class, 1);

        // Verifying the updated values
        assertEquals("updated@example.com", oldAccount.getEmail());
        assertEquals("1234567890123", oldAccount.getCnp());
    }

    @Test
    void testUpdateAccount_AccountNotFound() {
        // Mocking the EntityManager
        Mockito.when(entityManager.find(Account.class, 1)).thenReturn(null);

        // Creating a sample Account for update
        Account updatedAccount = new Account();

        // Testing the updateAccount method for the case where Account is not found
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accoutnDAO.updateAccount(1, updatedAccount));

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).find(Account.class, 1);

        // Verifying the exception message
        assertEquals("Account not found with ID:1", exception.getMessage());
    }
}