package com.example.controller;

import com.example.entity.StudentEntity;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/add")
    public Boolean addStudent(@RequestBody StudentEntity dto) {
        return studentService.addStudent(dto);

    }

    @GetMapping("/all")
    public List<StudentEntity> all() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentEntity getBookById(@PathVariable("id") Integer id){
        return studentService.getStudentById(id);

    }

    @DeleteMapping("/{id}")
    public boolean deleteBookById(@PathVariable("id") Integer id){
        return studentService.deleteStudentById(id);
    }

    @PutMapping("/update/{id}")
    public Boolean update(@RequestBody StudentEntity dto, @PathVariable("id") Integer id) {
      return studentService.updateStudent(dto,id);
    }

}
