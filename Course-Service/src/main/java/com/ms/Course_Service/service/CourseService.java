package com.ms.Course_Service.service;

import com.ms.Course_Service.dto.CourseRequestDTO;
import com.ms.Course_Service.dto.CourseResponseDTO;
import com.ms.Course_Service.model.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO addCourse(CourseRequestDTO course);

    CourseResponseDTO getCourseById(Long id);

    String deleteCourse(Long id);
}
