package com.example.orderDetail.client;

//import com.example.orderDetail.dto.StudentDto;
import com.example.orderDetail.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "http://localhost:8082")
public interface StudentClient {

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable Long id);


}
