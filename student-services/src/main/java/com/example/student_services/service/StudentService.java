//package com.example.student_services.service;
//
//import com.example.student_services.entity.Student;
//import com.example.student_services.repo.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class StudentService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    public List<Student> getAllStudents() {
//        return studentRepository.findAll();
//    }
//
//    public Student getStudentById(long id) {
//        return studentRepository.findById(id).get();
//
//    }
//
//    public Student addStudent(Student student) {
//        return studentRepository.save(student);
//    }
//
//
//}


package com.example.student_services.service;

import com.example.student_services.dto.RegisterRequest;
import com.example.student_services.dto.LoginRequest;
import com.example.student_services.entity.Student;
import com.example.student_services.repo.StudentRepository;
import com.example.student_services.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student register(RegisterRequest registerRequest) {
        Student student = new Student();
        student.setUsername(registerRequest.getUsername());
        student.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        student.setName(registerRequest.getName());
        student.setBranch(registerRequest.getBranch());
        return studentRepository.save(student);
    }

    public String login(LoginRequest loginRequest) {
        Optional<Student> existingStudent = studentRepository.findByUsername(loginRequest.getUsername());
        if (existingStudent.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), existingStudent.get().getPassword())) {
            return jwtUtil.generateToken(existingStudent.get().getUsername());
        }
        return "Invalid username or password";
    }
}

