package com.atypon.gradingsystem.service;

import com.atypon.gradingsystem.dao.StudentDao;
import com.atypon.gradingsystem.models.Course;
import com.atypon.gradingsystem.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public Student validateUserLogin(String username, String password) {
        Student student = studentDao.getByUsernameAndPassword(username, password);
        return student;
    }

    public Map<String, Integer> getMarks(Student student) {
        Map<String, Integer> result = new HashMap<>();
        for (Course course : student.getCourses()) {
            result.put(course.getName(), studentDao.getMark(student, course));
        }

        return result;
    }

    public void addInitialData() {
        studentDao.createInitData();
    }
}
