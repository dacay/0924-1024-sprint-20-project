import React, { useEffect, useState } from 'react';

import { toast } from 'react-toastify';
import { useHistory } from 'react-router-dom';

import Movie from './Movie';

import { getMovies } from '../utils/axios';

const Home = () => {

  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);

  const history = useHistory();

  const isLoggedIn = localStorage.getItem('token');

  useEffect(() => {

    (async () => {

      try {

        const movies = await getMovies();

        setMovies(movies);
  
      } catch(err) {

        console.error('Error fetching movies:', err);

        toast.error('Error fetching movies');

      } finally {

        setLoading(false);
      }
      
    })();

    // getMovies()
    //   .then((movies) => setMovies(movies))
    //   .catch((err) => console.error('Error fetching movies:', err));

  }, []);

  const handleLogout = () => {

    localStorage.removeItem('token');

    history.push('/login');
  }

  const movieElements = movies.map(origMovie => {

    const movie = {
      ...origMovie,
      categories: origMovie.categories.map(c => c.name)
    }

    return <Movie key={movie.id} title={movie.title} description={movie.description} categories={movie.categories} />
  });

  return loading ? <div>Loading...</div> : (
    <div>
      <h1 className='py-5 font-bold text-2xl flex flex-col items-center'>Home Page</h1>
      <div className='flex flex-wrap flex-col items-center gap-8'>
        {movieElements}
      </div>
      {isLoggedIn && <div className='my-6'>
        <button className='bg-blue-500 text-white px-4 py-2 rounded-md' onClick={handleLogout}>Logout</button>
      </div>}
    </div>
  );
};

export default Home; 