package com.example.beemplyeeapp.controller;

import com.example.beemplyeeapp.service.FaceComparisonService;
import com.example.beemplyeeapp.dao.IdentityDAO;
import com.example.beemplyeeapp.model.Account;
import com.example.beemplyeeapp.model.AccountDto;
import com.example.beemplyeeapp.model.ResponseStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {
    private  IdentityDAO identityDAO;
    private FaceComparisonService faceComparisonService;

    public IdentityController(IdentityDAO identityDAO,FaceComparisonService faceComparisonService) {
        this.identityDAO = identityDAO;
        this.faceComparisonService=faceComparisonService;
    }

    @PostMapping
    public ResponseEntity<?> createIdentity(@RequestParam("email") String email,
                                            @RequestParam("image") MultipartFile image) {
        try {
            identityDAO.createIdentity(email, image.getBytes());
            return ResponseEntity.ok().body("Identity created successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Unable to process image");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("An error occurred while creating the identity");
        }
    }

    @PostMapping("/compare")
    public ResponseEntity<ResponseStatus> compareWithAll(@RequestParam("image") MultipartFile image) {
        try {
            byte[] imageBytes = image.getBytes();
            HashMap<AccountDto, byte[]> allIdentities = identityDAO.getAllIdentities();
            for (Map.Entry<AccountDto, byte[]> entry : allIdentities.entrySet()) {
                String compareResult = faceComparisonService.compareFaces(imageBytes, entry.getValue());
                ObjectMapper mapper = new ObjectMapper();

                JsonNode responseJson = mapper.readTree(compareResult);

                double confidence = responseJson.get("confidence").asDouble();

                if (confidence > 80) {

                    AccountDto accountDto=entry.getKey();
                    Account account = new Account(accountDto.getEmail(),accountDto.getPassword(),accountDto.getCnp());
                    return  ResponseEntity.ok().body(ResponseStatus.builder().account(account).build());
                }
            }
            return ResponseEntity.notFound().build();

        } catch (IOException e) {

            e.printStackTrace();
            return ResponseEntity.badRequest().body(ResponseStatus.builder().message("An error ocured...").build());
        }
    }
}

