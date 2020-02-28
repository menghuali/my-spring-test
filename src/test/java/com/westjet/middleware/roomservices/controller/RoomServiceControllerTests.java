package com.westjet.middleware.roomservices.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.InvalidParameterException;

import com.westjet.middleware.roomservices.model.Room;
import com.westjet.middleware.roomservices.service.RoomService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * RoomServiceControllerTests
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class)
@WithMockUser(roles = {"ADMIN"})
public class RoomServiceControllerTests {

    @MockBean(name = "service")
    private RoomService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSuccessfulFindRoom() throws Exception {
        Room room = new Room();
        room.setRoom_number("100");
        when(service.findByRoomNumber(anyString())).thenReturn(room);
        mvc.perform(get("/rooms/100")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testInvalidRoomNumberFormat() throws Exception {
        String error = "The format of the room number 'NOT_NUMBER' is invalid.";
        when(service.findByRoomNumber(anyString()))
                .thenThrow(new InvalidParameterException(error));
        mvc.perform(get("/rooms/NOT_NUMBER")).andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(error));
    }

    @Test
    public void testRoomNotFound() throws Exception {
        when(service.findByRoomNumber(anyString())).thenReturn(null);
        mvc.perform(get("/rooms/100")).andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Cannot find the room."));
    }

}