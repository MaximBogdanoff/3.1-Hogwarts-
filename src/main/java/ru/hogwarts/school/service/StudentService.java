package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student create(Student student);

    Student read(Long id);

    Student update(Long id, Student student);

    Student delete(Long id);

    List<Student> filterByAge(int age);

    List<Student> findAllByAgeBetween(int fromAge, int toAge);

    Faculty getFaculty(Long studentId);
}
