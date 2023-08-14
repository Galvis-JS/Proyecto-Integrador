import React, { useState } from 'react';
import { Star, StarBorder } from '@mui/icons-material';
import { constants } from '../../services/constants';
import Swal from 'sweetalert2';
import './starRating.css';

const StarRating = ({ value = 0, cantidadVotos = 0, idCoworking, className = '' }) => {
  const initialHasVoted = localStorage.getItem(`hasVoted-${idCoworking}`) === 'true';
  const [valoraciones, setValoraciones] = useState(cantidadVotos);
  const [rating, setRating] = useState(value);
  const [hasVoted, setHasVoted] = useState(initialHasVoted);
  const [isLoading, setIsLoading] = useState(false);

  const handleRating = async (puntajeElegido) => {
    if (!hasVoted && !isLoading) {
      setIsLoading(true);
      
      const data = new FormData();
      data.append('idCoworking', idCoworking);
      data.append('puntuacion', puntajeElegido);
      
      try {
        const response = await fetch(constants.RATING_ENDPOINT, {
          method: 'POST',
          body: data,
        });

        if (!response.ok) {
          throw new Error(`API request failed with status ${response.status}`);
        }

        const result = await response.json();

        console.log('Success:', result);
        setHasVoted(true);
        setRating(puntajeElegido);

        // Actulizo el promedio de rating y la cantidad de votos
        const nuevoPromedio = ((rating * valoraciones) + puntajeElegido) / (valoraciones + 1);
        setRating(nuevoPromedio);
        setValoraciones(valoraciones + 1);
        
        localStorage.setItem(`hasVoted-${idCoworking}`, 'true');
        
        Swal.fire('Gracias por votar!', '', 'success');

      } catch (error) {
        console.error('Error:', error);
      } finally {
        setIsLoading(false);
      }

    } else {
     Swal.fire('Ya has votado!', '', 'warning');
    }
  }

  return (
    <div className={className}>
      {[...Array(5)].map((star, i) => {
        const ratingValue = i + 1;
        return (
          <label key={i}>
            <input
              type="radio"
              name="rating"
              value={ratingValue}
              onClick={() => handleRating(ratingValue)}
              style={{ display: 'none' }}
            />
            {ratingValue <= rating ? (
              <Star className="star" />
            ) : (
              <StarBorder className="star" />
            )}
          </label>
        );
      })}
      <span className='rating-valoracion'>{rating.toFixed(1)} ({valoraciones})</span>
      {isLoading && <p>Votando...</p>}
    </div>
  );
};

export default StarRating;