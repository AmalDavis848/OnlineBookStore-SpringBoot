//package com.example.book_services.controller;
//
//import com.example.book_services.entity.Book;
//import com.example.book_services.service.BookService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/books")
//public class BookController {
//    @Autowired
//    private BookService bookService;
//
//    @GetMapping("/{id}")
//    public Book getBookById(@PathVariable Long id) {
//        return bookService.getBookById(id);
//    }
//
//    @GetMapping("/all")
//    public List<Book> getAllBooks() {
//        return bookService.getAllBooks();
//    }
//    @PostMapping("/add")
//    public Book addBook(@RequestBody Book book) {
//        return bookService.saveBook(book);
//    }
//
//    @PutMapping("/{id}/count")
//    public Book updateBookCount(@PathVariable Long id, @RequestParam int count) {
//        return bookService.updateBookCount(id, count);
//    }
//
//    @GetMapping("/zero")
//    public List<Book> getBooksWithZeroCount() {
//        return bookService.getBookZero();
//    }
//
//    @GetMapping("/positive")
//    public List<Book> getBooksWithPositiveCount() {
//        return bookService.getBookLarger();
//    }
//
//}
//

package com.example.book_services.controller;

import com.example.book_services.entity.Book;
import com.example.book_services.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/public/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/public/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
}