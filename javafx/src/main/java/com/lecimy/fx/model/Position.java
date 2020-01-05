package main.java.com.lecimy.fx.model;

public class Position {

    private int place;
    private String name;
    private int score;

    public Position(int place, String name, int score) {
        this.place = place;
        this.name = name;
        this.score = score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
