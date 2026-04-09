package com.example.springbootdemotwo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllStudents() throws Exception {
        // Given
        Student student1 = new Student(1L, "John", "john@example.com", "CS", 3.8);
        Student student2 = new Student(2L, "Jane", "jane@example.com", "Math", 3.9);
        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);

        // When & Then
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById() throws Exception {
        // Given
        Student student = new Student(1L, "John", "john@example.com", "CS", 3.8);
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        // When & Then
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testGetStudentByIdNotFound() throws Exception {
        // Given
        when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testCreateStudent() throws Exception {
        // Given
        Student student = new Student(null, "John", "john@example.com", "CS", 3.8);
        Student savedStudent = new Student(1L, "John", "john@example.com", "CS", 3.8);
        when(studentService.addStudent(any(Student.class))).thenReturn(savedStudent);

        // When & Then
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));

        verify(studentService, times(1)).addStudent(any(Student.class));
    }

    @Test
    void testUpdateStudent() throws Exception {
        // Given
        Student updatedStudent = new Student(null, "John Updated", "john@example.com", "CS", 4.0);
        Student savedStudent = new Student(1L, "John Updated", "john@example.com", "CS", 4.0);
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(Optional.of(savedStudent));

        // When & Then
        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));

        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    void testUpdateStudentNotFound() throws Exception {
        // Given
        Student updatedStudent = new Student(null, "John Updated", "john@example.com", "CS", 4.0);
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    void testDeleteStudent() throws Exception {
        // Given
        when(studentService.deleteStudent(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    void testDeleteStudentNotFound() throws Exception {
        // Given
        when(studentService.deleteStudent(1L)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).deleteStudent(1L);
    }
}
