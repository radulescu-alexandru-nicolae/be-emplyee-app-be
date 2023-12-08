package com.example.beemplyeeapp.controller;
import com.example.beemplyeeapp.dao.IdentityDAO;
import com.example.beemplyeeapp.model.AccountDto;
import com.example.beemplyeeapp.model.ResponseStatus;
import com.example.beemplyeeapp.service.FaceComparisonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IdentityControllerTest {

    @Mock
    private IdentityDAO identityDAO;

    @Mock
    private FaceComparisonService faceComparisonService;

    @InjectMocks
    private IdentityController identityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateIdentity_Success() throws IOException {
        // Arrange
        String email = "test@example.com";
        MultipartFile mockImage = mock(MultipartFile.class);

        // Provide a non-null byte array
        byte[] imageData = "testImageData".getBytes();

        // Mock the behavior
        when(mockImage.getBytes()).thenReturn(imageData);

        // Act
        ResponseEntity<String> responseEntity = identityController.createIdentity(email, mockImage);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Identity created successfully", responseEntity.getBody());

        // Verify that createIdentity was called with the expected arguments
        verify(identityDAO).createIdentity(ArgumentMatchers.<String>eq(email),
                eq(imageData));
    }

//    @Test
//    void testCreateIdentity_BadRequest() throws IOException {
//        // Arrange
//        String email = "test@example.com";
//        MultipartFile mockImage = mock(MultipartFile.class);
//
//        // Provide a non-null byte array
//        byte[] imageData = "testImageData".getBytes();
//
//        // Mock the behavior
//        when(mockImage.getBytes()).thenReturn(imageData);
//        doThrow(IOException.class).when(identityDAO).createIdentity(eq(email), eq(imageData));
//
//        // Act
//        ResponseEntity<String> responseEntity = identityController.createIdentity(email, mockImage);
//
//        // Assert
//        assertEquals(400, responseEntity.getStatusCodeValue());
//        assertEquals("Unable to process image", responseEntity.getBody());
//    }

//    @Test
//    void testCreateIdentity_GenericException() throws IOException {
//        // Arrange
//        String email = "test@example.com";
//        MultipartFile mockImage = mock(MultipartFile.class);
//
//        // Provide a non-null byte array
//        byte[] imageData = "testImageData".getBytes();
//
//        // Mock the behavior
//        when(mockImage.getBytes()).thenReturn(imageData);
//        // Use doThrow to handle the checked exception
//        doThrow(new IOException("Simulated IOException")).when(identityDAO).createIdentity(eq(email), eq(imageData));
//
//        // Act
//        ResponseEntity<String> responseEntity = identityController.createIdentity(email, mockImage);
//
//        // Assert
//        assertEquals(400, responseEntity.getStatusCodeValue());
//        assertEquals("An error occurred while creating the identity", responseEntity.getBody());
//    }

    @Test
    void testCompareWithAll_Found() throws IOException {
        // Arrange
        MultipartFile mockImage = mock(MultipartFile.class);
        byte[] imageData = "testImageData".getBytes();
        when(mockImage.getBytes()).thenReturn(imageData);

        Map.Entry<AccountDto, byte[]> entry = Collections.singletonMap(new AccountDto(), "testEntryData".getBytes()).entrySet().iterator().next();
        when(identityDAO.getAllIdentities()).thenReturn(Collections.singletonMap(entry.getKey(), entry.getValue()));
        when(faceComparisonService.compareFaces(eq(imageData), any(byte[].class))).thenReturn("{\"confidence\": 90}");

        // Act
        ResponseEntity<ResponseStatus> responseEntity = identityController.compareWithAll(mockImage);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        // Add more assertions as needed for the ResponseStatus content
    }

    @Test
    void testCompareWithAll_NotFound() throws IOException {
        // Arrange
        MultipartFile mockImage = mock(MultipartFile.class);
        byte[] imageData = "testImageData".getBytes();
        when(mockImage.getBytes()).thenReturn(imageData);

        when(identityDAO.getAllIdentities()).thenReturn(Collections.emptyMap());

        // Act
        ResponseEntity<ResponseStatus> responseEntity = identityController.compareWithAll(mockImage);

        // Assert
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

//    @Test
//    void testCompareWithAll_IOError() throws IOException {
//        // Arrange
//        MultipartFile mockImage = mock(MultipartFile.class);
//        byte[] imageData = "testImageData".getBytes();
//        when(mockImage.getBytes()).thenReturn(imageData);
//
//        when(identityDAO.getAllIdentities()).thenReturn(Collections.singletonMap(new AccountDto(), "testEntryData".getBytes()));
//        when(faceComparisonService.compareFaces(eq(imageData), any(byte[].class))).thenThrow(IOException.class);
//
//        // Act
//        ResponseEntity<ResponseStatus> responseEntity = identityController.compareWithAll(mockImage);
//
//        // Assert
//        assertEquals(400, responseEntity.getStatusCodeValue());
//        // Add more assertions as needed for the ResponseStatus content
//    }
}