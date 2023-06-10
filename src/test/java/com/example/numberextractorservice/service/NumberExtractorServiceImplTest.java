package com.example.numberextractorservice.service;

import com.example.numberextractorservice.entity.NumberEntity;
import com.example.numberextractorservice.entity.NumberResponseEntity;
import com.example.numberextractorservice.entity.RequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NumberExtractorServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NumberExtractorServiceImpl numberExtractorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessFile() throws Exception {
        // Mock the ObjectMapper behavior
        RequestEntity mockRequestEntity = new RequestEntity();
        mockRequestEntity.setId("12345");
        mockRequestEntity.setText("Take immediate action to stop the violation and notify King County Industrial Waste within 24 hours of learning of the violation. In case of violation penalty of $1,000,000 should be paid within 3 months.");

        when(objectMapper.readValue(Mockito.any(byte[].class), Mockito.eq(RequestEntity.class)))
                .thenReturn(mockRequestEntity);

        // Create a mock file
        byte[] fileContent = "Test file content".getBytes();
        MultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent);

        // Call the service method
        NumberResponseEntity responseEntity = numberExtractorService.processFile(mockFile);

        // Verify the response
        assertEquals(mockRequestEntity.getId(), responseEntity.getId());

        List<NumberEntity> numberEntityList = responseEntity.getNumberEntityList();
        assertEquals(3, numberEntityList.size());

        NumberEntity numberEntity1 = numberEntityList.get(0);
        assertEquals("24", numberEntity1.getExtractedText());
        assertEquals(new BigDecimal("24"), numberEntity1.getExtractedValue());
        assertEquals(91, numberEntity1.getStartPosition());
        assertEquals(93, numberEntity1.getEndPosition());

        NumberEntity numberEntity2 = numberEntityList.get(1);
        assertEquals("1,000,000", numberEntity2.getExtractedText());
        assertEquals(new BigDecimal("1000000"), numberEntity2.getExtractedValue());
        assertEquals(163, numberEntity2.getStartPosition());
        assertEquals(172, numberEntity2.getEndPosition());
    }
}
