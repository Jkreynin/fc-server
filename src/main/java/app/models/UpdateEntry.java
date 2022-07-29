package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateEntry {
    @JsonProperty("durationInSeconds")
    private int durationInSeconds;

    public int getDurationInSeconds() {
        return durationInSeconds;
    }


}
