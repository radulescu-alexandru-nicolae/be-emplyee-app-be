package com.example.beemplyeeapp.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class FaceComparisonService {
    private static final String FACEPLUS_API_KEY = "B77z0dWb9a7T0G2nOAUU-IZBmDeRBNxL";
    private static final String FACEPLUS_API_SECRET = "pBKr2gN5SgHEPtKWu4LrpL4ggmVxlkav";
    private static final String FACEPLUS_COMPARE_URL = "https://api-us.faceplusplus.com/facepp/v3/compare";

    private final RestTemplate restTemplate;

    public FaceComparisonService() {
        this.restTemplate = new RestTemplate();
    }

    public String compareFaces(byte[] image1, byte[] image2) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("api_key", FACEPLUS_API_KEY);
        map.add("api_secret", FACEPLUS_API_SECRET);
        map.add("image_base64_1", Base64.getEncoder().encodeToString(image1));
        map.add("image_base64_2", Base64.getEncoder().encodeToString(image2));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(FACEPLUS_COMPARE_URL, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
