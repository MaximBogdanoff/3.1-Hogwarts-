package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private Long counter = 0L;

    @Override
    public Faculty create(Faculty faculty) {
        Long currentId = ++counter;
        faculty.setId(currentId);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty read(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        Faculty facultyFromDb = faculties.get(id);
        facultyFromDb.setName(faculty.getName());
        facultyFromDb.setColor(faculty.getColor());
        return facultyFromDb;
    }

    @Override
    public Faculty delete(Long id) {
        return faculties.remove(id);
    }

    @Override
    public List<Faculty> filterByColor(String color) {
        return faculties.values()
                .stream()
                .filter(it -> it.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
