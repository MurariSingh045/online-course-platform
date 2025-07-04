# \# 📚 Online Course Platform (Microservices Project)

# Online Course Platform built using Spring Boot Microservices architecture, implementing secure user authentication, role-based authorization, and seamless service communication.


# This system allows:

# Users :  to register, log in, view available courses, and enroll
# Admins : to create and manage courses
# API-Gateway : to validate JWT tokens before forwarding requests
# Each service to communicate via Feign Clients, secured by JWT, and discovered through Eureka Server

# 🔧 Tech Stack : 

# Spring Boot
# Spring Cloud Gateway(API Gateway)
# Spring Security + JWT(Authentication \& Authorization)
# Spring Cloud Netflix Eureka(Service Discovery)
# OpenFeign(Service-to-service communication)
# MySQL(Database)
# Lombok, ModelMapper, DTO-based design
# Postman(for testing)
# Maven(multi-module)

-------------------------

# \## 🧩 Microservices : 

# online-course-platform/

# ├── api-gateway         # Validates JWT and routes requests

# ├── auth-service        # Handles login, registration, token generation 

# ├── course-service      # Allows admins to create/view courses, users to view

# ├── enrollment-service  # Manages user course enrollments

# ├── discovery-server    # Eureka server for service registration

---------------------------------------------------------------------

# \## 🔐 Key Features : 

# ✅ User authentication using JWT
# 🔐 Role-based access control(`ADMIN`, `USER`)
# 🛡️ Gateway-level token validation using filters
# 🔁 Gateway routes requests based on path and service discovery
# 📡 Service discovery with Eureka
# 📄 Feign Clients & Web Client for internal API communication
# 📃 Clean DTO structure between services

# ------------------------------------------------------------------

# \## 📂 Getting Started

# \### Clone the Repository : 

# git clone https://github.com/your-username/online-course-platform.git

# \### Navigate to Project Directory

# ```bash

# cd online-course-platform

# \### Run Eureka Discovery Server
# ```bash
# cd discovery-server
# mvn spring-boot:run
# \### Run Other Microservices

----------------------------------------------
# Repeat the process for each service:

# ```bash

# cd ../auth-service

# mvn spring-boot:run

# ```bash

# cd ../api-gateway

# mvn spring-boot:run

# ... and so on for course and enrollment services.


# \## 🌐 API Endpoints Overview : 


# | Endpoint          | Method | Description                 |

# | ----------------- | ------ | --------------------------- |

# | `/auth/register`  | POST   | Register a new user         |

# | `/auth/login`     | POST   | Login and get JWT token     |

# | `/courses/get`   | GET    | View available courses      |

# | `/courses/add` | POST   | (ADMIN) Create a new course |

# | `/enrollments/enroll`   | POST   | Enroll user into a course   |

# | `/enrollments/get`      | GET   | Enroll user see thier courses   |

# | `/enrollments/all`       | GET   | Only Admin can view all enrolled courses   |











