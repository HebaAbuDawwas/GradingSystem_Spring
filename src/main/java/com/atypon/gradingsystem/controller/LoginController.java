package com.atypon.gradingsystem.controller;

import com.atypon.gradingsystem.models.Student;
import com.atypon.gradingsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Student student = studentService.validateUserLogin(username, password);
        if (student != null) {
            model.addAttribute("studentName", student.getName());
            studentService.getMarks(student);
            model.addAttribute("mapData", studentService.getMarks(student));
            return "list_view";
        } else {
            model.addAttribute("errorMessage", "Invalid credentials");
            return "login";
        }
    }

}
