package com.example.service;

import com.example.entity.BookEntity;
import com.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Boolean createBook(BookEntity dto) {
        BookEntity book = bookRepository.getBookByTitleAndAuthor(dto.getTitle(), dto.getAuthor());
        if (book != null){
            return null;
        }
        if (dto.getAuthor().length() < 2 || dto.getTitle().length() < 2) {
            return null;
        }

        dto.setPublishYear(LocalDateTime.now());
        return bookRepository.createBook(dto);
    }

    public List<BookEntity> getAll() {
        List<BookEntity> list = bookRepository.getAll();
        if (list.isEmpty()) {
            return null;
        }
        return list;


    }

    public BookEntity getBookBYId(Integer id) {
        List<BookEntity> list = bookRepository.getAll();
        return list.stream()
                .filter(bookEntity -> bookEntity.getId().equals(id))
                .findAny()
                .orElse(null);

    }

    public Boolean deleteBookById(Integer id) {
        return bookRepository.deleteBookById(id);
    }

    public Boolean updateBookById(BookEntity dto, Integer id) {
        List<BookEntity> list = bookRepository.getAll();
        for (BookEntity book:list){
            if (book != null && book.getId().equals(id)){
                book.setTitle(dto.getTitle());
                book.setAuthor(dto.getAuthor());
                return bookRepository.updateBookById(dto,id);

            }
        }
        return false;
    }
}

