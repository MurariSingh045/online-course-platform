package com.ms.ENROLLMENT_SERVICE.repo;

import com.ms.ENROLLMENT_SERVICE.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment , Long> {

    //When you use Optional<Enrollment>, Spring Data JPA expects exactly one or zero rows in the database.
    // if a user has multiple enrollment then it will throw 500 error.

    List<Enrollment> findByUserEmail(String email);

    //This avoids duplicate entries and respects the unique constraint (userEmail + courseId).
    Optional<Enrollment> findByUserEmailAndCourseId(String email, Long courseId);
}
