package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public Student read(@PathVariable Long id) {
        return studentService.read(id);
    }

    @PutMapping("{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {
        return studentService.delete(id);

    }

    @GetMapping("/filter/{age}")
    public List<Student> filterByAge(@RequestParam int age) {
        return studentService.filterByAge(age);
    }

    @GetMapping("/filter/{ageBetween}")
    public List<Student> filterByAgeBetween(int fromAge, int toAge) {
        return studentService.findAllByAgeBetween(fromAge, toAge);
    }

    @GetMapping("{id}/faculty")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }
    //
    @GetMapping("/amount")
    public int getAmountOfStudents() {
        return studentService.getAmountOfStudents();
    }
    @GetMapping("/average-age")
    public double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }
    @GetMapping("/last-students")
    public List<Student> getLastStudents(@RequestParam int count) {
        return studentService.getLastStudents(count);
    }
    @GetMapping("startsWithA")
    public List<String> startsWithA() {
        return studentService.startsWithA();
    }
    @GetMapping("averageAge")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

}
