package app.controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import app.models.*;
import app.repositories.ActivityTypeRepository;
import app.repositories.EntryRepository;
import app.repositories.StatsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@CrossOrigin(origins = {"http://localhost:3000", "http://fc-web-app.s3-website.eu-central-1.amazonaws.com/"}
)
@RestController
@RequestMapping("/api/v1/")
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/entries")
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @GetMapping("/activityTypes")
    public List<ActivityType> getAllActivityTypes() {
        return activityTypeRepository.findAll();
    }

    @PostMapping("/stats")
    public Stats getStats(@RequestBody StatsFilter statsFilter) {
        return statsRepository.getStats(statsFilter.getStartTime().toString());
    }

    @PostMapping("/entries")
    public Entry createEntry(@RequestBody Entry entry) {
        return entryRepository.save(entry);
    }

    @PostMapping("/entries/filters")
    public List<Entry> findEntryWithFilters(@RequestBody EntryFilter entryFilter) {
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Entry> cr = cb.createQuery(Entry.class);
            Root<Entry> root = cr.from(Entry.class);

            cr.select(root).where(cb.between(root.get("startTime"), entryFilter.getStartTime(), entryFilter.getEndTime())).orderBy(cb.desc(root.get("startTime")));
            Query query = session.createQuery(cr);
            return query.getResultList();
        }
    }


    @PostMapping("/weightByDay")
    public List<WeightByDay> findWeightByDay(@RequestBody WeightGraphFilter entryFilter) {
        return statsRepository.getWeightByDay(entryFilter.getStartTime().toString(), entryFilter.getEndTime().toString(), entryFilter.getGraphType());
    }

    // get employee by id rest api
    @GetMapping("/entries/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable Long id) throws Exception {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee not exist with id :" + id));
        return ResponseEntity.ok(entry);
    }

    // update employee rest api

    @PutMapping("/entries/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody UpdateEntry updateEntry) throws Exception {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee not exist with id :" + id));

        entry.getData().put("durationInSeconds", updateEntry.getDurationInSeconds());
        Entry updatedEntry = entryRepository.save(entry);

        return ResponseEntity.ok(updatedEntry);
    }

    // delete employee rest api
//    @DeleteMapping("/employees/{id}")
//    public ResponseEntity<Map<String, Boolean>> deleteEntry(@PathVariable Long id) {
//        Employee employee = entryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
//
//        entryRepository.delete(employee);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return ResponseEntity.ok(response);
//        return ResponseEntity.ok(new HashMap<>());
//    }
}