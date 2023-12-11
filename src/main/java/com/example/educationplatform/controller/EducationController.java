package com.example.educationplatform.controller;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.CourseFile;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.entity.UserCourse;
import com.example.educationplatform.repository.UserCourseRepository;
import com.example.educationplatform.service.CourseService;
import com.example.educationplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class EducationController {
    private final CourseService courseService;
    private final UserService userService;
    private final UserCourseRepository userCourseRepository;

    //получение всех курсов
    @GetMapping("/index")
    public String getCourse(Model model, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            model.addAttribute("user", user);
        }
        List<Course> courses = courseService.getAll();
        List<User> users = new ArrayList<>();
        List<CourseFile> courseFiles = new ArrayList<>();
        courses.forEach(course -> {
            users.add(userService.get(course.getUserId()));
            courseFiles.add(courseService.getAllImages(course.getId()).get(0));
        });
        users.addAll(courses.stream().map(r -> userService.get(r.getUserId())).collect(Collectors.toList()));
        model.addAttribute("courses", courses);
        model.addAttribute("users", users);
        model.addAttribute("files", courseFiles);
        return "ads";
    }
    @GetMapping("/course")
    public String courseInfo(@RequestParam("id") Long id, Model model, Authentication authentication) {
        Course course = courseService.getCourseById(id);
        User owner = userService.get(course.getUserId());
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            model.addAttribute("user", user);
        }
        List<CourseFile> courseFiles = courseService.getAllImages(id);
        model.addAttribute("course", course);
        model.addAttribute("images", courseFiles);
        model.addAttribute("owner", owner);
        return "course_info";
    }

    //добавить новый курс - могут добавить все
    @GetMapping("/course/new")
    public String newCourse(@ModelAttribute("course") /*@DateTimeFormat(pattern = "yyyy-MM-dd") */Course course, Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "add_course";
    }

    //добавить новый курс - могут добавить все - добавление файлов
    @PostMapping("/course")
    public String createCourse(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                      @ModelAttribute("advertisement") Course course, Authentication authentication)
            throws IOException {

        User user = (User) authentication.getPrincipal();
        course.setUserId(user.getId());
        courseService.addCourse(course, file1, file2);
        return "redirect:/index";
    }

    //добавить в избранное, чтобы потом купить
    @GetMapping("course/favourites")
    public String subscribeToCourse(/*@RequestParam("id") Long courseId*/ Model model, Authentication authentication)  {
        User user = (User) authentication.getPrincipal();
        List<Course> courses = userService.getMyFavorites(user);
//        System.out.println(courses);
//        model.addAttribute("courses", courses);
        List<User> users = new ArrayList<>();
        List<CourseFile> courseFiles = new ArrayList<>();
        courses.forEach(course -> {
            users.add(userService.get(course.getUserId()));
            courseFiles.add(courseService.getAllImages(course.getId()).get(0));
        });
        users.addAll(courses.stream().map(r -> userService.get(r.getUserId())).collect(Collectors.toList()));
        model.addAttribute("courses", courses);
        model.addAttribute("users", users);
        model.addAttribute("files", courseFiles);
        return "favourites";

    }
    @GetMapping("course/myEducation")
    public String myEducation(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
//        List<User> users = new ArrayList<>();
//        List<Course> courses = userService.getMyCourse(user);
//        List<CourseFile> courseFiles = new ArrayList<>();
//        courses.forEach(course -> {
////            users.add(userService.get(course.getUserId()));
//            courseFiles.add(courseService.getAllImages(course.getId()).get(0));
//        });
////        users.addAll(courses.stream().map(r -> userService.get(r.getUserId())).collect(Collectors.toList()));
//        model.addAttribute("courses", courses);
////        model.addAttribute("users", users);
//        model.addAttribute("files", courseFiles);
        List<Course> courses = userService.getMyCourse(user);
        List<User> users = new ArrayList<>();
        List<CourseFile> courseFiles = new ArrayList<>();
        courses.forEach(course -> {
            users.add(userService.get(course.getUserId()));
            courseFiles.add(courseService.getAllImages(course.getId()).get(0));
        });
        users.addAll(courses.stream().map(r -> userService.get(r.getUserId())).collect(Collectors.toList()));
        model.addAttribute("courses", courses);
        model.addAttribute("users", users);
        model.addAttribute("files", courseFiles);


//        return "redirect:course_info";
        return "my_education";
    }

    //покупка курса
    @GetMapping("/course/buy")
    public String buyCourse(@RequestParam("id") Long courseId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.buyCourseById(user, courseId);
        return "redirect:/my_education";
    }

    // удаление курса
    @GetMapping("/course/delete")
    public String deleteCourse(@RequestParam("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/index";
    }
}
