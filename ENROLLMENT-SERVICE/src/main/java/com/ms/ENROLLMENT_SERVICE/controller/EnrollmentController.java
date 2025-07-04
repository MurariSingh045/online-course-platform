package com.ms.ENROLLMENT_SERVICE.controller;

import com.ms.ENROLLMENT_SERVICE.dto.EnrollmentRequestDTO;
import com.ms.ENROLLMENT_SERVICE.dto.EnrollmentResponseDTO;
import com.ms.ENROLLMENT_SERVICE.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
   private EnrollmentService enrollmentService;

    // Enroll user
    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponseDTO> enrollInCourse(@RequestHeader("X-User-Email") String email,
                                                                @RequestBody EnrollmentRequestDTO request) {
        request.setUserEmail(email);// set the email in dto object which is coming from the gateway through header.
        return ResponseEntity.ok(enrollmentService.enrollUser(request));
    }

    // User can check their enrollment using this.
    @GetMapping("/get")
    public ResponseEntity<List<EnrollmentResponseDTO>> myEnrollments(@RequestHeader("X-User-Email") String email) {
        EnrollmentRequestDTO request = new EnrollmentRequestDTO(); // creating object of the Enrollment request dto
        request.setUserEmail(email); // and set the email in dto object which is coming from the gateway through header.
        return ResponseEntity.ok(enrollmentService.getUserEnrollments(request));
    }

    // here admin can get using role which is coming from the gateway header.
    @GetMapping("/all")
    public ResponseEntity<?> allEnrollments(@RequestHeader("X-User-Role") String role) {
        // if the role is not admin then denied the request.
        if (!"ROLE_ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        // if the role is admin then access the request
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }
}
