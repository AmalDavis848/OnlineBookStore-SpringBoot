//package com.example.student_services.controller;
//
//import com.example.student_services.entity.Student;
//import com.example.student_services.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/students")
//public class StudentController {
//    @Autowired
//    private StudentService studentService;
//
//
//    @GetMapping("/{id}")
//    public Student getStudentById(@PathVariable long id) {
//        return studentService.getStudentById(id);
//    }
//
//    @PostMapping("/add")
//    public Student createStudent(@RequestBody Student student) {
//        return studentService.addStudent(student);
//    }
//
//    @GetMapping("/all")
//    public List<Student> getAllStudents() {
//        return studentService.getAllStudents();
//    }
//
//
//}


package com.example.student_services.controller;

import com.example.student_services.dto.LoginRequest;
import com.example.student_services.dto.RegisterRequest;
import com.example.student_services.entity.Student;
import com.example.student_services.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/register")
    public Student register(@RequestBody RegisterRequest registerRequest) {
        return studentService.register(registerRequest);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return studentService.login(loginRequest);
    }
}
