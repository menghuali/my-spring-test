package com.westjet.middleware.roomservices.service;

import java.security.InvalidParameterException;

import com.westjet.middleware.roomservices.model.Room;

public interface RoomService {

	Room findByRoomNumber(String string) throws InvalidParameterException;

}
