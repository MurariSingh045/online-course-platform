package com.ms.Course_Service.dto;

import com.ms.Course_Service.model.Course;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {

    private Long id;
    private String title;
    private String description;
    private double price;

    public CourseResponseDTO(List<Course> allCourses) {
    }
}
