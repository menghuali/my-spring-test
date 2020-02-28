package com.westjet.middleware.roomservices.repository;

import com.westjet.middleware.roomservices.RoomServicesApplication;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * RoomRepoIntegrationTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoomServicesApplication.class)
@ContextConfiguration(initializers = RoomRepoIntegrationTests.Initializer.class)
public class RoomRepoIntegrationTests {
    @ClassRule
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>().withUsername("sa").withPassword("sa")
            .withInitScript("classpath:room_init.sql");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + postgres.getJdbcUrl(),
                            "spring.datasource.username=" + postgres.getUsername(),
                            "spring.datasource.password=" + postgres.getPassword())
                    .applyTo(applicationContext.getEnvironment());
        }
    }

}