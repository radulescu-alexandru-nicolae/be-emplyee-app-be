//package com.example.beemplyeeapp.controller;
//import com.example.beemplyeeapp.controller.IdentityController;
//import com.example.beemplyeeapp.dao.IdentityDAO;
//import com.example.beemplyeeapp.model.AccountDto;
//import com.example.beemplyeeapp.model.ResponseStatus;
//import com.example.beemplyeeapp.service.FaceComparisonService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class IdentityControllerTest {
//
//    @InjectMocks
//    private IdentityController identityController;
//
//    @Mock
//    private IdentityDAO identityDAO;
//
//    @Mock
//    private FaceComparisonService faceComparisonService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testCreateIdentityHappyPath() throws IOException {
//        MultipartFile image = mock(MultipartFile.class);
//        when(image.getBytes()).thenReturn(new byte[]{1, 2, 3});
//
//        when(identityDAO.createIdentity(anyString(), any(byte[].class)).thenReturn(1);
//
//        ResponseEntity<?> response = identityController.createIdentity("test@example.com", image);
//
//        verify(identityDAO, times(1)).createIdentity(anyString(), any(byte[].class));
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Identity created successfully", response.getBody());
//    }
//
//    @Test
//    public void testCreateIdentityUnhappyPathIOException() throws IOException {
//        MultipartFile image = mock(MultipartFile.class);
//        when(image.getBytes()).thenThrow(IOException.class);
//
//        when(identityDAO.createIdentity(anyString(), any(byte[].class)).thenThrow(IOException.class);
//
//        ResponseEntity<?> response = identityController.createIdentity("test@example.com", image);
//
//        verify(identityDAO, times(1)).createIdentity(anyString(), any(byte[].class));
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Unable to process image", response.getBody());
//    }
//
//    @Test
//    public void testCreateIdentityUnhappyPathException() throws IOException {
//        MultipartFile image = mock(MultipartFile.class);
//        when(image.getBytes()).thenReturn(new byte[]{1, 2, 3});
//
//        when(identityDAO.createIdentity(anyString(), any(byte[].class)).thenThrow(RuntimeException.class);
//
//        ResponseEntity<?> response = identityController.createIdentity("test@example.com", image);
//
//        verify(identityDAO, times(1)).createIdentity(anyString(), any(byte[].class));
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("An error occurred while creating the identity", response.getBody());
//    }
//
//    // Additional test methods for other endpoints
//
//}
