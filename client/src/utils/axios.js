import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 3000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export const login = async (email, password) => {

    const response = await instance.post('/auth/login', { email, password });

    const { token } = response.data;

    return token;
};

export const getMovies = async () => {

    const token = localStorage.getItem('token');

    const response = await instance.get('/movies', {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    const { movies } = response.data;

    return movies;
};
