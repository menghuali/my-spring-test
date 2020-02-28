package com.westjet.middleware.roomservices.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Room
 */
@Entity(name = "rooms")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    @Id
    private String room_number;

    public Room() {
    }

    public Room(String room_number) {
        this.room_number = room_number;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

}