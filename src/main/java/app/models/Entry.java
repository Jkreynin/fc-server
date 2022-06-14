package app.models;

import app.utils.HashMapConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "data")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> data;

    @Enumerated(EnumType.STRING)
    private EntryType type;

    public Entry() {

    }

    public Entry(LocalDateTime startTime) {
        super();
        this.startTime = startTime;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public EntryType getType() {
        return type;
    }
}