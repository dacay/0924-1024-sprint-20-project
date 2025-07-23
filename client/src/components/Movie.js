import React from 'react';
import PropTypes from 'prop-types';

const Movie = ({ title, description, categories }) => {

  console.log("categories", categories);

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden transition-transform hover:shadow-lg hover:scale-[1.01] w-1/3">
      <div className="p-5">
        <h2 className="text-xl font-bold text-gray-800 mb-2">{title}</h2>
        
        {/* Categories */}
        <div className="flex flex-wrap gap-2 mb-3 justify-center">
          {categories.map((category, index) => (
            <span 
              key={index} 
              className="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded-full"
            >
              {category}
            </span>
          ))}
        </div>
        
        {/* Description */}
        <p className="text-gray-600 text-sm">{description}</p>
      </div>
    </div>
  );
};

Movie.propTypes = {
  title: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
  categories: PropTypes.arrayOf(PropTypes.string).isRequired
};

export default Movie; 