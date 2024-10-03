-- liquibase formatted sql

-- changeset Максим:1
CREATE INDEX IDX_STUDENT_NAME ON student(name)
-- changeset Максим:2
CREATE  INDEX IND_FACULTY_NAME_COLOR ON faculty(name, color)

