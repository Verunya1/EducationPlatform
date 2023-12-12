package com.example.educationplatform.service;

import com.example.educationplatform.entity.UserCourse;

import java.util.List;

public interface UserCourseServiceInterface {
    List<UserCourse> getAllByUserId(Long courseId);

    UserCourse getCourseById(Long courseId);

    List<UserCourse> getAllByCourseId(Long userId);
}
