package com.example.educationplatform.repository;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseFile;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse,Long> {

    List<UserCourse> getAllByUserId(Long userId);

    Optional<UserCourse> findByUserIdAndCourseId(Long userId, Long courseId);

    //    List<UserCourse> getAllByUserId(Long courseId);
//
//    UserCourse getCourseById(Long courseId);
//
//    UserCourse findByUserId(Long user);
//    UserCourse findByCourseId(Long courseId);
//
//    List<UserCourse> getAllByCourseId(Long userId);
//    List<Course> getAllByUserCourseId(Long userId);
////    List<UserCourse> getAllByCourseId(Long userId);
}
