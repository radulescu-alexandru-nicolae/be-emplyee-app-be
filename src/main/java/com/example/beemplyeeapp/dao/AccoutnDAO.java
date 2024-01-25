package com.example.beemplyeeapp.dao;

import com.example.beemplyeeapp.model.Account;
import com.example.beemplyeeapp.utils.CNPValidator;
import com.example.beemplyeeapp.utils.PasswordEncryption;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class AccoutnDAO implements IAccoutnDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public boolean createAccount(Account account) {
        String sqlQuery = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ACCOUNTS a WHERE a.email = ?1 OR a.cnp = ?2) THEN 0 ELSE 1 END";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter(1, account.getEmail());
        query.setParameter(2, account.getCnp());
        long result = (Long) query.getSingleResult();
        if (result == 1) {
            if (CNPValidator.isValidCNP(account.getCnp())) {
                entityManager.persist(account);
                return true;
            } else {
                return false;

            }
        } else {
            return false;
        }
    }
    public Account findByEmail(String email) {
        try {
            String sqlQuery = "SELECT a FROM Account a WHERE a.email = :email";
            TypedQuery<Account> query = entityManager.createQuery(sqlQuery, Account.class);
            query.setParameter("email", email);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
    public boolean login(String email, String password) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password);
        if (encryptedPassword == null) {
            // Handle encryption failure
            return false;
        }

        System.out.println("Encrypted password in accountDAO: " + encryptedPassword);

        String sqlQuery = "SELECT CASE WHEN EXISTS (SELECT 1 FROM accounts a WHERE a.email = ?1 and a.password = ?2) THEN 1 ELSE 0 END";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter(1, email);
        query.setParameter(2, encryptedPassword);

        System.out.println("111111 " + encryptedPassword + " " + password);

        long result = (long) query.getSingleResult();
        return result == 1;
    }

    @Override
    @Transactional
    public void removeAccount(int id) {
        Account account = entityManager.find(Account.class, id);
        System.out.println("AAAAAAAAAA" +account);
        if (account != null) {

            entityManager.remove(account);

        } else {
            throw new EntityNotFoundException("Account not found with ID: " + id);
        }
    }

    @Override
    @Transactional
    public void updateAccount(int id, Account account) {
        Account oldAccount = entityManager.find(Account.class, id);

        if (oldAccount != null) {
            oldAccount.setCnp(account.getCnp());
            oldAccount.setEmail(account.getEmail());
            String accountDecryptedPassword = account.getPasswordDecrypted();
            oldAccount.setPassword(accountDecryptedPassword);
        } else {
            throw new EntityNotFoundException("Account not found with ID:" + id);
        }
    }


}
