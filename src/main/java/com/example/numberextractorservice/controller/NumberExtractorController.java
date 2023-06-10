package com.example.numberextractorservice.controller;

import com.example.numberextractorservice.entity.NumberResponseEntity;
import com.example.numberextractorservice.service.NumberExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class NumberExtractorController {

    @Autowired
    NumberExtractorService numberExtractorService;

    @PostMapping("/extract-numbers")
    public ResponseEntity<NumberResponseEntity> extractNumbers(@RequestPart("file") MultipartFile file) throws IOException {

        NumberResponseEntity numberResponseEntity = numberExtractorService.processFile(file);

        return ResponseEntity.ok(numberResponseEntity);
    }
}
