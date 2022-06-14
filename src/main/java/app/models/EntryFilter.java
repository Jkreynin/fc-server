package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EntryFilter {
    @JsonProperty("start-time")
    private LocalDateTime startTime;

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
