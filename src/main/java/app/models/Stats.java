package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Stats {
    @JsonProperty("todayCalories")
    private int todayCalories;
    @JsonProperty("todayWeight")
    private double todayWeight;
    @JsonProperty("thisWeekTotalCalories")
    private int thisWeekTotalCalories;
    @JsonProperty("thisWeekAverageWeight")
    private double thisWeekAverageWeight;
    @JsonProperty("lastWeekTotalCalories")
    private int lastWeekTotalCalories;
    @JsonProperty("lastWeekAverageWeight")
    private double lastWeekAverageWeight;

    public Stats(int todayCalories, double todayWeight, int thisWeekTotalCalories, double thisWeekAverageCalories, int thisWeekAverageWeight, double lastWeekAverageWeight) {
        this.todayCalories = todayCalories;
        this.todayWeight = todayWeight;
        this.thisWeekTotalCalories = thisWeekTotalCalories;
        this.thisWeekAverageWeight = thisWeekAverageCalories;
        this.lastWeekTotalCalories = thisWeekAverageWeight;
        this.lastWeekAverageWeight = lastWeekAverageWeight;
    }

    public int getTodayCalories() {
        return todayCalories;
    }

    public double getTodayWeight() {
        return todayWeight;
    }

    public int getThisWeekTotalCalories() {
        return thisWeekTotalCalories;
    }

    public double getThisWeekAverageWeight() {
        return thisWeekAverageWeight;
    }

    public int getLastWeekTotalCalories() {
        return lastWeekTotalCalories;
    }

    public double getLastWeekAverageWeight() {
        return lastWeekAverageWeight;
    }
}
