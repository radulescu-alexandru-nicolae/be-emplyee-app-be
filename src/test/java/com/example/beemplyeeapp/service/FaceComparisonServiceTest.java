package com.example.beemplyeeapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

 class FaceComparisonServiceTest {

    @Mock
    private RestTemplate restTemplate;

     @InjectMocks
     private FaceComparisonService faceComparisonService = new FaceComparisonService();

     private static final String FACEPLUS_COMPARE_URL = "https://api-us.faceplusplus.com/facepp/v3/compare";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCompareFaces_Success() {
        // Mocking the response from the external API
        String successResponse = "{\"confidence\": 0.85}";
        ResponseEntity<String> successEntity = new ResponseEntity<>(successResponse, HttpStatus.OK);
        Mockito.when(restTemplate.postForEntity(eq(FACEPLUS_COMPARE_URL), any(HttpEntity.class), eq(String.class)))
                .thenReturn(successEntity);

        // Mocking images
        byte[] image1 = "image1".getBytes();
        byte[] image2 = "image2".getBytes();

        // Performing the actual comparison
        String result = faceComparisonService.compareFaces(image1, image2);

        // Verifying the result
        assertNull(result);
    }

    @Test
    void testCompareFaces_Failure() {
        // Mocking the response from the external API for failure
        ResponseEntity<String> failureEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(restTemplate.postForEntity(eq(FACEPLUS_COMPARE_URL), any(HttpEntity.class), eq(String.class)))
                .thenReturn(failureEntity);

        // Mocking images
        byte[] image1 = "image1".getBytes();
        byte[] image2 = "image2".getBytes();

        // Performing the actual comparison
        String result = faceComparisonService.compareFaces(image1, image2);

        // Verifying the result
        assertNull(result);
    }
}
