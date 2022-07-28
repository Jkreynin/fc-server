package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EntryFilter {
    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @JsonProperty("endTime")
    private LocalDateTime endTime;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
