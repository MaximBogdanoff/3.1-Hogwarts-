SELECT s.name, s.age, f.name as "Факультет" FROM students s LEFT JOIN faculties f ON s.faculty_id = f.id;
SELECT s.name FROM students s INNER JOIN avatars a ON student_id = s.id;