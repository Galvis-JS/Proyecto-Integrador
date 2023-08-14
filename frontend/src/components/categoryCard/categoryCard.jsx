import "./categoryCard.css";
import { Link } from "react-router-dom";

export const CategoryCard = ({idCategory, image, name, results}) => {
  // cardCategory va a recibir cada objeto Category del back
  // y vamos a mostrar una imagen representativa de la categoria
  // el nombre de la categoria y la cantidad de resultados para esa categoria aprox
  return (
    <>
     
      <div className="card-category">
        <Link to={`/category/${idCategory}`} className="card-category-link">
        <img
          src={image}
          className="card-category-img-top"
          alt="..."
        />
        </Link>
       
        
        <div className="card-category-body">
          {/* categoria */}
          <h4>{name}</h4>
          {/* cantidad de resultados */}
          <p>{results} oficinas en esta categoria</p>
        </div>
      </div>
    </>
  );
};
