# Student Rental Marketplace — Java Full Stack

A student-to-student rental marketplace based on the supplied abstract. The repository has separate `backend/` and `frontend/` folders.

## Technology
- Backend: Java 17, Spring Boot 3.3, Spring Security, JWT, Spring Data JPA, Maven
- Frontend: React 18, Vite, React Router, Axios
- Database: PostgreSQL 16+
- Deployment: Render Blueprint/Docker for API, Vercel for frontend

## Run locally
### Requirements
Install JDK 17+, Maven 3.9+, Node.js 20+, npm, and Docker (or PostgreSQL 16). Start the local database with `docker compose up -d postgres`.

1. Start the local PostgreSQL container: `docker compose up -d postgres`. It creates the `student_rental_marketplace` database and local user automatically.
2. Configure `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`, `DB_SSLMODE`, and `JWT_SECRET` if needed (or edit `backend/src/main/resources/application.properties`). The defaults match `docker-compose.yml`.
3. Start the backend from the repository root:
   ```bash
   mvn -f backend/pom.xml spring-boot:run
   ```
   Alternatively, run `cd backend` first and then `mvn spring-boot:run`. Use `spring-boot:run` for local development; `spring-boot:start` is not the normal foreground startup goal.
4. In a second terminal start Vite:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
5. Open `http://localhost:5173`. Backend health endpoint: `http://localhost:8080/api/health`.

On Windows, `start-backend-windows.bat` and `start-frontend-windows.bat` are also provided.

## Deploy backend and database to Render

1. Push this project to a GitHub repository.
2. In Render, choose **New → Blueprint**, connect the repository, and select the root `render.yaml`. The Blueprint creates a PostgreSQL database and the API web service. The API connects to the database using Render-provided host, port, database, username, and password environment variables.
3. When prompted for `FRONTEND_ORIGIN`, enter your exact Vercel production origin, for example `https://your-project.vercel.app` (no trailing slash). Multiple comma-separated origins are supported if needed.
4. Render generates `JWT_SECRET` automatically. Keep database credentials and JWT secrets out of your Git repository and frontend environment variables.
5. Deploy and wait for the service health check to pass. Verify `https://YOUR-RENDER-SERVICE.onrender.com/api/health` returns JSON with `"status":"UP"`.

The service uses Render's `PORT` environment variable. Hibernate is configured with `ddl-auto=update`, so tables are created/updated on startup. For production-grade systems, use versioned database migrations and backups. Check the current limits and expiry/retention rules for your selected Render PostgreSQL plan before storing important data.

### Alternative: run the API in Docker locally
```bash
cd backend
docker build -t student-rental-api .
docker run --rm -p 8080:8080 \
  -e DB_HOST=host.docker.internal -e DB_PORT=5432 \
  -e DB_NAME=student_rental_marketplace \
  -e DB_USERNAME=student_rental_user -e DB_PASSWORD=student_rental_password \
  -e DB_SSLMODE=disable \
  -e JWT_SECRET='replace-with-a-random-secret-at-least-32-bytes-long' \
  -e FRONTEND_ORIGIN=http://localhost:5173 student-rental-api
```

## Deploy frontend to Vercel

1. Push the project to GitHub.
2. In Vercel, import the repository and set **Root Directory** to `frontend`.
3. Framework preset: **Vite**. Build command: `npm run build`. Output directory: `dist`. Install command: `npm install`.
4. Add this environment variable in Vercel's project settings for Production (and Preview if required):
   - `VITE_API_BASE_URL` = `https://YOUR-RENDER-SERVICE.onrender.com/api`
   Replace the example with your real Render service URL. It must end with `/api`.
5. Deploy/redeploy after setting the environment variable. The included `frontend/vercel.json` supports React Router routes on refresh.
6. Ensure the Render `FRONTEND_ORIGIN` matches the deployed Vercel origin exactly. If using a Vercel preview domain, add that exact origin to the comma-separated list and redeploy the API.

Vite environment variables are embedded at build time, so changing `VITE_API_BASE_URL` requires a new Vercel deployment. Never put database passwords or JWT secrets in frontend variables.

## Demo accounts
Seeded on first backend startup:
- Admin: `admin@rental.local` / `Admin@12345`
- Student: `student@rental.local` / `Student@12345`

Five example listings are seeded when the items table is empty. These credentials are for demonstration only. Change the seeded passwords before using a publicly accessible deployment; do not use the demo credentials for real user data.

## Features
- Student registration, login, JWT authentication, hashed passwords
- Public item browsing, keyword search, category/location filters
- Create, edit, delete own rental listings
- Date-based booking requests, owner accept/reject, booking history and return/completion flow
- Reviews after completed rentals
- Admin dashboard, platform stats, student verification, listing moderation
- Light/dark theme saved in browser local storage
- Responsive React UI and API error messages

## API overview
- `POST /api/auth/register`, `POST /api/auth/login`, `GET /api/auth/me`
- `GET /api/health`
- `GET /api/items?q=&category=&location=`, `GET /api/items/mine`, `POST /api/items`, `PUT/DELETE /api/items/{id}`
- `GET/POST /api/bookings`, `PATCH /api/bookings/{id}/status`, `PATCH /api/bookings/{id}/return`
- `GET/POST /api/reviews`
- `GET /api/admin/stats`, `GET /api/admin/users`, `PATCH /api/admin/users/{id}/verify`, `GET /api/admin/items`, `DELETE /api/admin/items/{id}`

## Scope and deployment cautions
This is an educational project, not a production payment system. Deposits are recorded as booking information; no payment gateway is connected. QR handover and real-time notifications are not implemented. Use hosted image URLs. Before public launch, add database migrations, stricter rate limiting, secure admin credential rotation, monitoring, backups, and a production security review.
