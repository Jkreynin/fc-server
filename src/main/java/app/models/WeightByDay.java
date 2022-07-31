package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class WeightByDay {
    @JsonProperty("day")
    private String day;
    @JsonProperty("weight")
    private double weight;

    public WeightByDay(String day, double weight) {
        this.day = day;
        this.weight = weight;
    }

    public String getDay() {
        return day;
    }

    public double getWeight() {
        return weight;
    }
}
