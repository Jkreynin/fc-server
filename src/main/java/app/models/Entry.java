package app.models;

import app.utils.EntryEnumType;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "entries")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(
                name = "entry_type_enum",
                typeClass = EntryEnumType.class
        )
})
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_time")
    @JsonProperty("start-time")
    private LocalDateTime startTime;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json", name = "data")
    private Map<String, String> data;

    @Type(type = "entry_type_enum")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "entry_type")
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

    public Map<String, String> getData() {
        return data;
    }

    public EntryType getType() {
        return type;
    }
}