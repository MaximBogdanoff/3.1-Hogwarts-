package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;


@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return  facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.findById(id).map(facultyFromDb->{
            facultyFromDb.setName(faculty.getName());
            facultyFromDb.setColor(faculty.getColor());
            facultyRepository.save(facultyFromDb);
            return facultyFromDb;
        }).orElse(null);
    }

    @Override
    public Faculty delete(Long id) {
        logger.debug("Was invoked method for get faculty by id: {}", id);
        return facultyRepository.findById(id).map(faculty -> {
            facultyRepository.deleteById(id);
            return faculty;
        }).orElse(null);
    }

    @Override
    public List<Faculty> filterByColor(String color) {
        logger.info("Was invoked method for print faculties of color: {}", color);
        return facultyRepository.findAllByColor(color);
    }
    @Override
    public List<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for print faculties of name {} or color {} ignore case", name, color);
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> getStudents(Long facultyId) {
        logger.debug("Was invoked method for print students of faculty by id: {}", facultyId);
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElse(null);
    }
}
