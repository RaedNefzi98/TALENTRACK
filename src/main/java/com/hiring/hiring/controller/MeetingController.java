package com.hiring.hiring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
;

@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/api/meeting")
@RestController
public class MeetingController {
    @Autowired
    private IserviceMeeting iserviceMeeting;

    @PostMapping("/")
    public ResponseEntity<Meeting> createMeeitng(@RequestBody Meeting m)  {
        return new ResponseEntity<>(iserviceMeeting.addMeeting(m), HttpStatus.OK);

    }


    @GetMapping("/getMeetingsByFormation/{idFormation}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MeetingDTO> getMeetingsByFormation(@PathVariable("idFormation") Long idFormation) {
        return iserviceMeeting.getMeetingsByFormation(idFormation);

    }

    @GetMapping("/getMeetingsByUser/{idUser}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MeetingDTO> getMeetingsByUser(@PathVariable("idUser") Long idUser) {
        return iserviceMeeting.getMeetingsByUser(idUser);

    }
}
