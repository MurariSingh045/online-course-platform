package com.ms.ENROLLMENT_SERVICE.service;

import com.ms.ENROLLMENT_SERVICE.dto.EnrollmentRequestDTO;
import com.ms.ENROLLMENT_SERVICE.dto.EnrollmentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollmentService {

    EnrollmentResponseDTO enrollUser(EnrollmentRequestDTO enrollmentRequestDTO);

    List<EnrollmentResponseDTO> getUserEnrollments(EnrollmentRequestDTO enrollmentRequestDTO);

    List<EnrollmentResponseDTO> getAllEnrollments();



    String deleteEnrollment(Long id);
}
