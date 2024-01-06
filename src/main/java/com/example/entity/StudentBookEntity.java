package com.example.entity;


import com.example.enums.StudentBookStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "student_book")
public class StudentBookEntity  extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentId;
    @OneToOne
    @JoinColumn(name = "book_id")
    private BookEntity bookId;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StudentBookStatus status;
    @Column(name = "returned_date")
    private LocalDateTime returnedDate;
    @Column(name = "duration")
    private int  duration;
}
