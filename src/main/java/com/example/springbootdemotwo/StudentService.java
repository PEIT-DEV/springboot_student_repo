package com.example.springbootdemotwo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private List<Student> students;

    public StudentService() {
        students = new ArrayList<>();
        // Initialize with sample data
        students.add(new Student(1, "John Doe", "john@example.com", "Computer Science", 3.8));
        students.add(new Student(2, "Jane Smith", "jane@example.com", "Information Technology", 3.9));
        students.add(new Student(3, "Mike Johnson", "mike@example.com", "Software Engineering", 3.7));
        students.add(new Student(4, "Sarah Williams", "sarah@example.com", "Data Science", 3.85));
    }

    // Get all students
    public List<Student> getAllStudents() {
        return students;
    }

    // Get student by ID
    public Optional<Student> getStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst();
    }

    // Add a new student
    public Student addStudent(Student student) {
        student.setId(students.size() + 1);
        students.add(student);
        return student;
    }

    // Update student
    public Optional<Student> updateStudent(int id, Student updatedStudent) {
        Optional<Student> existingStudent = getStudentById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            student.setCourse(updatedStudent.getCourse());
            student.setGpa(updatedStudent.getGpa());
            return Optional.of(student);
        }
        return Optional.empty();
    }

    // Delete student
    public boolean deleteStudent(int id) {
        return students.removeIf(student -> student.getId() == id);
    }
}

