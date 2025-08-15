# Authentication Test Project

A full-stack web application demonstrating JWT-based authentication with a React frontend and Spring Boot backend.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Deployment Options](#deployment-options)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## 🚀 Project Overview

This project is a learning implementation of JWT (JSON Web Token) authentication featuring:
- User login/authentication
- JWT token generation and validation
- CORS configuration for cross-origin requests
- MongoDB integration for data persistence
- Responsive React frontend

## 🛠 Technologies Used

### Backend
- **Java 21**
- **Spring Boot 3.4.8**
- **Spring Data MongoDB**
- **JWT (JSON Web Tokens)** - v0.11.5
- **Maven** - Build tool
- **MongoDB** - Database

### Frontend
- **React 19.1.1**
- **React Router DOM 7.8.0**
- **React Scripts 5.0.1**
- **CSS** - Styling

## 📁 Project Structure

```
authentication_test/
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── me/nguyenn/AuthenticationTest/
│   │   │   │       ├── controller/        # REST controllers
│   │   │   │       ├── service/           # Business logic
│   │   │   │       ├── repository/        # Data access layer
│   │   │   │       ├── model/             # Entity models
│   │   │   │       ├── dto/               # Data Transfer Objects
│   │   │   │       ├── util/              # Utility classes (JWT)
│   │   │   │       └── configs/           # Configuration classes
│   │   │   └── resources/
│   │   │       └── application.properties # App configuration
│   │   └── test/                          # Test files
│   └── pom.xml                           # Maven dependencies
└── frontend/                             # React frontend
    ├── src/
    │   ├── components/                   # React components
    │   ├── App.js                        # Main app component
    │   └── index.js                      # Entry point
    ├── public/                           # Static assets
    └── package.json                      # NPM dependencies
```

## ✅ Prerequisites

Before running this application, make sure you have the following installed:

- **Java 21** or higher
- **Node.js 16** or higher
- **npm** or **yarn**
- **Maven 3.6** or higher
- **MongoDB** (or access to MongoDB Atlas)

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd authentication_test
```

### 2. Backend Setup

```bash
cd backend

# Install dependencies (Maven will download them automatically)
mvn clean install

# Optional: Run tests
mvn test
```

### 3. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Or using yarn
yarn install
```

### 4. Environment Configuration

Update the MongoDB connection string in `backend/src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/AuthenticationTest
# Or your MongoDB Atlas connection string
```

## 🚀 Running the Application

### Development Mode

#### Backend (Spring Boot)
```bash
cd backend

# Run with Maven
mvn spring-boot:run

# Or run the JAR file
mvn clean package
java -jar target/AuthenticationTest-0.0.1-SNAPSHOT.war
```

The backend will start on `http://localhost:8080`

#### Frontend (React)
```bash
cd frontend

# Start development server
npm start

# Or using yarn
yarn start
```

The frontend will start on `http://localhost:3000`

### Production Mode

#### Build Frontend for Production
```bash
cd frontend
npm run build
```

This creates a `build` folder with optimized production files.

## 🌐 Deployment Options

### Option 1: Deploy Frontend with Nginx

#### 1. Build the React App
```bash
cd frontend
npm run build
```

#### 2. Nginx Configuration
Create an Nginx configuration file (`/etc/nginx/sites-available/authentication-app`):

```nginx
server {
    listen 80;
    server_name your-domain.com;
    root /path/to/frontend/build;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # Proxy API requests to Spring Boot backend
    location /api/ {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

#### 3. Enable the Site
```bash
sudo ln -s /etc/nginx/sites-available/authentication-app /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx
```

### Option 2: Serve Frontend from Spring Boot

#### 1. Build React App
```bash
cd frontend
npm run build
```

#### 2. Copy Build Files to Spring Boot
```bash
# Copy the build files to Spring Boot's static resources
cp -r frontend/build/* backend/src/main/resources/static/
```

#### 3. Update Spring Boot Configuration
Add to `application.properties`:

```properties
# Serve frontend from root path
spring.web.resources.static-locations=classpath:/static/
spring.mvc.view.prefix=/
spring.mvc.view.suffix=.html
```

#### 4. Create a Controller for Frontend Routes
Create a controller to handle frontend routing:

```java
@Controller
public class FrontendController {
    
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
```

#### 5. Package and Deploy
```bash
cd backend
mvn clean package
java -jar target/AuthenticationTest-0.0.1-SNAPSHOT.war
```

Now both frontend and backend are served from `http://localhost:8080`

### Option 3: Docker Deployment

#### Dockerfile for Backend
```dockerfile
FROM openjdk:21-jdk-slim
COPY target/AuthenticationTest-0.0.1-SNAPSHOT.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.war"]
```

#### Dockerfile for Frontend
```dockerfile
FROM node:16-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

#### Docker Compose
```yaml
version: '3.8'
services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/AuthenticationTest
    depends_on:
      - mongo

  frontend:
    build: ./frontend
    ports:
      - "80:80"
    depends_on:
      - backend

  mongo:
    image: mongo:latest
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

volumes:
  mongo_data:
```

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | User login |
| POST | `/api/auth/register` | User registration |
| GET | `/api/auth/validate` | Validate JWT token |

### Request/Response Examples

#### Login Request
```json
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}
```

#### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "60d5ecb74b24c7001f8e4b8a",
    "username": "user@example.com"
  }
}
```

## 🔧 Configuration

### Backend Configuration
Key configuration options in `application.properties`:

```properties
# Application name
spring.application.name=AuthenticationTest

# Server configuration
server.address=0.0.0.0
server.port=8080

# MongoDB configuration
spring.data.mongodb.uri=mongodb://localhost:27017/AuthenticationTest

# JWT configuration (add these)
jwt.secret=your-secret-key
jwt.expiration=86400000
```

### Frontend Configuration
Update API endpoints in your React components to match your backend URL:

```javascript
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is created for learning JWT authentication. Feel free to use it for educational purposes.

## 🐛 Troubleshooting

### Common Issues

1. **CORS Issues**: Make sure CORS is properly configured in your Spring Boot application
2. **MongoDB Connection**: Verify your MongoDB connection string and network access
3. **Port Conflicts**: Ensure ports 3000 (React) and 8080 (Spring Boot) are available
4. **JWT Secret**: Make sure to set a secure JWT secret in production

### Support

If you encounter any issues, please check the console logs for both frontend and backend applications.
