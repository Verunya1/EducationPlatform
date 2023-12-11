package com.example.educationplatform.repository;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> getCourseById(Long courseId);

}
