package com.example.educationplatform.service;

import com.example.educationplatform.entity.UserCourse;
import com.example.educationplatform.repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCourseService /*implements UserCourseServiceInterface*/{
    private final UserCourseRepository userCourseRepository;


//    public UserCourse getCourseById(Long courseId) {
//        return userCourseRepository.findById(courseId).orElseThrow();
//    }
//
//    public List<UserCourse> getAllByCourseId(Long userId) {
//        return userCourseRepository.getAllByCourseId(userId);
//    }
//
//
//    public  List<UserCourse> getAllByUserId(Long courseId) {
//        return userCourseRepository.getAllByUserId(courseId);
//    }
//    public  List<Course> getAllByUserCourseId(Long userId) {
//        return userCourseRepository.getAllByUserCourseId(userId);
//    }
}
