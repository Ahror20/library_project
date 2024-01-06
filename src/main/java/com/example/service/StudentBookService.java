package com.example.service;

import com.example.entity.BookEntity;
import com.example.entity.StudentBookEntity;
import com.example.entity.StudentEntity;
import com.example.enums.StudentBookStatus;
import com.example.repository.BookRepository;
import com.example.repository.StudentBookRepository;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentBookService {
    private final StudentBookRepository studentBookRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public StudentBookService(StudentBookRepository studentBookRepository, StudentRepository studentRepository, BookRepository bookRepository) {
        this.studentBookRepository = studentBookRepository;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;
    }

    public Boolean takingBook(Integer studentId, Integer bookId) {
        StudentEntity student = studentRepository.getStudentByID(studentId);
        BookEntity book = bookRepository.getBookById(bookId);
        if (student == null || book == null) {
            return null;
        }
        StudentBookEntity studentBookEntity = new StudentBookEntity();
        studentBookEntity.setStudentId(student);
        studentBookEntity.setBookId(book);
        studentBookEntity.setCreatedDate(LocalDateTime.now());
        studentBookEntity.setStatus(StudentBookStatus.TAKEN);
        studentBookEntity.setReturnedDate(null);
        studentBookEntity.setDuration(10);
        return studentBookRepository.takingBook(studentBookEntity);
    }

    public Boolean returningBook(Integer id) {
        StudentBookEntity studentBook = studentBookRepository.getStudentBookById(id);
        if (studentBook == null) {
            return null;
        }
        if (studentBook.getStatus().equals(StudentBookStatus.RETURNED)) {
            return null;
        }
        StudentBookEntity studentBookEntity = new StudentBookEntity();
        studentBookEntity.setStudentId(studentBook.getStudentId());
        studentBookEntity.setBookId(studentBook.getBookId());
        studentBookEntity.setCreatedDate(studentBook.getCreatedDate());
        studentBookEntity.setDuration(studentBook.getDuration());
        studentBookEntity.setReturnedDate(LocalDateTime.now());
        studentBookEntity.setStatus(StudentBookStatus.RETURNED);
        return studentBookRepository.returningBook(studentBookEntity, id);
    }

    public List<StudentBookEntity> getAll() {
        List<StudentBookEntity> list = studentBookRepository.getAll();
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public StudentBookEntity getBookBYId(Integer id) {
        List<StudentBookEntity> list = studentBookRepository.getAll();
        return list.stream()
                .filter(studentBookEntity -> studentBookEntity.getId().equals(id))
                .findAny()
                .orElse(null);

    }

    public List<BookEntity> return_All_Student_Taken_Book(Integer id) {
        StudentEntity student =studentRepository.getStudentByID(id);
        if (student == null){
            return null;
        }
        List<StudentBookEntity> list = studentBookRepository.getAll();
        List<BookEntity> bookList = new ArrayList<>();
       for (StudentBookEntity studentBookEntity:list){
           if (studentBookEntity.getStudentId().getId().equals(student.getId())){
               bookList.add(studentBookEntity.getBookId());
           }
       }
       return bookList;
    }

    public List<BookEntity> return_All_Book_Taken_List() {
        List<StudentBookEntity> list =studentBookRepository.getAll();
        Set<BookEntity> bookEntities  = new HashSet<>();
        for (StudentBookEntity studentBookEntity:list){
            if (studentBookEntity != null && studentBookEntity.getStatus().equals(StudentBookStatus.TAKEN)){
                bookEntities.add(studentBookEntity.getBookId());
            }
        }
        return new ArrayList<>(bookEntities);
    }
}
