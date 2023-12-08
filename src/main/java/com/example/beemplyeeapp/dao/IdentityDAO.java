package com.example.beemplyeeapp.dao;

import com.example.beemplyeeapp.model.Account;
import com.example.beemplyeeapp.model.AccountDto;
import com.example.beemplyeeapp.model.Identity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class IdentityDAO {
    @PersistenceContext
    private EntityManager entityManager;

@Autowired
private AccoutnDAO accoutnDAO;

    public void createIdentity(String email, byte[] image) {
        Account account = accoutnDAO.findByEmail(email); // Find an Account with the same email.

        if (account != null) {
            Identity identity = new Identity(email, image,account);

            account.setIdentity(identity); // Associate the identity with the account.

            entityManager.persist(identity); // Save the identity, which is now associated with the account.
        } else {
throw new RuntimeException();        }
    }

    public Map<AccountDto, byte[]> getAllIdentities() {
        List<Identity> identities = entityManager.createQuery("SELECT i FROM identities i", Identity.class).getResultList();

        HashMap<AccountDto, byte[]> identitiesMap = new HashMap<>();
        for (Identity identity : identities) {
            Account account = identity.getAccount();
            AccountDto accountDto = new AccountDto();
            accountDto.setId(account.getId());
            accountDto.setEmail(account.getEmail());
            accountDto.setCnp(account.getCnp());
            accountDto.setPassword(account.getPasswordDecrypted());

            identitiesMap.put(accountDto, identity.getImage());
        }

        return identitiesMap;
    }
}