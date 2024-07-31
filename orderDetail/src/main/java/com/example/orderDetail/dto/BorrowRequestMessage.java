package com.example.orderDetail.dto;

public class BorrowRequestMessage {
    private Long studentId;
    private Long bookId;

    public BorrowRequestMessage() {
    }

    public BorrowRequestMessage(Long studentId, Long bookId) {
        this.studentId = studentId;
        this.bookId = bookId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
