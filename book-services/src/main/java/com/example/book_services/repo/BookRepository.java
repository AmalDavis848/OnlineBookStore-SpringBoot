package com.example.book_services.repo;

import com.example.book_services.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCount(int count);
    List<Book> findByCountGreaterThan(int count);

}
