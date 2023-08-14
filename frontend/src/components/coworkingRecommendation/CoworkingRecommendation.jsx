import "./coworkingRecommendation.css"

import { useEffect, useState } from "react"

import { CoworkingCard } from "../coworkingCard/CoworkingCard"
import { constants } from "../../services/constants"
import { getData } from "../../services/utils"
import { Loading } from "../loading/Loading"

export const CoworkingRecommendation = () => {

  const [coworkingRecommendation, setCoworkingRecommendation] = useState([]);

  useEffect(() => {
    getData(constants.PRODUCTS_ENDPOINT).then(data => {
      setCoworkingRecommendation(data)
    });
  }, []);


  const getRandomList = (array) => {
    let arrayAleatorio = [...array]; // crea una copia del array original para no modificarlo
    for (let i = arrayAleatorio.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1)); // selecciona un índice aleatorio
      [arrayAleatorio[i], arrayAleatorio[j]] = [arrayAleatorio[j], arrayAleatorio[i]]; // intercambia los elementos
    }
    return arrayAleatorio;
  }

  return (

    <div className="coworking-recommendation">
      <h2>Recomendaciones</h2>
      {coworkingRecommendation.length > 0 ?
        (
          <div className="coworking-recommendation-container">
            {getRandomList(coworkingRecommendation).map(item => (
              <CoworkingCard product={{...item}} key={item.idCoworking} />
            ))}
          </div>
        ) : (<Loading/>)}
    </div>
  )
}
