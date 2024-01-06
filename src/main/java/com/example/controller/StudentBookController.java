package com.example.controller;

import com.example.entity.BookEntity;
import com.example.entity.StudentBookEntity;
import com.example.entity.StudentEntity;
import com.example.service.StudentBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-book/")
public class StudentBookController {
    private final StudentBookService studentBookService;

    @Autowired
    public StudentBookController(StudentBookService studentBookService) {
        this.studentBookService = studentBookService;
    }

    @PostMapping("/taking-book")
    public Boolean takingBookForStudent(@RequestBody StudentBookEntity studentBookEntity){
        return studentBookService.takingBook(studentBookEntity.getStudentId().getId(), studentBookEntity.getBookId().getId());
    }
    @PutMapping("/returning-book/{id}")
    public Boolean returningBook( @PathVariable("id") Integer id){
        return studentBookService.returningBook(id);
    }

    @GetMapping("/all")
    public List<StudentBookEntity> getAll(){
        return studentBookService.getAll();
    }
    @GetMapping("/{id}")
    public StudentBookEntity getBookById(@PathVariable("id") Integer id){
        return studentBookService.getBookBYId(id);
    }

    @GetMapping("/student/{id}")
    public List<BookEntity> return_All_Student_Taken_Book(@PathVariable("id") Integer id){
        return studentBookService.return_All_Student_Taken_Book(id);
    }

    @GetMapping("/book/taking-book-list")
    public List<BookEntity>return_All_Book_Taken_List (){
        return studentBookService.return_All_Book_Taken_List();
    }

}
