package com.example.beemplyeeapp.dao;


import com.example.beemplyeeapp.model.Account;

public interface IAccoutnDao {
    boolean createAccount(Account account);
    boolean login(String email,String password);

    void removeAccount(int id);

    void updateAccount(int id,Account account);
}
