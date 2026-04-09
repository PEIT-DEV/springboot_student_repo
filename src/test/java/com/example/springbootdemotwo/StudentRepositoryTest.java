package com.example.springbootdemotwo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveAndFindById() {
        // Given
        Student student = new Student(null, "John Doe", "john@example.com", "Computer Science", 3.8);

        // When
        Student savedStudent = studentRepository.save(student);

        // Then
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getName()).isEqualTo("John Doe");

        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindAll() {
        // Given
        Student student1 = new Student(null, "Jane Doe", "jane@example.com", "Math", 3.9);
        Student student2 = new Student(null, "Bob Smith", "bob@example.com", "Physics", 3.7);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // When
        List<Student> students = studentRepository.findAll();

        // Then
        assertThat(students).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testDeleteById() {
        // Given
        Student student = new Student(null, "Alice", "alice@example.com", "Biology", 4.0);
        Student savedStudent = studentRepository.save(student);

        // When
        studentRepository.deleteById(savedStudent.getId());

        // Then
        Optional<Student> deletedStudent = studentRepository.findById(savedStudent.getId());
        assertThat(deletedStudent).isNotPresent();
    }
}
