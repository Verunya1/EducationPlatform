package com.example.educationplatform.repository;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseFile;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse,Long> {
    List<User> getAllByUserId(Long courseId);
    List<Course> getAllByCourseId(Long userId);
}
