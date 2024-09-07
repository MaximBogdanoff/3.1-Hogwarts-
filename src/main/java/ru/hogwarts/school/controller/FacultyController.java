package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("{id}")
    public Faculty read(@RequestParam Long id) {
        return facultyService.read(id);
    }

    @PutMapping("{id}")
    public Faculty update1(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("{id}")
    public Faculty delete(@PathVariable Long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/filter/{color}")
    public List<Faculty> filterByColor(@RequestParam String color) {
        return facultyService.filterByColor(color);
    }
    @GetMapping("/filter/{nameIgnoreCaseOrColorIgnoreCase}")
    public List<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return facultyService.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @GetMapping("{id}/students")
    public List<Student> getStudents(@PathVariable Long id) {
        return facultyService.getStudents(id);
    }
}
