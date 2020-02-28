package com.westjet.middleware.roomservices.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.westjet.middleware.roomservices.model.Room;
import com.westjet.middleware.roomservices.repository.RoomRepo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TestRoomServiceImpl
 */
public class RoomServiceImplTests {

    @Test
    @DisplayName("Test looking up existing room")
    public void lookupExistingRoom() {
        RoomRepo repo = mock(RoomRepo.class);
        when(repo.getOne(anyString())).thenReturn(new Room());

        RoomService service = new RoomServiceImpl(repo);
        Room room = service.findByRoomNumber("100");
        assertNotNull(room);
        // assertThat(room).isNotNull();
        assertAll(() -> assertNull(room));
    }

    @Test
    public void throwExceptionForNonExistingRoom() {
        RoomRepo repo = mock(RoomRepo.class);
        when(repo.getOne(anyString())).thenReturn(null);

        RoomService service = new RoomServiceImpl(repo);
        assertNull(service.findByRoomNumber("100"));
    }

    @Test
    public void throwExceptionInvalidRoomNumberFormat() {
        RoomRepo repo = mock(RoomRepo.class);
        when(repo.getOne(anyString())).thenReturn(new Room());
        RoomService service = new RoomServiceImpl(repo);
        try {
            service.findByRoomNumber("NOT A NUMBER");
            fail("Exception should had been thrown.");
        } catch (Exception e) {
            assertEquals("The format of the room number 'NOT A NUMBER' is invalid.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionInvalidRoomNumberNull() {
        RoomRepo repo = mock(RoomRepo.class);
        when(repo.getOne(anyString())).thenReturn(new Room());
        RoomService service = new RoomServiceImpl(repo);
        try {
            service.findByRoomNumber(null);
            fail("Exception should had been thrown.");
        } catch (Exception e) {
            assertEquals("The room number cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void throwExceptionInvalidRoomNumberEmpty() {
        RoomRepo repo = mock(RoomRepo.class);
        when(repo.getOne(anyString())).thenReturn(new Room());
        RoomService service = new RoomServiceImpl(repo);
        try {
            service.findByRoomNumber(null);
            fail("Exception should had been thrown.");
        } catch (Exception e) {
            assertEquals("The room number cannot be empty.", e.getMessage());
        }
    }

}