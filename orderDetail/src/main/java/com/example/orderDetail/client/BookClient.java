package com.example.orderDetail.client;

import com.example.orderDetail.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service", url = "http://localhost:8081")
public interface BookClient {
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id);

    @PutMapping("/books/{id}/count")
    Book updateBookCount(@PathVariable Long id, @RequestParam int count);

}
