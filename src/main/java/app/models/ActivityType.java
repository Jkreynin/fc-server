package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

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