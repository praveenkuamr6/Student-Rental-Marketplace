@echo off
cd /d "%~dp0backend"
set DB_USERNAME=root
set DB_PASSWORD=root
set JWT_SECRET=local-demo-secret-key-change-this-before-deployment-123456789
mvn spring-boot:run
