package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Stats {
    @JsonProperty("today-calories")
    private int todayCalories;
    @JsonProperty("today-weight")
    private double todayWeight;
    @JsonProperty("this-week-total-calories")
    private int thisWeekTotalCalories;
    @JsonProperty("this-week-average-weight")
    private double thisWeekAverageCalories;
    @JsonProperty("last-week-total-calories")
    private int lastWeekTotalCalories;
    @JsonProperty("last-week-average-weight")
    private double lastWeekAverageWeight;

    public Stats(int todayCalories, double todayWeight, int thisWeekTotalCalories, double thisWeekAverageCalories, int lastWeekTotalCalories, double lastWeekAverageWeight) {
        this.todayCalories = todayCalories;
        this.todayWeight = todayWeight;
        this.thisWeekTotalCalories = thisWeekTotalCalories;
        this.thisWeekAverageCalories = thisWeekAverageCalories;
        this.lastWeekTotalCalories = lastWeekTotalCalories;
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

    public double getThisWeekAverageCalories() {
        return thisWeekAverageCalories;
    }

    public int getLastWeekTotalCalories() {
        return lastWeekTotalCalories;
    }

    public double getLastWeekAverageWeight() {
        return lastWeekAverageWeight;
    }
}
