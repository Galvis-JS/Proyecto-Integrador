import "./categoryDetail.css";

import { Link, useParams } from 'react-router-dom';
import { useEffect, useState } from "react";

import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import { CoworkingCard } from "../../components/coworkingCard/CoworkingCard";
import { constants } from "../../services/constants";
import { getData } from "../../services/utils";

export const CategoryDetail = () => {

    const [category, setCategory] = useState({})
    const [coworkingByCategory, setCoworkingByCategory] = useState([])
    const { categoryId } = useParams()

    useEffect(() => {
        //obtener la categoria por id
        getData(constants.CATEGORIES_ENDPOINT + categoryId).then(data => { 
            setCategory(data)
        })
        //obtener las oficinas por categoria
        getData(constants.PRODUCTS_ENDPOINT).then(data => {
            const coworkingByCategory = data.filter(item => item.category.idCategory === parseInt(categoryId))
            setCoworkingByCategory(coworkingByCategory)
        });
    }, [categoryId]);


    return (
        <>
            {category && coworkingByCategory ?
                (
                    <>
                        <div className="title">
                            <h1>{category.name}</h1>
                            <Link className="arrowBack" to="/">
                                <ChevronLeftIcon sx={{ fontSize: 50 }} className="icon" />
                            </Link>
                        </div>
                        <div className="container">
                            {coworkingByCategory.map((coworking, index) => (
                                <CoworkingCard product={ coworking } key={index} />
                            ))}
                        </div>
                    </>
                ) : (<p>Cargando...</p>)}
        </>
    )
}