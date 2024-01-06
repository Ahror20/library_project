package com.example.controller;


import com.example.entity.BookEntity;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
 private final BookService bookService;
   @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public Boolean create(@RequestBody BookEntity dto) {
     return bookService.createBook(dto);

    }
    @GetMapping("/all")
    public List<BookEntity> getAll(){
       return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable("id") Integer id){
        return bookService.getBookBYId(id);
    }
    @DeleteMapping("/{id}")
    public Boolean deleteBookById(@PathVariable("id") Integer id){
       return bookService.deleteBookById(id);
    }
    @PutMapping("/update/{id}")
    public Boolean update(@RequestBody BookEntity dto, @PathVariable("id") Integer id){
       return bookService.updateBookById(dto,id);
    }





}
