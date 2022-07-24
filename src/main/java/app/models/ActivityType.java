package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "activity_types")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @JsonProperty("name")
    private String activityName;


    public long getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }


}