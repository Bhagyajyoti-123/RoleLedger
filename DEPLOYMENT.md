# Deployment Guide

## Prerequisites

- GitHub account
- Railway/Render account (for backend)
- Vercel account (for frontend)
- Google Gemini API key

## Backend Deployment (Railway)

### Step 1: Prepare Repository
1. Push code to GitHub
2. Ensure `railway.json` is in root directory

### Step 2: Deploy on Railway
1. Go to [railway.app](https://railway.app)
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Select your repository
5. Railway will detect Java and build automatically

### Step 3: Add MySQL Database
1. Click "New" → "Database" → "MySQL"
2. Railway will create MySQL instance
3. Copy connection details

### Step 4: Configure Environment Variables
In Railway dashboard, add these variables:

```
DATABASE_URL=jdbc:mysql://host:port/railway
DATABASE_USERNAME=root
DATABASE_PASSWORD=<from_railway>
JWT_SECRET=<generate_strong_secret_256_bits_min>
GEMINI_API_KEY=<your_gemini_key>
CORS_ORIGINS=https://your-frontend.vercel.app
SPRING_PROFILES_ACTIVE=prod
PORT=8080
```

### Step 5: Deploy
1. Railway will automatically deploy
2. Copy the deployment URL (e.g., `https://your-app.railway.app`)

## Backend Deployment (Render)

### Step 1: Prepare Repository
1. Push code to GitHub
2. Ensure `render.yaml` is in root directory

### Step 2: Deploy on Render
1. Go to [render.com](https://render.com)
2. Click "New" → "Web Service"
3. Connect GitHub repository
4. Configure:
   - **Name**: roleledger-backend
   - **Environment**: Java
   - **Build Command**: `cd backend && mvn clean package -DskipTests`
   - **Start Command**: `cd backend && java -jar target/roleledger-backend-1.0.0.jar`
   - **Instance Type**: Free

### Step 3: Add MySQL Database
1. Click "New" → "PostgreSQL" (or MySQL if available)
2. Render will create database
3. Copy connection string

### Step 4: Configure Environment Variables
Same as Railway (see above)

### Step 5: Deploy
1. Click "Create Web Service"
2. Render will build and deploy
3. Copy the deployment URL

## Frontend Deployment (Vercel)

### Step 1: Prepare Repository
1. Ensure code is on GitHub
2. Ensure `vercel.json` is in root directory

### Step 2: Deploy on Vercel
1. Go to [vercel.com](https://vercel.com)
2. Click "Add New" → "Project"
3. Import GitHub repository
4. Configure:
   - **Framework Preset**: React
   - **Root Directory**: `frontend`
   - **Build Command**: `npm install && npm run build`
   - **Output Directory**: `build`

### Step 3: Configure Environment Variables
Add in Vercel dashboard:

```
REACT_APP_API_URL=https://your-backend.railway.app/api
```

Or if using Render:

```
REACT_APP_API_URL=https://your-backend.onrender.com/api
```

### Step 4: Deploy
1. Click "Deploy"
2. Vercel will build and deploy
3. Copy the deployment URL

### Step 5: Update Backend CORS
Update backend `CORS_ORIGINS` environment variable to include your Vercel URL:

```
CORS_ORIGINS=https://your-app.vercel.app
```

## Database Setup (Cloud)

### Railway MySQL
- Automatically created when you add MySQL service
- Connection details available in service dashboard
- Use these in backend environment variables

### Clever Cloud MySQL (Alternative)
1. Go to [clever-cloud.com](https://www.clever-cloud.com)
2. Create account
3. Add MySQL add-on
4. Copy connection string
5. Update backend `DATABASE_URL`

## Post-Deployment Checklist

- [ ] Backend is accessible (check `/api/health`)
- [ ] Frontend can connect to backend
- [ ] Database is connected
- [ ] Authentication works (signup/login)
- [ ] AI features work (test with Gemini API)
- [ ] CORS is configured correctly
- [ ] Environment variables are set
- [ ] SSL/HTTPS is enabled (automatic on Vercel/Railway/Render)

## Monitoring

### Railway
- View logs in Railway dashboard
- Monitor resource usage
- Check deployment status

### Render
- View logs in Render dashboard
- Monitor service health
- Check build logs

### Vercel
- View deployment logs
- Monitor analytics
- Check function logs

## Troubleshooting Deployment

### Backend won't start
- Check build logs for errors
- Verify environment variables are set
- Check database connection string
- Ensure Java version is compatible

### Frontend build fails
- Check Node.js version (should be 16+)
- Verify all dependencies install correctly
- Check for TypeScript/ESLint errors
- Review build logs

### CORS errors
- Verify `CORS_ORIGINS` includes frontend URL
- Check backend CORS configuration
- Ensure URLs match exactly (including https)

### Database connection fails
- Verify connection string format
- Check database is accessible from backend
- Ensure credentials are correct
- Check firewall/network settings

## Cost Estimation

### Free Tier Limits:
- **Railway**: $5 free credit/month
- **Render**: Free tier available (with limitations)
- **Vercel**: Free tier (generous limits)
- **Gemini API**: Free tier (60 requests/minute)

For production use beyond free tiers, consider:
- Upgrading to paid plans
- Using alternative free services
- Self-hosting on VPS

## Security Checklist

- [ ] JWT secret is strong and random
- [ ] Database credentials are secure
- [ ] API keys are in environment variables (not in code)
- [ ] CORS is restricted to frontend domain only
- [ ] HTTPS is enabled
- [ ] Database has proper access controls
- [ ] Regular backups are configured
