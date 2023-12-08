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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CourseService courseService;
    private final UserCourseService userCourseService;
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

    public void subscribeToCourse(/*Long userId,*/ User user,Long courseId){
//        User user = userRepository.findById(userId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        course.setPaymentStatus("ждет оплаты");
//        course.set("ждет оплаты");
        UserCourse userCourse = UserCourse.builder().user(user).course(course).build();
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
        course.setPaymentStatus("оплачен");
        course.setStatus("мое обучение");

    }
    public List<Course> getMyCourse(User user){
        List<Course> courses = userCourseRepository.getAllByCourseId(user.getId());
        return courses.stream()
                .filter(course -> "оплачен".equals(course.getStatus()))
                .collect(Collectors.toList());
    }
}
