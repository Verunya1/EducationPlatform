package com.example.educationplatform.service;

import com.example.educationplatform.entity.User;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.UserCourseRepository;
import com.example.educationplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserCourseRepository userCourseRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.get(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @InjectMocks
    private UserService userService;

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void add() {
    }

    @Test
    void authorise() {
    }

    @Test
    void subscribeToCourse() {
    }

    @Test
    void buyCourseById() {
    }

    @Test
    void getMyCourse() {
    }
}