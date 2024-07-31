//package com.example.book_services.service;
//
//import com.example.book_services.entity.Book;
//import com.example.book_services.repo.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookService {
//    @Autowired
//    private BookRepository bookRepository;
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//
//    }
//    public Book getBookById(Long id) {
//        return bookRepository.findById(id).get();
//    }
//
//    public Book saveBook(Book book) {
//        return bookRepository.save(book);
//    }
//
//    public Book updateBookCount(Long id, int count) {
//        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
//        book.setCount(count);
//        return bookRepository.save(book);
//    }
//    public List<Book>getBookZero(){
//        return bookRepository.findByCount(0);
//
//    }
//
//    public List<Book>getBookLarger(){
//        return bookRepository.findByCountGreaterThan(0);
//    }
//
//}


package com.example.book_services.service;

import com.example.book_services.entity.Book;
import com.example.book_services.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
