# RoleLedger - AI-Powered Placement & Internship Tracker

RoleLedger is a comprehensive full-stack web application designed to help students and professionals track their job and internship applications while receiving intelligent, context-aware AI guidance throughout their placement journey.

## 🚀 Features

### Core Functionality
- **User Authentication**: Secure JWT-based authentication with signup and login
- **Application Management**: Full CRUD operations for tracking job/internship applications
- **Dashboard Analytics**: Visual charts and statistics showing application status breakdown
- **Status Tracking**: Track applications through stages: Applied → Online Assessment → Interview → Offer → Rejected

### AI-Powered Features
1. **Interview Question Generator**
   - Generates role-specific technical questions (Java-focused)
   - Behavioral questions using STAR method
   - Company and role-specific tailored questions

2. **Next-Step Advisor**
   - Context-aware preparation advice based on current application status
   - Adaptive guidance that changes as status progresses
   - Specific recommendations for technical and behavioral preparation

3. **Resume Feedback Assistant**
   - AI-powered resume analysis
   - Role-specific feedback and optimization suggestions
   - ATS optimization recommendations

## 🛠️ Tech Stack

### Backend
- **Java 17** with **Spring Boot 3.2.0**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for database operations
- **MySQL** database
- **Google Gemini AI API** for intelligent responses
- **Maven** for dependency management

### Frontend
- **React 18** with functional components and hooks
- **Material UI (MUI)** for modern, responsive UI components
- **Recharts** for data visualization
- **React Router** for navigation
- **Axios** for API calls

### Deployment
- **Frontend**: Vercel (free tier)
- **Backend**: Railway / Render (free tier)
- **Database**: Railway MySQL / Clever Cloud MySQL (free tier)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 16+ and npm
- MySQL 8.0+
- Google Gemini API key (free tier)

## 🏗️ Architecture

### Backend Architecture
```
Controller Layer (REST APIs)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Database (MySQL)
```

### Frontend Architecture
```
React Components
    ↓
Context API (State Management)
    ↓
Axios (HTTP Client)
    ↓
Backend REST APIs
```

## 📦 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/roleledger.git
cd roleledger
```

### 2. Database Setup
```bash
# Create MySQL database
mysql -u root -p < database/schema.sql

# Or manually:
mysql -u root -p
CREATE DATABASE roleledger;
USE roleledger;
SOURCE database/schema.sql;
```

### 3. Backend Setup

1. Navigate to backend directory:
```bash
cd backend
```

2. Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
gemini.api.key=your_gemini_api_key
jwt.secret=your-secret-key-change-in-production
```

3. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

Backend will run on `http://localhost:8080`

### 4. Frontend Setup

1. Navigate to frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Create `.env` file:
```bash
REACT_APP_API_URL=http://localhost:8080/api
```

4. Start development server:
```bash
npm start
```

Frontend will run on `http://localhost:3000`

## 🌐 Deployment

### Backend Deployment (Railway)

1. Create account at [Railway.app](https://railway.app)
2. Create new project
3. Add MySQL database service
4. Add Java service and connect to GitHub repository
5. Set environment variables:
   - `DATABASE_URL`: MySQL connection string
   - `DATABASE_USERNAME`: MySQL username
   - `DATABASE_PASSWORD`: MySQL password
   - `JWT_SECRET`: Strong secret key
   - `GEMINI_API_KEY`: Your Gemini API key
   - `CORS_ORIGINS`: Your frontend URL
   - `SPRING_PROFILES_ACTIVE`: prod
6. Deploy

### Backend Deployment (Render)

1. Create account at [Render.com](https://render.com)
2. Create new Web Service
3. Connect GitHub repository
4. Set build command: `cd backend && mvn clean package -DskipTests`
5. Set start command: `cd backend && java -jar target/roleledger-backend-1.0.0.jar`
6. Add MySQL database service
7. Set environment variables (same as Railway)
8. Deploy

### Frontend Deployment (Vercel)

1. Create account at [Vercel.com](https://vercel.com)
2. Import GitHub repository
3. Set root directory to `frontend`
4. Set build command: `npm install && npm run build`
5. Set output directory: `build`
6. Add environment variable:
   - `REACT_APP_API_URL`: Your deployed backend URL
7. Deploy

### Database Setup (Cloud)

#### Railway MySQL
1. Add MySQL service in Railway
2. Copy connection details
3. Update backend environment variables

#### Clever Cloud MySQL
1. Create account at [Clever Cloud](https://www.clever-cloud.com)
2. Create MySQL add-on
3. Copy connection string
4. Update backend environment variables

## 🔐 Environment Variables

### Backend
```bash
DATABASE_URL=jdbc:mysql://host:port/database
DATABASE_USERNAME=username
DATABASE_PASSWORD=password
JWT_SECRET=your-secret-key-min-256-bits
GEMINI_API_KEY=your-gemini-api-key
CORS_ORIGINS=https://your-frontend.vercel.app
SPRING_PROFILES_ACTIVE=prod
```

### Frontend
```bash
REACT_APP_API_URL=https://your-backend.railway.app/api
```

## 📁 Project Structure

```
roleledger/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/roleledger/
│   │   │   │   ├── config/          # Security & CORS config
│   │   │   │   ├── controller/       # REST controllers
│   │   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── entity/           # JPA entities
│   │   │   │   ├── enums/            # Enumerations
│   │   │   │   ├── exception/        # Exception handlers
│   │   │   │   ├── repository/       # Data repositories
│   │   │   │   ├── security/         # JWT & security
│   │   │   │   └── service/          # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/       # Reusable components
│   │   ├── context/          # React Context
│   │   ├── pages/            # Page components
│   │   ├── App.js
│   │   └── index.js
│   └── package.json
├── database/
│   └── schema.sql
├── README.md
├── railway.json
├── render.yaml
└── vercel.json
```

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/health` - Health check

### Applications
- `GET /api/applications` - Get all applications (authenticated)
- `GET /api/applications/{id}` - Get application by ID
- `POST /api/applications` - Create new application
- `PUT /api/applications/{id}` - Update application
- `DELETE /api/applications/{id}` - Delete application
- `GET /api/applications/dashboard/stats` - Get dashboard statistics

### AI Tools
- `POST /api/ai/interview-questions` - Generate interview questions
- `POST /api/ai/next-step-advice` - Get next step advice
- `POST /api/ai/resume-feedback` - Get resume feedback

## 🎯 Usage

1. **Sign Up**: Create a new account
2. **Add Applications**: Track your job/internship applications
3. **View Dashboard**: See statistics and visualizations
4. **Use AI Tools**:
   - Generate interview questions for specific roles
   - Get personalized preparation advice
   - Receive resume feedback

## 🧪 Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
npm test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is open source and available under the MIT License.

## 🙏 Acknowledgments

- Google Gemini AI for intelligent responses
- Spring Boot team for excellent framework
- Material UI for beautiful components
- Railway, Render, and Vercel for free hosting tiers

## 📧 Support

For issues and questions, please open an issue on GitHub.

---

**Built with ❤️ for students and professionals seeking placements**
