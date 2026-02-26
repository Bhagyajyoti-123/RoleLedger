# RoleLedger Setup Guide

## Quick Start Guide

### Step 1: Get Gemini API Key

1. Go to [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Sign in with your Google account
3. Click "Create API Key"
4. Copy the API key (you'll need it for backend configuration)

### Step 2: Database Setup

#### Local MySQL Setup
```bash
# Install MySQL if not already installed
# Windows: Download from mysql.com
# Mac: brew install mysql
# Linux: sudo apt-get install mysql-server

# Start MySQL service
# Windows: Start MySQL service from Services
# Mac/Linux: sudo systemctl start mysql

# Create database
mysql -u root -p
CREATE DATABASE roleledger;
USE roleledger;
SOURCE database/schema.sql;
EXIT;
```

### Step 3: Backend Configuration

1. Navigate to `backend` directory
2. Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   gemini.api.key=your_gemini_api_key_here
   jwt.secret=your-super-secret-jwt-key-minimum-256-bits-long
   ```

3. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

   Backend should start on `http://localhost:8080`

### Step 4: Frontend Configuration

1. Navigate to `frontend` directory
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

   Frontend should start on `http://localhost:3000`

### Step 5: Test the Application

1. Open browser to `http://localhost:3000`
2. Click "Sign Up" to create an account
3. Add your first application
4. Try the AI Tools!

## Troubleshooting

### Backend won't start
- Check MySQL is running: `mysql -u root -p`
- Verify database exists: `SHOW DATABASES;`
- Check application.properties has correct credentials
- Ensure port 8080 is not in use

### Frontend can't connect to backend
- Verify backend is running on port 8080
- Check `.env` file has correct `REACT_APP_API_URL`
- Check browser console for CORS errors
- Ensure backend CORS config includes `http://localhost:3000`

### AI features not working
- Verify Gemini API key is correct
- Check backend logs for API errors
- Ensure you have API quota remaining (free tier has limits)
- Check network connectivity

### Database connection errors
- Verify MySQL is running
- Check username/password in application.properties
- Ensure database `roleledger` exists
- Check MySQL user has proper permissions

## Production Deployment

See README.md for detailed deployment instructions for:
- Railway (Backend + Database)
- Render (Backend + Database)
- Vercel (Frontend)

## Need Help?

- Check the main README.md for detailed documentation
- Review error logs in backend console
- Check browser developer console for frontend errors
- Ensure all environment variables are set correctly
