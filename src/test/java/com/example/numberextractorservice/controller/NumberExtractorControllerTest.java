package com.example.numberextractorservice.controller;

import com.example.numberextractorservice.entity.NumberResponseEntity;
import com.example.numberextractorservice.service.NumberExtractorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NumberExtractorControllerTest {

    @Mock
    private NumberExtractorService numberExtractorService;

    @Test
    public void testExtractNumbers() throws IOException {
        // Mock the service
        NumberResponseEntity mockResponse = new NumberResponseEntity("id", new ArrayList<>());
        when(numberExtractorService.processFile(any(MultipartFile.class))).thenReturn(mockResponse);

        // Create a mock file
        byte[] fileContent = "Test file content".getBytes();
        MultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // Create the controller
        NumberExtractorController controller = new NumberExtractorController();
        controller.numberExtractorService = numberExtractorService;

        // Call the endpoint
        ResponseEntity<NumberResponseEntity> response = controller.extractNumbers(mockFile);

        // Verify the service method was called
        verify(numberExtractorService, times(1)).processFile(mockFile);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }
}
