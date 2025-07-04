package com.ms.ENROLLMENT_SERVICE.service;

import ch.qos.logback.classic.spi.LoggingEventVO;
import com.ms.ENROLLMENT_SERVICE.dto.CourseResponseDTO;
import com.ms.ENROLLMENT_SERVICE.dto.EnrollmentRequestDTO;
import com.ms.ENROLLMENT_SERVICE.dto.EnrollmentResponseDTO;
import com.ms.ENROLLMENT_SERVICE.enrollment.Enrollment;
import com.ms.ENROLLMENT_SERVICE.feign.CourseFeignClient;
import com.ms.ENROLLMENT_SERVICE.repo.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseFeignClient courseFeignClient;


    @Override
    public EnrollmentResponseDTO enrollUser(EnrollmentRequestDTO enrollmentRequestDTO) {

        //getting enrolled user from db
        Optional<Enrollment> existing = enrollmentRepository
                .findByUserEmailAndCourseId(enrollmentRequestDTO.getUserEmail(), enrollmentRequestDTO.getCourseId());

        // if the user is already enrolled throw exception
        if (existing.isPresent()) {
            throw new IllegalArgumentException("❌ Already enrolled in this course.");
        }

        // Get course from course service  when the user hit the request to enroll in course via courseId
        // "courseId" : x ,x = 1,2,... then via feign client it will send request to course-service and get the response.
        CourseResponseDTO course = courseFeignClient.getCourseById(enrollmentRequestDTO.getCourseId());

        Enrollment enrollment = Enrollment.builder()
                .userEmail(enrollmentRequestDTO.getUserEmail())
                .courseId(enrollmentRequestDTO.getCourseId())
                .enrolledAt(LocalDateTime.now()) // set the current time and date
                .build();

        // saving enrollment into db
        Enrollment saved = enrollmentRepository.save(enrollment);

        // return response
        return EnrollmentResponseDTO.builder()
                .id(saved.getId())
                .userEmail(saved.getUserEmail())
                .courseId(saved.getCourseId())
                .enrolledAt(saved.getEnrolledAt())
                .build();
    }


    // By using User can check their enrollment.
    @Override
    public List<EnrollmentResponseDTO> getUserEnrollments(EnrollmentRequestDTO enrollmentRequestDTO) {
        return enrollmentRepository.findByUserEmail(enrollmentRequestDTO.getUserEmail()).stream()
                .map(e -> EnrollmentResponseDTO.builder()
                        .id(e.getId())
                        .userEmail(e.getUserEmail())
                        .courseId(e.getCourseId())
                        .enrolledAt(e.getEnrolledAt())
                        .build())
                .collect(Collectors.toList());
    }

   // only admin can get All enrollment not any user can get this.
    @Override
    public List<EnrollmentResponseDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(e -> EnrollmentResponseDTO.builder()
                        .id(e.getId())
                        .userEmail(e.getUserEmail())
                        .courseId(e.getCourseId())
                        .enrolledAt(e.getEnrolledAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String deleteEnrollment(Long id) {
         enrollmentRepository.deleteById(id);
         return "Deleted Successfully !";
    }
}
