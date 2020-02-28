package com.westjet.middleware.roomservices.service;

import java.security.InvalidParameterException;

import com.westjet.middleware.roomservices.model.Room;
import com.westjet.middleware.roomservices.repository.RoomRepo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

    private RoomRepo repo;

    @Autowired
    public RoomServiceImpl(RoomRepo repo) {
        this.repo = repo;
    }

    @Override
    public Room findByRoomNumber(String roomNumber) {
        if (StringUtils.isEmpty(roomNumber))
            throw new InvalidParameterException("The room number cannot be empty.");
        if (!StringUtils.isNumeric(roomNumber))
            throw new InvalidParameterException(
                    String.format("The format of the room number '%s' is invalid.", roomNumber));
        return repo.getOne(roomNumber);
    }

}