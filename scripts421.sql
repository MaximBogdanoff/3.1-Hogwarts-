ALTER TABLE students ADD CONSTRAINT age_check CHECK(age>=16);
ALTER TABLE students ADD CONSTRAINT name_unique UNIQUE(name);
ALTER TABLE students ALTER COLUMN name SET NOT NULL;
ALTER TABLE faculties ADD CONSTRAINT name_color unique UNIQUE(name, color);
ALTER TABLE students ALTER COLUMN name SET DEFAULT 20;