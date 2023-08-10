package com.atypon.gradingsystem.dao;

import com.atypon.gradingsystem.models.Course;
import com.atypon.gradingsystem.models.Mark;
import com.atypon.gradingsystem.models.Student;
import com.atypon.gradingsystem.models.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GradingSystemPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public Student getByUsernameAndPassword(String username, String password) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = cb.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        criteriaQuery.select(studentRoot);
        criteriaQuery.where(cb.equal(studentRoot.get("username"), username));
        Query query = entityManager.createQuery(criteriaQuery);
        if (!query.getResultList().isEmpty()) {
            Student student = (Student) query.getResultList().get(0);
            if (student.getPassword().equals(password)) return student;
        }
        return null;


    }

    public int getMark(Student student, Course course) {
        Query markQuery = entityManager.createQuery("SELECT m FROM Mark m WHERE m.student = :student AND m.course = :course");
        markQuery.setParameter("student", student);
        markQuery.setParameter("course", course);
        List<Mark> marks = markQuery.getResultList();

        return marks.isEmpty() ? -1 : marks.get(0).getMarks();
    }

    public void createInitData() {

        Teacher teacher1 = new Teacher("bashayrah", "m_bashayrah", "hrmad");

        Course course1 = new Course("MVC", "course mvc ", teacher1);
        Course course2 = new Course("Java", "course Java ", teacher1);
        List<Course> courseList1 = new ArrayList<>();
        courseList1.add(course1);
        courseList1.add(course2);

        Student student1 = new Student("heba", "hdawwas", "hrmad", courseList1);
        Student student2 = new Student("mohammad", "dawwasmd", "hrmad", courseList1);

        entityManager.getTransaction().begin();
        entityManager.persist(teacher1);
        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(new Mark(student1, course1, 99));
        entityManager.persist(new Mark(student1, course2, 98));
        entityManager.persist(new Mark(student2, course1, 100));
        entityManager.persist(new Mark(student2, course2, 100));

        entityManager.getTransaction().commit();

    }

}
