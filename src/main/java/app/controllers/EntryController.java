package app.controllers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.models.EntryFilter;
import app.repositories.EntryRepository;
import app.models.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping("/entries")
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @PostMapping("/entries")
    public Entry createEntry(@RequestBody Entry entry) {
        return entryRepository.save(entry);
    }

    @PostMapping("/entries/filters")
    public List<Entry> findEntryWithFilters(@RequestBody EntryFilter entry) {
        LocalDateTime startOfDay = entry.getStartTime().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime nextDay = startOfDay.plusDays(1);
        return entryRepository.findByStartTimeBetween(startOfDay, nextDay);
    }

    // get employee by id rest api
    @GetMapping("/entries/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable Long id) throws Exception {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee not exist with id :" + id));
        return ResponseEntity.ok(entry);
    }

    // update employee rest api

    @PutMapping("/employees/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody Entry entry) {
//        Employee employee = entryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
//
//        employee.setFirstName(entry.getFirstName());
//        employee.setLastName(entry.getLastName());
//        employee.setEmailId(entry.getEmailId());
//
//        Employee updatedEmployee = entryRepository.save(employee);
//        return ResponseEntity.ok(updatedEmployee);
        return ResponseEntity.ok(new Entry());
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEntry(@PathVariable Long id) {
//        Employee employee = entryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
//
//        entryRepository.delete(employee);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return ResponseEntity.ok(response);
        return ResponseEntity.ok(new HashMap<>());
    }
}