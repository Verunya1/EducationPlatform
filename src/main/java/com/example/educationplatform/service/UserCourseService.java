package com.example.educationplatform.service;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseFile;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.repository.CourseFileRepository;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.UserCourseRepository;
import com.example.educationplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCourseService {
//        private final CourseRepository courseRepository;
//    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;

    public List<Course> getAllByCourseId(Long userId) {
        return userCourseRepository.getAllByCourseId(userId);
    }

    public  List<User> getAllByUserId(Long courseId) {
        return userCourseRepository.getAllByUserId(courseId);
    }
}
