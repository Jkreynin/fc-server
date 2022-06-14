package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EntryFilter {
    @JsonProperty("start-time")
    private LocalDateTime startTime;

    @JsonProperty("end-time")
    private LocalDateTime endTime;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
