Online Course Platform (Microservices Project)

Online Course Platform built using Spring Boot Microservices architecture, implementing secure user authentication, role-based authorization, and seamless service communication.

-----------------------------------------------------------------------------------------------------------------
System Features:

1. Users can register, log in, view available courses, and enroll
2. Admins can create and manage courses
3. Gateway validates JWT tokens before forwarding requests
4. Each service communicates via Feign Clients, secured by JWT, and discovered through Eureka Server

-------------------------------------------------------------------------------------------------------------------

### Tech Stack :
 1. Spring Boot
 2. Spring Cloud Gateway
 3. Spring Security + JWT
 4. Spring Cloud Netflix Eureka
 5. OpenFeign
 6. MySQL
 7. Lombok, ModelMapper
 8. Postman
 9. Maven (multi-module)

--------------------------------------------------------------------------------------------------------------------------------

### Microservices Structure

```
online-course-platform/
├── api-gateway : validate JWT and handle routes.
├── auth-service : login , register user & generate JWT token. 
├── course-service : admin can add & get courses , user can only get courses.
├── enrollment-service : User can enroll & get in available courses , ADMIN can get all enrolled user 
├── discovery-server : Register all the microservices.
```

--------------------------------------------------------------------------------------------------------------
### Key Features
 1. User authentication with JWT
 2. Role-based access control
 3. Gateway-level token validation using filters
 4. Service discovery with Eureka
 5. Feign clients for service-to-service calls
 6. Clean DTO architecture

--------------------------------------------------------------------------------------------------------------------

### Getting Started

1. Clone the repository
   `git clone https://github.com/your-username/online-course-platform.git`
2. Navigate to each folder and run:
   `mvn spring-boot:run`
Start with:

 `discovery-server`
 `auth-service`
 `api-gateway`
 `course-service`
 `enrollment-service`

--------------------------------------------------------------------

### API Overview

| Endpoint          | Method | Description                |
| ----------------- | ------ | -------------------------- |
| `/auth/register`  | POST   | Register a new user        |
| `/auth/login`     | POST   | Log in and receive JWT     |
| `/courses/list`   | GET    | View available courses     |
| `/courses/add`    | POST   | Create course (Admin only) |
| `/enrollments/enroll`   | POST   | Enroll in a course   |
