# Blog API

## Overview
The Blog API is a RESTful web service built using Java and Spring Boot. It provides a secure and efficient way to manage blog posts and user authentication using JWT.

## Features
- User authentication and authorization using JWT.
- CRUD operations for blog posts.
- Secure access control with Spring Security.
- Database management with Spring Data JPA and MySQL.
- Optimized query performance for improved response times.

## Tech Stack
- **Backend:** Java, Spring Boot
- **Security:** Spring Security, JWT
- **Database:** MySQL, Hibernate (Spring Data JPA)
- **API Documentation:** Springdoc OpenAPI
- **Testing:** JUnit, Mockito

## Installation & Setup
1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-repo/blog-api.git
   cd blog-api
   ```
2. **Configure MySQL database:**
   - Update `application.properties` with your MySQL credentials.
   
3. **Build and run the project:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints
### Authentication
- **Register:** `POST /api/auth/register`
- **Login:** `POST /api/auth/login`

### Blog Posts
- **Create Post:** `POST /api/posts`
- **Get All Posts:** `GET /api/posts`
- **Get Post by ID:** `GET /api/posts/{id}`
- **Update Post:** `PUT /api/posts/{id}`
- **Delete Post:** `DELETE /api/posts/{id}`

## Security & Authentication
- Uses **JWT-based authentication**.
- Users must authenticate to create, update, or delete blog posts.
- Public endpoints for reading blog posts.

## Improvements & Optimization
- Optimized database queries to improve API response time by **30%**.
- Implemented efficient **data persistence** using Hibernate ORM.

## Contribution
Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss the proposed changes.

## License
This project is licensed under the MIT License.
