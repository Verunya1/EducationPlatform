package com.example.educationplatform.service;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseFile;
import com.example.educationplatform.repository.CourseFileRepository;
import com.example.educationplatform.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final FileService fileService;
    private final CourseFileRepository courseFileRepository;

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow();
    }

    public List<CourseFile> getAllImages(Long id) {
        return courseFileRepository.getAllByCourseId(id);
    }

    public Course addCourse(Course course, MultipartFile multipartFile1, MultipartFile multipartFile2) throws IOException {
//        course.setPaymentStatus("не оплачен");
        course = courseRepository.save(course);
        courseFileRepository.saveAll(List.of(
                new CourseFile(0L, course.getId(), fileService.saveFile("img_screensaver" + course.getId() + "_1", multipartFile1)),
                new CourseFile(0L, course.getId(), fileService.saveFile("vid" + course.getId() + "_2", multipartFile2))
//                new RealtyImage(0L, realty.getId(), fileService.saveFile("img" + realty.getId() + "_3", file3))
        ));
        return course;
    }

    public void deleteCourse(Long courseId) {
        courseFileRepository.deleteAll(courseFileRepository.getAllByCourseId(courseId));
        courseRepository.deleteById(courseId);
    }
//    public Course updateCourse(Long courseId, Course course, MultipartFile multipartFile1, MultipartFile multipartFile2) throws IOException {
//
//        Course updatingCourse = courseRepository.findById(courseId).orElseThrow();
//        updatingCourse.setPrice();
//        courseFileRepository.saveAll(List.of(
//                new CourseFile(0L, course.getId(), fileService.saveFile("img_screensaver" + course.getId() + "_1", multipartFile1)),
//                new CourseFile(0L, course.getId(), fileService.saveFile("vid" + course.getId() + "_2", multipartFile2))
////                new RealtyImage(0L, realty.getId(), fileService.saveFile("img" + realty.getId() + "_3", file3))
//        ));
//        course = courseRepository.save(course);
//        return course;
//    }


}
