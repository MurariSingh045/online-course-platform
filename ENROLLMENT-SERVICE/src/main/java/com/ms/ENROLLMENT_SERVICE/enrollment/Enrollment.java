package com.ms.ENROLLMENT_SERVICE.enrollment;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// this ensures that same user can't  enroll more than once , "Ensure that each (userEmail, courseId) pair is unique across all rows."
// if the same user tries to enroll again this will reject from enrollment.
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userEmail", "courseId"}))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private Long courseId;
    private LocalDateTime enrolledAt;
}
