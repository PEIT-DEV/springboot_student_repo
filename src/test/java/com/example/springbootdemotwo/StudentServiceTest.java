package com.example.springbootdemotwo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testGetAllStudents() {
        // Given
        Student student1 = new Student(1L, "John", "john@example.com", "CS", 3.8);
        Student student2 = new Student(2L, "Jane", "jane@example.com", "Math", 3.9);
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        // When
        List<Student> students = studentService.getAllStudents();

        // Then
        assertThat(students).hasSize(2);
        assertThat(students.get(0).getName()).isEqualTo("John");
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        // Given
        Student student = new Student(1L, "John", "john@example.com", "CS", 3.8);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // When
        Optional<Student> foundStudent = studentService.getStudentById(1L);

        // Then
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getName()).isEqualTo("John");
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testAddStudent() {
        // Given
        Student student = new Student(null, "John", "john@example.com", "CS", 3.8);
        Student savedStudent = new Student(1L, "John", "john@example.com", "CS", 3.8);
        when(studentRepository.save(student)).thenReturn(savedStudent);

        // When
        Student result = studentService.addStudent(student);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John");
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudent() {
        // Given
        Student existingStudent = new Student(1L, "John", "john@example.com", "CS", 3.8);
        Student updatedStudent = new Student(null, "John Updated", "john@example.com", "CS", 4.0);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        // When
        Optional<Student> result = studentService.updateStudent(1L, updatedStudent);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Updated");
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void testDeleteStudent() {
        // Given
        when(studentRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = studentService.deleteStudent(1L);

        // Then
        assertThat(result).isTrue();
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStudentNotFound() {
        // Given
        when(studentRepository.existsById(1L)).thenReturn(false);

        // When
        boolean result = studentService.deleteStudent(1L);

        // Then
        assertThat(result).isFalse();
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, never()).deleteById(1L);
    }
}
