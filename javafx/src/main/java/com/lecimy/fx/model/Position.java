package com.lecimy.fx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position implements Comparable<Position> {

    private int place;
    private String name;
    private int score;

    @Override
    public int compareTo(Position o) {
        if (o.score > this.score) return 1;
        else if (o.score < this.score) return -1;
        else return 0;
    }
}
