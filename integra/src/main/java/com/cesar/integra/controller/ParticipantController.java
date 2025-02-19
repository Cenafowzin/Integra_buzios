package com.cesar.integra.controller;

import com.cesar.integra.model.Group;
import com.cesar.integra.model.Participant;
import com.cesar.integra.model.Registration;
import com.cesar.integra.service.GroupService;
import com.cesar.integra.service.ParticipantService;
import com.cesar.integra.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/newParticipant")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant){
        Optional<Group> group = groupService.findById(participant.getGroup().getId());
        if (group.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Optional<Registration> registration = registrationService.findById(participant.getRegistration().getId());
        if(registration.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Participant newParticipant = new Participant(group.get(), participant.getRegistration());

        Participant savedParticipant = participantService.save(newParticipant);
        return ResponseEntity.ok(savedParticipant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipant(@PathVariable int id) {
        Optional<Participant> participant = participantService.find(id);
        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getAllParticipants() {
        List<Participant> participants = participantService.findAll();
        return ResponseEntity.ok(participants);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Participant> updateParticipant(@PathVariable int id, @RequestBody Participant participant){
        Optional.ofNullable(participant)
                .filter(p -> p.getId() != 0)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Participant ID"));

        return participantService.find(id)
                .map(existingGuide -> {
                    participantService.save(participant);
                    return ResponseEntity.ok(participant);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Participant> deleteParticipant(@RequestParam int id) {
        Optional<Participant> existingParticipant = participantService.find(id);
        if (existingParticipant.isPresent()) {
            participantService.delete(id);
            return ResponseEntity.noContent().build();
        }else{
            return  ResponseEntity.notFound().build();
        }
    }
}
