package com.example.educationplatform.repository;

import com.example.educationplatform.entity.CourseFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseFileRepository extends JpaRepository<CourseFile,Long> {
    List<CourseFile> getAllByCourseId(Long courseId);

}
