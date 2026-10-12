@echo off
cd /d "%~dp0frontend"
set VITE_API_BASE_URL=http://localhost:8080/api
if not exist node_modules call npm install
npm run dev
