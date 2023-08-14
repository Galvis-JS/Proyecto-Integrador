import React, { useContext, useState } from 'react';
import { deleteData, postData } from '../../services/utils';

import AppContext from '../../context/AppContext';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import FavoriteIcon from '@mui/icons-material/Favorite';
import IconButton from '@mui/material/IconButton';
import Swal from "sweetalert2";
import { constants } from '../../services/constants';
import { red } from '@mui/material/colors';

const FavoriteButton = ({ idCoworking, favId }) => {

    const [isFavorite, setIsFavorite] = useState(favId ? true : false);
    const [currentFavId, setCurrentFavId] = useState(favId);
    const { isAuthGlobalState } = useContext(AppContext);

    

    const handleOnClick = () => {
        if (!isAuthGlobalState()) {
            Swal.fire({
                title: "Login requerido!",
                text: "Inicia seción para agregar coworking a favoritos",
                icon: "warning",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#F2921D",
            })
            return;
        }

        if (isFavorite) {
            setIsFavorite(false)
            deleteData(constants.FAVORITES_ENDPOINT + currentFavId)
        } else {
            setIsFavorite(true)
            postData(constants.FAVORITES_ENDPOINT, { idCoworking }).then(data => {
                if (data) {
                    setCurrentFavId(data.id)
                }
            })
        }
    }

    return (
        <IconButton onClick={() => { handleOnClick() }}>
            {isFavorite ? <FavoriteIcon sx={{ color: red[500] }} /> : <FavoriteBorderIcon />}
        </IconButton>
    )
}

export default FavoriteButton;