package com.example.numberextractorservice.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class NumberEntity {

    private String extractedText;
    private BigDecimal extractedValue;
    private int startPosition;
    private int endPosition;

}
