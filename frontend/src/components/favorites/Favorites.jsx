import "./favorites.css";

import { useEffect, useState } from "react";

import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import { CoworkingCard } from "../coworkingCard/CoworkingCard";
import { Link } from "react-router-dom";
import { Loading } from "../loading/Loading";
import React from "react";
import { constants } from "../../services/constants";
import { getDataAuth } from "../../services/utils";

export const Favorites = () => {

    const [favoritesList, setFavoritesList] = useState(null);

    useEffect(() => {
        getDataAuth(`${constants.FAVORITES_ENDPOINT}user`)
            .then(data => setFavoritesList(data));
    }, [])


    return (
        <div className="coworking-favoritos">

            <div className="title">
                <h1>Mis Coworking Favoritos</h1>
                <Link className="arrowBack" to={`/`}>
                    <ChevronLeftIcon sx={{ fontSize: 50 }} className="icon" />
                </Link>
            </div>

            {
                favoritesList === null 
                    ? (<Loading/>) 
                    : favoritesList?.length == 0 
                        ? (<h3 className="sin-coworking">No tienes Coworking Favoritos</h3>) 
                        : (
                            <div className="coworking-favorito-container">
                                {favoritesList.map((fav, index) => <CoworkingCard key={index} product={fav.coworking} />)}
                            </div>
                        ) 
            }
        </div>
    )

}