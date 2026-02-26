# RoleLedger Quick Start

## 🚀 Get Started in 5 Minutes

### Prerequisites Check
- ✅ Java 17+ installed (`java -version`)
- ✅ Maven installed (`mvn -version`)
- ✅ Node.js 16+ installed (`node -v`)
- ✅ MySQL 8+ installed and running
- ✅ Gemini API key ([Get it here](https://makersuite.google.com/app/apikey))

### 1. Database Setup (2 minutes)
```bash
mysql -u root -p
CREATE DATABASE roleledger;
USE roleledger;
SOURCE database/schema.sql;
EXIT;
```

### 2. Backend Setup (1 minute)
```bash
cd backend
# Edit src/main/resources/application.properties with your MySQL password and Gemini API key
mvn clean install
mvn spring-boot:run
```
✅ Backend running on http://localhost:8080

### 3. Frontend Setup (1 minute)
```bash
cd frontend
npm install
# Create .env file with: REACT_APP_API_URL=http://localhost:8080/api
npm start
```
✅ Frontend running on http://localhost:3000

### 4. Test It! (1 minute)
1. Open http://localhost:3000
2. Sign up with your email
3. Add an application
4. Try AI Tools!

## 🎯 What You Get

- ✅ Full authentication system
- ✅ Application tracking (CRUD)
- ✅ Dashboard with charts
- ✅ AI-powered interview questions
- ✅ Context-aware preparation advice
- ✅ Resume feedback assistant

## 📚 Next Steps

- Read [SETUP.md](SETUP.md) for detailed setup
- Read [DEPLOYMENT.md](DEPLOYMENT.md) for production deployment
- Read [README.md](README.md) for full documentation

## 🆘 Need Help?

- Check backend logs for errors
- Check browser console for frontend errors
- Verify all environment variables are set
- Ensure MySQL is running
- Verify Gemini API key is correct

Happy coding! 🎉
