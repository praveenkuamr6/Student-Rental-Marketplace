import axios from 'axios';
const api=axios.create({baseURL:import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'});
api.interceptors.request.use(config=>{const token=localStorage.getItem('sr_token');if(token)config.headers.Authorization=`Bearer ${token}`;return config;});
api.interceptors.response.use(r=>r,e=>{if(e.response?.status===401&&localStorage.getItem('sr_token')){localStorage.removeItem('sr_token');localStorage.removeItem('sr_user');}return Promise.reject(e);});
export default api;
