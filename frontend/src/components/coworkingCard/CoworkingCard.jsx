import "./coworkingCard.css"
import { Icons } from "../icons/Icons";
import { Link } from 'react-router-dom';
import StarRating  from "../starRating/StarRating";


export const CoworkingCard = ({ product, clasePersonalizada}) => {
  // este componente va a recibir una oficina de buena valoracion
  // y de este vamos a extraer, la NOTA, el promedio ej: MUY BUENO, BUENO, etc
  // cant estrellas ?
  // distancia al centro de la ciudad en la que se encuentra
  // breve descripcion
  // comodidades ?
  const images = product.image.split(";");

  const imageStyle = {
    backgroundImage: `url(${images[0]})`
  }

  return (
    <>
      <div className={ clasePersonalizada ? `coworking-card ${clasePersonalizada}` : "coworking-card"}>
        <div className="coworking-card-img" style={ imageStyle }></div>
        <div className="coworking-card-container">
          <div className="coworking-card-top">
            <h3>{product.name}</h3>
            <h4>{product.category.name}</h4>
            <StarRating className="rating-container" 
                                    value={product.rating?.rating}
                                    cantidadVotos={product.rating?.valoraciones} 
                                    idCoworking={product.idCoworking} 
                                />
          </div>
          <div className="coworking-card-body">
            <div>
              <p> <Icons name="pin" /> {product.address} {product.city.country}</p>
              <p>MOSTRAR EN EL MAPA</p>
            </div>
          </div>
          <div className="coworking-card-footer">
            <p>{product.description.substring(0, 70)}...</p>
            <Link className="btn" to={`/detail/${product.idCoworking}`}>Ver Mas</Link>
          </div>
        </div>
      </div>
    </>
  );
};
