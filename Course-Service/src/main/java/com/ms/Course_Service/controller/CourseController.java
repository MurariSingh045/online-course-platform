package com.ms.Course_Service.controller;

import com.ms.Course_Service.dto.CourseRequestDTO;

import com.ms.Course_Service.dto.CourseResponseDTO;
import com.ms.Course_Service.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    // when the request will come through feign client which is inside the enrollment service
    // then here will check the course exist or not . if exist or not then return the response  to enrollment service via feign client
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

        CourseResponseDTO course = courseService.getCourseById(id);

        return ResponseEntity.ok(course);

    }


    @GetMapping("/get")
    public ResponseEntity<?> getAllCourses(@RequestHeader("X-User-Role") String role) {
        System.out.println("🔍 Received Role: " + role);
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_USER")) {
            List<CourseResponseDTO> response = courseService.getAllCourses();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestHeader("X-User-Role") String role,
                                       @RequestBody CourseRequestDTO courseRequestDTO) {

        System.out.println("🔍 Received Role: " + role);
        if (role.equals("ROLE_ADMIN")) {
            CourseResponseDTO response = courseService.addCourse(courseRequestDTO);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only ADMINs can add courses");
    }
}
