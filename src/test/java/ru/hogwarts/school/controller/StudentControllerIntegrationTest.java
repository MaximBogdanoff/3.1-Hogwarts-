package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void clearDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        Student student = new Student("Harry Potter", 23);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/students",
                student,
                Student.class
        );
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getName(), student.getName());
        org.assertj.core.api.Assertions.assertThat(actualStudent.getAge()).isEqualTo(student.getAge());
    }

    @Test
    void shouldUpdateStudent() {
        Student student = new Student("Hermione Granger", 20);
        student = studentRepository.save(student);

        Student studentForUpdate = new Student("Harry Potter", 23);

        HttpEntity<Student> entity = new HttpEntity<>(studentForUpdate);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange("http://localhost:" + port + "/students/" + student.getId(),
                HttpMethod.PUT,
                entity,
                Student.class
        );
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), studentForUpdate.getName());
        assertEquals(actualStudent.getAge(), studentForUpdate.getAge());
    }

    @Test
    void shouldGetStudent() {
        Student student = new Student("Ronald Weasley", 21);
        student = studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/students/" + student.getId(),
                Student.class
        );

        assertNotNull(studentResponseEntity);
        Assertions.assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        Assertions.assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldDeleteStudent() {
        Student student = new Student("Ronald Weasley", 21);
        student = studentRepository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/students/" + student.getId(),
                HttpMethod.DELETE,
                null,
                Student.class
        );
        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        org.assertj.core.api.Assertions.assertThat(studentRepository.findById(student.getId())).isNotPresent();
    }
}