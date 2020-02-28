package com.westjet.middleware.roomservices.repository;

import com.westjet.middleware.roomservices.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends JpaRepository<Room, String> {
}
