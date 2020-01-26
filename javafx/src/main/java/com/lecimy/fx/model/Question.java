package com.lecimy.fx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
}
