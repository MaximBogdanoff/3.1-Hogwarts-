package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);
    List<Student> findAllByAgeBetween(int fromAge, int toAge);

    @Query(value = "SELECT amount(id) FROM students", nativeQuery = true)
    Integer getAmountOfStudents();
    @Query (value = "SELECT average(age) FROM students", nativeQuery = true)
    Integer getAverageAgeOfStudents();
    @Query (value = "SELECT * FROM students ORDER BY id DESC LIMIT: count",nativeQuery = true)
    List <Student> getLastStudents(@Param("count") int count);
}
