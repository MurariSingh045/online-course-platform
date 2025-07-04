package com.ms.Course_Service.service;

import com.ms.Course_Service.dto.CourseRequestDTO;
import com.ms.Course_Service.dto.CourseResponseDTO;
import com.ms.Course_Service.model.Course;
import com.ms.Course_Service.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> CourseResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .price(course.getPrice())
                        .build())
                .toList();
    }

    @Override
    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {

        Course course = Course.builder()
                .title(courseRequestDTO.getTitle())
                .description(courseRequestDTO.getDescription())
                .instructor(courseRequestDTO.getInstructor())
                .price(courseRequestDTO.getPrice())
                .build();

       Course saved =  courseRepository.save(course);


        return CourseResponseDTO.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .build();
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(course -> CourseResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .price(course.getPrice())
                        .build())
                .orElse(null);
    }

    @Override
    public String deleteCourse(Long id) {
         courseRepository.deleteById(id);
         return "Deleted Successfully !";
    }


}
