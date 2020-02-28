package com.westjet.middleware.roomservices.controller;

import java.security.InvalidParameterException;

import com.westjet.middleware.roomservices.model.Room;
import com.westjet.middleware.roomservices.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private RoomService service;

    @GetMapping
    @RequestMapping("{roomNumber}")
    public ResponseEntity<?> findByRoomNumber(@PathVariable String roomNumber) {
        Room room = null;
        try {
            room = service.findByRoomNumber(roomNumber);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return room == null ? new ResponseEntity<String>("Cannot find the room.", HttpStatus.NOT_FOUND)
                : new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @Autowired
    public void setService(RoomService service) {
        this.service = service;
    }

}
