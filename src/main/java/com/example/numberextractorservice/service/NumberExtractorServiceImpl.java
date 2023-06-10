package com.example.numberextractorservice.service;

import com.example.numberextractorservice.entity.NumberEntity;
import com.example.numberextractorservice.entity.NumberResponseEntity;
import com.example.numberextractorservice.entity.RequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class NumberExtractorServiceImpl implements NumberExtractorService {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Process Multipart FIle
     * @param file multipart file
     * @return NumberResponseEntity with id and List Of Objects
     */
    @Override
    public NumberResponseEntity processFile(MultipartFile file) throws IOException {

        log.info("File Process Started");
        RequestEntity requestEntity = convertFileToModel(file);
        List<NumberEntity> numberEntityList = extractNumbers(requestEntity.getText());
        log.info("File Processed Successfully");

        return new NumberResponseEntity(requestEntity.getId(), numberEntityList);
    }

    /**
     * Extract numbers and it's position from the text
     * @param text text from the multipart file
     * @return List of NumberEntity
     */
    private List<NumberEntity> extractNumbers(String text) {

        //Create list of NumberEntity
        List<NumberEntity> numberEntityList = new ArrayList<>();

        //Regular expression pattern to match numbers
        Pattern pattern = Pattern.compile("\\d{1,3}(,\\d{3})*(\\.\\d+)?");
        Matcher matcher = pattern.matcher(text);

        //Create number entity from the matcher and add it to list
        while (matcher.find()) {
            String numberString = matcher.group();
            int startPosition = matcher.start();
            int endPosition = matcher.end();
            BigDecimal number = new BigDecimal(numberString.replaceAll(",", ""));
            numberEntityList.add(new NumberEntity(numberString, number, startPosition, endPosition));
        }
        return numberEntityList;
    }

    /**
     * Convert multipart file to Request Entity
     * @param file multipart file
     * @return RequestEntity
     */
    private RequestEntity convertFileToModel(MultipartFile file) throws IOException {

        //Convert file to RequestEntity
        RequestEntity requestEntity = objectMapper.readValue(file.getBytes(), RequestEntity.class);

        return requestEntity;
    }

}

