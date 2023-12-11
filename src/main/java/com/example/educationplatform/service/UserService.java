package com.example.educationplatform.service;

import com.example.educationplatform.entity.Course;
import com.example.educationplatform.entity.User;
import com.example.educationplatform.entity.UserCourse;
import com.example.educationplatform.repository.CourseRepository;
import com.example.educationplatform.repository.UserCourseRepository;
import com.example.educationplatform.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CourseService courseService;
    private final UserCourseService userCourseService;
    //    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setMoney(0L);
        return userRepository.save(user);
    }

    public Boolean authorise(User user, HttpServletRequest req) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        Authentication auth = authenticationManager.authenticate(authReq);

        if (!auth.isAuthenticated()) {
            return false;
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

        return true;
    }

    public void subscribeToCourse(/*Long userId,*/ User user, Long courseId) {
//        User user = userRepository.findById(userId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
//        UserCourse userCourse = userCourseRepository.findById(courseId).orElseThrow();
//        course.setPaymentStatus("ждет оплаты");
//        course.set("ждет оплаты");
        //TODO сделать проверку на статус и статус оплаты, запретить это делать, мб удалить из списка курсы для записи, добавить эти курсы в избранное и мое обучение
        UserCourse userCourse = UserCourse.builder().user(user).course(course).status("пройти").paymentStatus("ждет оплаты").build();
        System.out.println(userCourse);
        userCourseRepository.save(userCourse);
    }

    public void buyCourseById(User user, Long courseId) {
        Course course = courseService.getCourseById(courseId);
        if (user.getMoney() < course.getPrice()) {
            return;
        }
        user.setMoney(user.getMoney() - course.getPrice());

        User owner = get(course.getUserId());

        owner.setMoney(owner.getMoney() + course.getPrice());

        UserCourse userCourse = new UserCourse();
        userCourse.setUser(user);
        userCourse.setCourse(course);
        userCourse.setPaymentStatus("оплачен");
        userCourse.setStatus("мое обучение");
        System.out.println(userCourse);
        userCourseRepository.save(userCourse);

    }


    public List<Course> getMyFavorites(User user) {
        List<UserCourse> courses = userCourseRepository.getAllByUserId(user.getId());
        List<Course> courses1 = new ArrayList<>();
        for (UserCourse cours : courses) {
            if (cours.getPaymentStatus().equals("ждет оплаты")) {
                courses1.add(cours.getCourse());
            }

        }
//        List<Course> courses = userRepository.getAllByCourseId(user.getId());
        return courses.stream()
                .filter(cours -> "ждет оплаты".equals(cours.getPaymentStatus()))
                .map(UserCourse::getCourse)
                .collect(Collectors.toList());
//                courses.stream()
//                .filter(userCourse -> "ждет оплаты".equals(userCourse.getStatus()))
//                .map(userCourse -> courseRepository.getCourseById(userCourse.getUser().getId()))
//                .collect(Collectors.toList());
    }
    public List<Course> getMyCourse(User user) {
        List<UserCourse> courses = userCourseRepository.getAllByUserId(user.getId());
//        UserCourse course = userCourseRepository.findById(23L).orElseThrow();
//        System.out.println(course);
//        System.out.println(courses);
//        List<Course> courses1 = new ArrayList<>();
//        for (UserCourse cours : courses) {
//            if (cours.getPaymentStatus().equals("оплачен")) {
//                courses1.add(cours.getCourse());
//            }
//
//        }
//        System.out.println(courses1);
////        return courses.stream()
////                .filter(userCourse -> "оплачен".equals(userCourse.getStatus()))
////                .map(userCourse -> courseRepository.getCourseById(userCourse.getUser().getId()))
////                .collect(Collectors.toList());
        return courses.stream()
                .filter(cours -> "оплачен".equals(cours.getPaymentStatus()))
                .map(UserCourse::getCourse)
                .collect(Collectors.toList());
    }
}
