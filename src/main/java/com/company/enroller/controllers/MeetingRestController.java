package com.company.enroller.controllers;


import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {

        Meeting meeting = meetingService.findById(id);

        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting){

        if (meetingService.findById(meeting.getId()) != null){
            return new ResponseEntity("Unable to create. A participant with ID " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);
        }

        meetingService.addMeeting(meeting);

        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantToMeeting(@PathVariable("id") long id, @RequestBody Participant participant){


        Meeting meeting = meetingService.findById(id);

        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        meeting.addParticipant(participant);


        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingParticipants(@PathVariable("id") long id){


        Meeting meeting = meetingService.findById(id);

        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Collection<Participant> participants = meeting.getParticipants();


        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id){


        Meeting meeting = meetingService.findById(id);

        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        meetingService.deleteMeeting(meeting);

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> putMeeting(@PathVariable("id") long id, @RequestBody Meeting meeting) {

        meeting.setId(id);


        if (meetingService.findById(id) == null){
            return new ResponseEntity("Unable to put. A meeting with id " + meeting.getId() + " does not exist.", HttpStatus.NOT_FOUND);
        }

        meetingService.putMeeting(meeting);

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeetingParticipant(@PathVariable("id") long id, @RequestBody Participant participant){


        Meeting meeting = meetingService.findById(id);

        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        meeting.removeParticipant(participant);

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }
}
