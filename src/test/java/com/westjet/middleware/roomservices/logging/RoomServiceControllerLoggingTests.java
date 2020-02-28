package com.westjet.middleware.roomservices.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.westjet.middleware.roomservices.controller.RoomController;
import com.westjet.middleware.roomservices.model.Room;
import com.westjet.middleware.roomservices.service.RoomService;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * RoomServiceControllerLoggingTests
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class)
@Import(ControllerLoggingAspect.class)
@WithMockUser(roles = { "ADMIN" })
@TestPropertySource(properties = "logging.config=classpath:logback-static-appender.xml")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class RoomServiceControllerLoggingTests {

    @MockBean
    private RoomService service;

    @Autowired
    private MockMvc mvc;

    @After
    public void cleanUp() {
        TestAppender.clearEvents();
    }

    @Test
    public void loggingForSuccessfulRequest() throws Exception {
        when(service.findByRoomNumber(anyString())).thenReturn(new Room("100"));
        mvc.perform(get("/rooms/100")).andExpect(status().isOk());
        assertThat(TestAppender.getEvents()).extracting("message").contains("Entering Method: findByRoomNumber",
                "Exiting Method: findByRoomNumber");
    }

    @Test
    public void loggingWhenExpectionBeingThrown() throws Exception {
        when(service.findByRoomNumber(anyString())).thenThrow(new RuntimeException("Internal Server Error"));
        mvc.perform(get("/rooms/100")).andExpect(status().isInternalServerError());
        assertThat(TestAppender.getEvents()).extracting("message").contains("Entering Method: findByRoomNumber",
                "Exiting Method: findByRoomNumber");
    }

}