package com.example.service;

import com.example.entity.BookEntity;
import com.example.entity.StudentEntity;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Boolean addStudent(StudentEntity dto) {
        if (dto.getPhone()==null || dto.getPhone().isBlank()){
            return null;
        }

        StudentEntity student = studentRepository.getStudentByPhone(dto.getPhone());
        System.out.println(student);
        if (student != null){
            return null;
        }

        dto.setCreatedDate(LocalDateTime.now());
        return studentRepository.saveStudent(dto);
    }

    public List<StudentEntity> getAll() {
        List<StudentEntity> list = studentRepository.getAll();
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public StudentEntity getStudentById(Integer id) {
        List<StudentEntity> list = studentRepository.getAll();
        return list.stream()
                .filter(studentEntity -> studentEntity.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public boolean deleteStudentById(Integer id) {
        return studentRepository.deleteStudentById(id);
    }

    public Boolean updateStudent(StudentEntity dto, Integer id) {
        StudentEntity student1 = studentRepository.getStudentByPhone(dto.getPhone());
        if (dto.getPhone()==null || dto.getPhone().isBlank()){
            return null;
        }
        if (student1 != null){
            return null;
        }
        List<StudentEntity> list = studentRepository.getAll();
        for (StudentEntity student:list){
            if (student != null && student.getId().equals(id)){
                student.setName(dto.getName());
                student.setSurname(dto.getSurname());
                student.setPhone(dto.getPhone());
                return studentRepository.updateStudent(dto,id);

            }
        }
        return false;
    }
}
