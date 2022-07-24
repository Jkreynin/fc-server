package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class StatsFilter {
    @JsonProperty("start-time")
    private LocalDateTime startTime;

    public LocalDateTime getStartTime() {
        return startTime;
    }

}
