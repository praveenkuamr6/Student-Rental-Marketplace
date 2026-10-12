@echo off
cd /d "%~dp0backend"
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=student_rental_marketplace
set DB_USERNAME=student_rental_user
set DB_PASSWORD=student_rental_password
set DB_SSLMODE=disable
set JWT_SECRET=local-demo-secret-key-change-this-before-deployment-123456789
set FRONTEND_ORIGIN=http://localhost:5173
mvn spring-boot:run
