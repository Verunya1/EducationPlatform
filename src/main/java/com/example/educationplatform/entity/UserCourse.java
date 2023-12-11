package com.example.educationplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@Table(name = "user_course")
@AllArgsConstructor
public class UserCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

//    @Column(nullable = false, insertable = false, columnDefinition = "VARCHAR(255) DEFAULT 'не оплачен'")
    private String paymentStatus; // оплачен курс или нет\ не оплачен, ждет оплаты, оплачен

//    @Column(nullable = false, insertable = false, columnDefinition = "VARCHAR(255) DEFAULT 'пройти'")
    private String status; //пройти, мое обучение, пройден


//    private Long userId;
//    private Long courseId;
}
