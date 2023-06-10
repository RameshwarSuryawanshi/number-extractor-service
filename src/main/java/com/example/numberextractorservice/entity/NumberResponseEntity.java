package com.example.numberextractorservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NumberResponseEntity {

    private String id;
    private List<NumberEntity> numberEntityList;
}
