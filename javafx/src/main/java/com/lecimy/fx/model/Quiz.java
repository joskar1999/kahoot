package com.lecimy.fx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quiz {

    private int id;
    private String name;
    private String questionsAmount;
    private String playersAmount;
}