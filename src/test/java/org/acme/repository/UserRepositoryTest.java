package org.acme.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.acme.exceptions.UserAlreadyFoundException;
import org.acme.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userRepository.addUser(new UserModel("uuid-1", "User1", "user1@user", null, null));
        userRepository.addUser(new UserModel("uuid-2", "User2", "user2@user", null, null));
        userRepository.addUser(new UserModel("uuid-3", "User3", "user3@user", null, null));
    }

    @Test
    @DisplayName("should reject adding duplicated user")
    void mustRejectAddingDuplicatedUser() {
        UserModel duplicatedUser = new UserModel("uuid-1", "User1", "user1@user", null, null);
        assertThrows(UserAlreadyFoundException.class, () -> {
            userRepository.addUser(duplicatedUser);
        });
    }

    @Test
    @DisplayName("should not find user by ID")
    void mustNotFindUserById() {
        Optional<UserModel> result = userRepository.findById("non-existent-uuid");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should find user by ID")
    void mustFindUserById() {
        Optional<UserModel> result = userRepository.findById("uuid-2");
        assertTrue(result.isPresent());
        assertEquals("User2", result.get().name());
    }

    @Test
    @DisplayName("should return empty when ID does not exist")
    void shouldReturnEmptyWhenIdDoesNotExist() {
        Optional<UserModel> result = userRepository.findById("999");
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should return the first page with the requested size")
    void shouldPaginateFirstPageCorrectly() {
        Set<UserModel> page0 = userRepository.findAll(0, 2);
        assertEquals(2, page0.size());
    }

    @Test
    @DisplayName("should return a specific item on findAll")
    void shouldReturnRemainingItemsOnLastPage() {
        Set<UserModel> page0 = userRepository.findAll(0, 2);
        assertEquals(2, page0.size());
        assertTrue(page0.stream().anyMatch(u -> u.id().equals("uuid-2")));

        Set<UserModel> page1 = userRepository.findAll(1, 2);
        assertEquals(1, page1.size());
        assertTrue(page1.stream().anyMatch(u -> u.id().equals("uuid-3")));

        Set<UserModel> pageAll = userRepository.findAll(0, 100);
        assertEquals(3, pageAll.size());
        assertTrue(pageAll.stream().anyMatch(u -> u.id().equals("uuid-3")));
    }

    @Test
    @DisplayName("should return empty set when the page exceeds the total number of records")
    void shouldReturnEmptyWhenPageIsOutOfBounds() {
        Set<UserModel> pageOutOfBounds = userRepository.findAll(10, 2);
        assertTrue(pageOutOfBounds.isEmpty());
    }

    @Test
    @DisplayName("should return specific item by email")
    void shouldReturnSpecificItemByEmail() {
        Optional<UserModel> result = userRepository.findByEmail("user2@user");
        assertTrue(result.isPresent());
        assertEquals("User2", result.get().name());
    }

    @Test
    @DisplayName("should return specific item by id")
    void shouldReturnSpecificItemById() {
        Optional<UserModel> result = userRepository.findById("uuid-2");
        assertTrue(result.isPresent());
        assertEquals("User2", result.get().name());
    }
}
