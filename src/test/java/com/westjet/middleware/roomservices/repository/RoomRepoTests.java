package com.westjet.middleware.roomservices.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.westjet.middleware.roomservices.model.Room;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * RoomRepoTests
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepoTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepo repo;

    @Test
    public void getOne_ByName() {
        // given
        Room room = new Room();
        room.setRoom_number("123");
        entityManager.persistAndFlush(room);

        // when
        Room found = repo.getOne("123");

        // Then
        assertNotNull(found);
        assertEquals("123", found.getRoom_number());
    }

}