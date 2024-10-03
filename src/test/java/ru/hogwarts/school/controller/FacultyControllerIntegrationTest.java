package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@Configuration

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void clear(){
        facultyRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        Faculty faculty= new Faculty("Griffindor", "Green");

        ResponseEntity<Faculty> facultyResponseEntity=restTemplate.postForEntity("http://localhost:"+port+"/faculties",
                faculty,
                Faculty.class
        );
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty=facultyResponseEntity.getBody();
        assertNotNull(actualFaculty.getId());
        assertEquals(actualFaculty.getName(),faculty.getName());
        org.assertj.core.api.Assertions.assertThat(actualFaculty.getColor()).isEqualTo(faculty.getColor());
    }

    @Test
    void shouldUpdateFaculty() {
        Faculty faculty= new Faculty("Slytherin", "color");
        faculty=facultyRepository.save(faculty);

        Faculty facultyForUpdate=new Faculty("Griffindor","Green");

        HttpEntity<Faculty> entity = new HttpEntity<>(facultyForUpdate);
        ResponseEntity<Faculty> facultyResponseEntity=restTemplate.exchange("http://localhost:"+port+"/faculties/"+faculty.getId(),
                HttpMethod.PUT,
                entity,
                Faculty.class
        );
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty=facultyResponseEntity.getBody();
        assertEquals(actualFaculty.getId(),faculty.getId());
        assertEquals(actualFaculty.getName(),facultyForUpdate.getName());
        assertEquals(actualFaculty.getColor(),facultyForUpdate.getColor());
    }

    @Test
    void shouldGetFaculty() {
        Faculty faculty= new Faculty("Hufflepuff", "yellow");
        faculty=facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity=restTemplate.getForEntity(
                "http://localhost:"+port+"/faculties/"+faculty.getId(),
                Faculty.class
        );

        assertNotNull(facultyResponseEntity);
        Assertions.assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Faculty actualFaculty=facultyResponseEntity.getBody();
        Assertions.assertEquals(actualFaculty.getId(),faculty.getId());
        assertEquals(actualFaculty.getName(),faculty.getName());
        assertEquals(actualFaculty.getColor(),faculty.getColor());
    }

    @Test
    void shouldDeleteFaculty() {

        Faculty faculty = new Faculty("Hufflepuff", "yellow");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculties/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Faculty.class
        );
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        org.assertj.core.api.Assertions.assertThat(facultyRepository.findById(faculty.getId())).isNotPresent();
    }
}
