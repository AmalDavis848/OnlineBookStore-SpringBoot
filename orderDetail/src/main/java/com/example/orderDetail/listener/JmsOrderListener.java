package com.example.orderDetail.listener;

import com.example.orderDetail.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
public class JmsOrderListener {

    private static final Logger logger = LoggerFactory.getLogger(JmsOrderListener.class);

    @Autowired
    private OrderService orderService;

    @JmsListener(destination = "borrow.queue")
    public void handleBorrowMessage(Map<String, Long> message) {
        Long studentId = message.get("studentId");
        Long bookId = message.get("bookId");
        logger.info("Received borrow message: studentId={}, bookId={}", studentId, bookId);
        try {
            orderService.borrowBook(studentId, bookId);
        } catch (Exception e) {
            logger.error("Failed to process borrow order", e);
        }
    }

    @JmsListener(destination = "return.queue")
    public void handleReturnMessage(Long orderId) {
        logger.info("Received return message: orderId={}", orderId);
        try {
            orderService.returnBook(orderId);
        } catch (Exception e) {
            logger.error("Failed to process return order", e);
        }
    }
}
