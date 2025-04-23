# Kanri Blog Platform

A Java web application for managing blog posts with role-based access control (CR and NON-CR users).

## Technologies Used

- Java (JDK 22)
- Apache Tomcat 9.0.104
- MySQL 9.2.0
- Maven
- JSP/Servlet
- JSTL
- HTML/CSS

## Prerequisites

- JDK 22 or later
- Apache Tomcat 9.x
- MySQL Server 9.2.0
- Maven 3.x

## Project Structure

```
kanri/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/kopinfotech/blog/
│   │   │       ├── controller/        # Servlet classes
│   │   │       │   ├── LoginServlet.java
│   │   │       │   ├── SignupServlet.java
│   │   │       │   ├── LogoutServlet.java
│   │   │       │   ├── CRDashboardServlet.java
│   │   │       │   ├── NONCRDashboardServlet.java
│   │   │       │   └── PostServlet.java
│   │   │       ├── dao/              # Data Access Objects
│   │   │       │   ├── UserDAO.java
│   │   │       │   └── PostDAO.java
│   │   │       ├── model/            # Model classes
│   │   │       │   ├── User.java
│   │   │       │   └── Post.java
│   │   │       ├── filter/           # Filters
│   │   │       │   └── AuthenticationFilter.java
│   │   │       └── util/             # Utility classes
│   │   │           └── DBUtil.java
│   │   ├── resources/
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       └── jsp/                  # JSP pages
│   │           ├── login.jsp
│   │           ├── signup.jsp
│   │           ├── cr_dashboard.jsp
│   │           ├── non_cr_dashboard.jsp
│   │           └── post_form.jsp
└── pom.xml                          # Maven configuration
```

## Database Setup

1. Create a MySQL database named `kanridb`
2. Execute the following SQL commands:

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    role ENUM('CR', 'NON-CR') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
    post_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

## Configuration

1. Update database connection settings in `DBUtil.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/kanridb";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

2. Ensure Maven dependencies are correct in `pom.xml`:
- MySQL Connector version: 9.2.0
- Servlet API version: 4.0.1
- JSP API version: 2.3.3
- JSTL version: 1.2

## Building and Deployment

1. Build the project:
```bash
mvn clean package
```

2. Deploy to Tomcat:
- Stop Tomcat if running
- Delete old application from `webapps` directory
- Copy `target/kanri.war` to Tomcat's `webapps` directory
- Start Tomcat

3. Access the application:
```
http://localhost:8080/kanri/
```

## Features

1. **User Management**
   - User registration (Signup)
   - User authentication (Login/Logout)
   - Role-based access control (CR/NON-CR)

2. **Post Management**
   - CR users can create, edit, and delete posts
   - NON-CR users can view posts
   - Post listing with author information

3. **Security**
   - Session-based authentication
   - Role-based access control
   - Input validation and sanitization
   - SQL injection prevention

## Common Issues and Solutions

1. **404 Error**
   - Verify correct application context path
   - Check Tomcat deployment
   - Ensure all JSP files are in correct location

2. **Database Connection Issues**
   - Verify MySQL is running
   - Check connection credentials
   - Confirm database and tables exist

3. **Deployment Issues**
   - Clean Tomcat work directory
   - Verify WAR file is properly built
   - Check Tomcat logs for errors

## TODO

- [ ] Implement password hashing
- [ ] Add post pagination
- [ ] Implement post search functionality
- [ ] Add user profile management
- [ ] Implement post comments feature

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 
