package com.example.beemplyeeapp.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.example.beemplyeeapp.model.Account;
import com.example.beemplyeeapp.model.AccountDto;
import com.example.beemplyeeapp.model.Identity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class IdentityDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private AccoutnDAO accoutnDAO;

    @InjectMocks
    private IdentityDAO identityDAO;

    @Test
    void testCreateIdentity() {
        // Mocking the Account and EntityManager
        Account account = new Account();
        Mockito.when(accoutnDAO.findByEmail("test@example.com")).thenReturn(account);

        // Mocking the void method
        Mockito.doNothing().when(entityManager).persist(any(Identity.class));

        // Testing the createIdentity method
        assertDoesNotThrow(() -> identityDAO.createIdentity("test@example.com", new byte[]{1, 2, 3}));

        // Verifying that AccoutnDAO methods were called
        Mockito.verify(accoutnDAO, Mockito.times(1)).findByEmail("test@example.com");

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).persist(any(Identity.class));

        // Verifying that the Identity is associated with the Account
        assertNotNull(account.getIdentity());
        assertArrayEquals(new byte[]{1, 2, 3}, account.getIdentity().getImage());
    }

    @Test
    void testCreateIdentity_AccountNotFound() {
        // Mocking the Account
        Mockito.when(accoutnDAO.findByEmail("test@example.com")).thenReturn(null);

        // Testing the createIdentity method for the case where Account is not found
        RuntimeException exception = assertThrows(RuntimeException.class, () -> identityDAO.createIdentity("test@example.com", new byte[]{1, 2, 3}));

        // Verifying that AccoutnDAO methods were called
        Mockito.verify(accoutnDAO, Mockito.times(1)).findByEmail("test@example.com");

        // Verifying the exception type
        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void testGetAllIdentities() {
        // Mocking the Identity, Account, and TypedQuery
        Identity identity1 = new Identity("test1@example.com", new byte[]{1, 2, 3}, new Account());
        Identity identity2 = new Identity("test2@example.com", new byte[]{4, 5, 6}, new Account());
        List<Identity> identities = Arrays.asList(identity1, identity2);

        TypedQuery<Identity> query = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(anyString(), eq(Identity.class))).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(identities);

        // Testing the getAllIdentities method
        Map<AccountDto, byte[]> identitiesMap = identityDAO.getAllIdentities();

        // Verifying that EntityManager methods were called
        Mockito.verify(entityManager, Mockito.times(1)).createQuery(anyString(), eq(Identity.class));

        // Verifying the result map
        assertEquals(1, identitiesMap.size());
        assertTrue(identitiesMap.containsKey(new AccountDto()));
        assertTrue(identitiesMap.containsKey(new AccountDto()));
    }
}
