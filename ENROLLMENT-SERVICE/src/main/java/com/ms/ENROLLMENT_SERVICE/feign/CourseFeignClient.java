package com.ms.ENROLLMENT_SERVICE.feign;


import com.ms.ENROLLMENT_SERVICE.dto.CourseResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COURSE-SERVICE")
public interface CourseFeignClient {

    @GetMapping("/courses/{id}")
    CourseResponseDTO getCourseById(@PathVariable("id") Long id);
}
