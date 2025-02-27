package com.cesar.integra.controller;

import com.cesar.integra.model.Activity;
import com.cesar.integra.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("/newActivity")
    public ResponseEntity<Activity> newActivity(@RequestBody Activity activity) {
        Optional.ofNullable(activity)
                .filter(a -> a.getTitle() != null && !a.getTitle().trim().isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Activity title cannot be null or empty"));

        Activity savedActivity = activityService.save(activity);
        return ResponseEntity.ok(savedActivity);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Activity> getActivity(@PathVariable String title) {
        Optional<Activity> activity = activityService.find(title);
        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.findAll();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/titleAndStatus")
    public ResponseEntity<List<Map<String, Object>>> getNameAndStatus() {
        List<Map<String, Object>> activities = activityService.findAll().stream()
                .map(Activity::nameAndStatus)
                .toList();

        return ResponseEntity.ok(activities);
    }

    @GetMapping("/byEvent/{eventName}")
    public ResponseEntity<List<Activity>> getActivitiesByEvent(@PathVariable String eventName) {
        List<Activity> activities = activityService.findActivitiesByEvent(eventName);
        return ResponseEntity.ok(activities);
    }

    @PutMapping(("/{title}/edit"))
    public ResponseEntity<Activity> uptadeActivity(@PathVariable String title, @RequestBody Activity activity) {
        Optional.ofNullable(activity)
                .filter(a -> a.getTitle() != null && !a.getTitle().trim().isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Activity title cannot be null or empty"));

        return activityService.find(title)
                .map(existingActivity -> {
                    activityService.save(activity);
                    return ResponseEntity.ok(activity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deleteActivity(@PathVariable String title) {
        Optional<Activity> existingActivity = activityService.find(title);

        if (existingActivity.isPresent()) {
            activityService.delete(title);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
