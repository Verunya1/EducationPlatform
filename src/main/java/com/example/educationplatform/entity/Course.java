package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "course_user",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
//    private List<User> userId;
    private String name; // название курса
    private Long price; // цена
    @Column(nullable = false, insertable = false, columnDefinition = "VARCHAR(255) DEFAULT 'пройти'")
    private String status; //пройти, мое обучение, пройден
/*    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp startCourse; //время начало курса
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp closeCourse; // время прохождения/ время завершения курса*/
    private String description; // описание курса
    @Column(nullable = false, insertable = false, columnDefinition = "VARCHAR(255) DEFAULT 'не оплачен'")
    private String paymentStatus; // оплачен курс или нет\ не оплачен, ждет оплаты, оплачен
    private Long userId;//    тот кто создал курс
}