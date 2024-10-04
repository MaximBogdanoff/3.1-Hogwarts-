package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        logger.debug("method to read student by id = {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student update(Long id, Student student) {
        logger.debug(" method for update student with id = {}, studentName = {}", id, student.getName());
        return studentRepository.findById(id).map(studentFromDb -> {
            studentFromDb.setName(student.getName());
            studentFromDb.setAge(student.getAge());
            return studentFromDb;
        }).orElse(null);
    }

    @Override
    public Student delete(Long id) {
        logger.debug("method delete id = {}", id);
        return studentRepository.findById(id).map(student -> {
            studentRepository.deleteById(id);
            return student;
        }).orElse(null);
    }

    @Override
    public List<Student> filterByAge(int age) {
        logger.info("Was invoked method for print students of age: {}", age);
        return studentRepository.findAllByAge(age);
    }

    @Override
    public List<Student> findAllByAgeBetween(int fromAge, int toAge) {
        logger.info("Was invoked method for print students of age between {} and {}", fromAge, toAge);
        return studentRepository.findAllByAgeBetween(fromAge, toAge);
    }

    @Override
    public Faculty getFaculty(Long studentId) {
        logger.debug("Was invoked method for get faculty of student with id {}", studentId);
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);
    }


    @Override
    public int getAmountOfStudents() {
        logger.info("Was invoked method for get count all students");
        return studentRepository.getAmountOfStudents();
    }

    @Override
    public double getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age from students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public List<Student> getLastStudents(int count) {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastStudents(count);
    }

    @Override
    public List<String> startsWithA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(it->it.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());


    }

    @Override
    public double getAverageAge() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

}
