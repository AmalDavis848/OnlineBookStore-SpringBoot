

package com.example.orderDetail.service;

import com.example.orderDetail.client.BookClient;
import com.example.orderDetail.client.StudentClient;
import com.example.orderDetail.dto.*;
import com.example.orderDetail.entity.Book;
import com.example.orderDetail.entity.OrderDetail;
import com.example.orderDetail.entity.Student;
import com.example.orderDetail.exception.*;
import com.example.orderDetail.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private BookClient bookClient;

    public OrderDetailDto borrowBook(Long studentId, Long bookId) {
        logger.info("Borrow book request: studentId={}, bookId={}", studentId, bookId);

        Student student = studentClient.getStudentById(studentId);
        if (student == null) {
            logger.error("Student not found with ID: {}", studentId);
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        Book book = bookClient.getBookById(bookId);
        if (book == null) {
            logger.error("Book not found with ID: {}", bookId);
            throw new ResourceNotFoundException("Book not found with ID: " + bookId);
        }

        if (book.getCount() <= 0) {
            logger.error("Book is not available: bookId={}", bookId);
            throw new BookUnavailableException("Book is not available");
        }

        OrderDetail order = new OrderDetail();
        order.setStudentId(studentId);
        order.setBookId(bookId);
        order.setPurchaseDate(LocalDate.now());
        order.setDueDate(LocalDate.now().plusWeeks(1));
        order.setStatus("borrowed");

        OrderDetail savedOrder = orderRepository.save(order);
        bookClient.updateBookCount(bookId, book.getCount() - 1);

        return convertToDTO(savedOrder, student, book);
    }

    public OrderDetailDto returnBook(Long orderId) {
        logger.info("Return book request: orderId={}", orderId);

        Optional<OrderDetail> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent()) {
            logger.error("Order not found with ID: {}", orderId);
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }

        OrderDetail order = optionalOrder.get();

        if ("returned".equals(order.getStatus())) {
            logger.error("Book has already been returned: orderId={}", orderId);
            throw new RunTimeBookException("Book has already been returned");
        }

        Book book = bookClient.getBookById(order.getBookId());
        bookClient.updateBookCount(order.getBookId(), book.getCount() + 1);

        order.setStatus("returned");

        OrderDetail updatedOrder = orderRepository.save(order);

        Student student = studentClient.getStudentById(order.getStudentId());

        return convertToDTO(updatedOrder, student, null);
    }

        public List<OrderDetailDto> getAllOrders() {
        logger.info("Get all orders request");

        List<OrderDetail> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            Student student = studentClient.getStudentById(order.getStudentId());
            Book book = order.getStatus().equals("returned") ? null : bookClient.getBookById(order.getBookId());
            return convertToDTO(order, student, book);
        }).collect(Collectors.toList());
    }

    private OrderDetailDto convertToDTO(OrderDetail order, Student student, Book book) {
        OrderDetailDto dto = new OrderDetailDto();
        dto.setOrderId(order.getOrderId());
        dto.setPurchaseDate(order.getPurchaseDate());
        dto.setDueDate(order.getDueDate());
        dto.setStatus(order.getStatus());

        StudentDto studentDTO = new StudentDto();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setbranch(student.getbranch());
        dto.setStudent(studentDTO);

        if (book != null) {
            BookDto bookDTO = new BookDto();
            bookDTO.setId(book.getId());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setCount(book.getCount());
            dto.setBook(bookDTO);
        } else {
            dto.setBook(null);
        }

        return dto;
    }

    public List<StudentBookDto> getAllStudentBooks() {
        logger.info("Get all student books request");

        List<OrderDetail> orders = orderRepository.findAll();
        Map<String, StudentBookDto> studentBooksMap = new HashMap<>();

        for (OrderDetail order : orders) {

            if (!"returned".equals(order.getStatus())) {
                Student student = studentClient.getStudentById(order.getStudentId());
                Book book = bookClient.getBookById(order.getBookId());

                String studentId = student.getId().toString();
                StudentBookDto studentBooksDto = studentBooksMap.get(studentId);
                if (studentBooksDto == null) {
                    studentBooksDto = new StudentBookDto();
                    studentBooksDto.setStudentId(studentId);
                    studentBooksDto.setStudentName(student.getName());
                    studentBooksDto.setStudentBranch(student.getbranch());
                    studentBooksDto.setBooks(new ArrayList<>());
                    studentBooksMap.put(studentId, studentBooksDto);
                }

                BookDto bookDto = new BookDto();
                bookDto.setId(book.getId());
                bookDto.setTitle(book.getTitle());
                bookDto.setAuthor(book.getAuthor());
                bookDto.setCount(book.getCount());

                studentBooksDto.getBooks().add(bookDto);
            }
        }

        return new ArrayList<>(studentBooksMap.values());
    }

}
