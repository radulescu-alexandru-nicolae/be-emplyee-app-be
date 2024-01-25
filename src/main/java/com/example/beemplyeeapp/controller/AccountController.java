package com.example.beemplyeeapp.controller;

import com.example.beemplyeeapp.dao.IAccoutnDao;
import com.example.beemplyeeapp.model.Account;
import com.example.beemplyeeapp.model.ResponseStatus;
import com.example.beemplyeeapp.utils.PasswordEncryption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
@Validated
@RequiredArgsConstructor
public class AccountController {


    private final IAccoutnDao iAccoutnDao;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseStatus> addAccount(@RequestBody Account account) {
        if (!iAccoutnDao.createAccount(account)) {
            return new ResponseEntity<>(ResponseStatus.builder().message("Created Unsucssesful").build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ResponseStatus.builder().message("Created Succesful").account(account).build(), HttpStatus.CREATED);
    }

    @GetMapping("/auth/{email}/{password}")
    public ResponseEntity<ResponseStatus> login(@PathVariable("email") String email, @PathVariable("password") String password) {
        if (iAccoutnDao.login(email, password)) {
            return new ResponseEntity<>(ResponseStatus.builder().message("Login Succesful").build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseStatus.builder().message("Invalid Credentials").build(), HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStatus> remove(@PathVariable("id") int id){
        try{
            iAccoutnDao.removeAccount(id);
            return new ResponseEntity<>(ResponseStatus.builder().message("Deleted Succesful").build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatus.builder().message("Account not found with ID "+id).build(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseStatus> update(@PathVariable("id") int id,@RequestBody Account account){
        try{
            iAccoutnDao.updateAccount(id,account);
            return new ResponseEntity<>(ResponseStatus.builder().message("Update Succesful").build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(ResponseStatus.builder().message("Account not found with ID "+id).build(), HttpStatus.NOT_FOUND);
        }
    }


}
