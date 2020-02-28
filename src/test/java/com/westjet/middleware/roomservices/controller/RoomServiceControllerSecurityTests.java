package com.westjet.middleware.roomservices.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.westjet.middleware.roomservices.model.Room;
import com.westjet.middleware.roomservices.service.RoomService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * RoomServiceControllerSecurityTests
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class)
public class RoomServiceControllerSecurityTests {

    @MockBean(name = "service")
    private RoomService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void findRoomWithoutAuthentica() throws Exception {
        Room room = new Room();
        room.setRoom_number("100");
        when(service.findByRoomNumber(anyString())).thenReturn(room);
        mvc.perform(get("/rooms/100")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void findRoomWithUserWithoutRightRole() throws Exception {
        Room room = new Room();
        room.setRoom_number("100");
        when(service.findByRoomNumber(anyString())).thenReturn(room);
        mvc.perform(get("/rooms/100")).andExpect(status().isForbidden());
    }

}