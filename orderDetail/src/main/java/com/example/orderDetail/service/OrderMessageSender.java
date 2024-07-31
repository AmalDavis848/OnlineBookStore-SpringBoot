package com.example.orderDetail.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendBorrowMessage(Long studentId, Long bookId) {
        Map<String, Long> message = new HashMap<>();
        message.put("studentId", studentId);
        message.put("bookId", bookId);


        jmsTemplate.convertAndSend("borrow.queue", message);
    }

    public void sendReturnMessage(Long orderId) {
        jmsTemplate.convertAndSend("return.queue", orderId);
    }
}
