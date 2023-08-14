import "./coworkingDetail.css";

import { Link, useNavigate, useParams } from 'react-router-dom';
import { getData, getDataAuth, getStringFromDate } from '../../services/utils';
import { useContext, useEffect, useState } from "react";

import AcUnitIcon from '@mui/icons-material/AcUnit';
import AppContext from "../../context/AppContext";
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import CoffeeIcon from '@mui/icons-material/Coffee';
import DirectionsCarIcon from '@mui/icons-material/DirectionsCar';
import FavoriteButton from "../../components/favorites/FavoriteButton";
import { Loading } from "../../components/loading/Loading";
import LocationOnIcon from '@mui/icons-material/LocationOn';
import Map from "../../components/maps/Map";
import PrintIcon from '@mui/icons-material/Print';
import ReservationCalendar from "../../components/reservationCalendar/ReservationCalendar";
import SocialMediaSharing from "../../components/socialMediaSharing/SocialMediaSharing";
import StarRating from "../../components/starRating/StarRating";
import TvIcon from '@mui/icons-material/Tv';
import WifiIcon from '@mui/icons-material/Wifi';
import { constants } from "../../services/constants";

const ReservationButton = ({ coworkingId }) => {
    const { isAuthGlobalState } = useContext(AppContext);
    const navigate = useNavigate();

    const handleOnClick = () => {
        if (isAuthGlobalState()) {
            navigate(`/reservation/${coworkingId}`);
        } else {
            localStorage.setItem('redirected', 'true'); // Guarda el estado en el almacenamiento local
            localStorage.setItem('lastLocation', `/detail/${coworkingId}`); // Guarda la última ubicación en el almacenamiento local
            navigate('/login');
        }
    };

    return (
        <button className="btn" onClick={handleOnClick}>Reservar</button>
    );
};

export const CoworkingDetail = () => {

    const { setCheckIn, setCheckOut } = useContext(AppContext);
    const [ favId, setFavId ] = useState(null);
    const [singleOffice, setSingleOffice] = useState({})
    const [reservations, setReservations] = useState([])
    const { id } = useParams()

    const facilitiesMap = {
        "Wifi": <WifiIcon className="icon" />,
        "Aire acondicionado": <AcUnitIcon className="icon" />,
        "Smart TV": <TvIcon className="icon" />,
        "Estacionamiento": <DirectionsCarIcon className="icon" />,
        "Cafe": <CoffeeIcon className="icon" />,
        "Impresora": <PrintIcon className="icon" />
    }

    useEffect(() => {
        getData(constants.PRODUCTS_ENDPOINT + id).then(data => {
            data.images = data.image.split(";");
            setSingleOffice(data)
        });
        getData(`${constants.RESERVATIONS_ENDPOINT}coworking?coworking=${id}`).then(data => {
            const reservationsMap = data.map(reservation => {
                return {
                    start: reservation.start_date.split("T")[0],
                    end: reservation.end_date.split("T")[0],
                }
            });
            setReservations(reservationsMap);
        });
        getDataAuth(`${constants.FAVORITES_ENDPOINT}user`)
            .then(data => {
                if (data === null) {
                    setFavId(0);
                    return;
                }
                
                const currentFav = data.filter(fav => {
                    return fav.coworking.idCoworking === parseInt(id);
                });
                if (currentFav.length > 0) {
                    setFavId(currentFav[0].id);
                }else {
                    setFavId(0);
                }
            });
    }, [id])

    const handleSelect = (ranges) => {
        const { startDate, endDate } = ranges.selection;
        setCheckIn(getStringFromDate(startDate));
        setCheckOut(getStringFromDate(endDate));
    }

    return (
        <>
            {singleOffice && singleOffice.images ?
                (
                    <>
                        <div className="title">
                            <h1>{singleOffice.name}</h1>
                            <Link className="arrowBack" to="/">
                                <ChevronLeftIcon sx={{ fontSize: 50 }} className="icon" />
                            </Link>
                        </div>
                        <div className="star-address">
                            <div className="address">
                                <p className="icon-container"> <LocationOnIcon className="icon" />
                                    <span>
                                        {singleOffice.address} {singleOffice.city.name}
                                    </span>
                                </p>
                            </div>
                            <div className="icon-container">
                                <StarRating
                                    value={singleOffice.rating?.rating}
                                    cantidadVotos={singleOffice.rating?.valoraciones}
                                    idCoworking={singleOffice.idCoworking}
                                />
                            </div>
                        </div>
                        <div className="container redes">
                            <SocialMediaSharing
                                message={`¡Mira nuestra oficina ${singleOffice.name}! Es parte de nuestra categoria ${singleOffice.category.name}. ${singleOffice.description}`}
                                hastags={["Coworking"]}
                                image={singleOffice.image} />
                            { favId !== null && <FavoriteButton idCoworking={singleOffice.idCoworking} favId={favId} /> }
                        </div>

                        <div className="container">
                            <div className="css-grid">
                                <div className="item large">
                                    <img src={singleOffice.images[0]} alt="Imagen principal del producto" className="main-image" />
                                </div>
                                {singleOffice.images.slice(1).map((image, index) => (
                                    <div key={index} className="item">
                                        <img src={image} alt={`Imagen ${index + 1}`} />
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div className="container">
                            <h2 className="category"> Categoria: {singleOffice.category.name}</h2>
                            <p>{singleOffice.description}</p>
                        </div>
                        <div className="container with-border-bottom">
                            <h2 className="category">Comodidades</h2>
                        </div>
                        <div className="container comodidades">
                            <div className="css-grid">

                                {(singleOffice?.coworkingFacilities?.length || 0) > 0 && singleOffice.coworkingFacilities.map((item, index) => {
                                    return (
                                        <div key={index} className="item icon-container">
                                            {facilitiesMap[item.facility.name]}
                                            <span>{item.facility.name}</span>
                                        </div>
                                    )
                                })}
                            </div>
                        </div>
                        <div className="container calendario">
                            <h2 className="category">Fechas disponibles</h2>
                            <div className="reserva">
                                <ReservationCalendar fechasNoDisponibles={reservations} onChange={handleSelect} />
                                <div className="botonReserva">
                                    <h3 className="category">Agregá tus fechas de reservas para obtener precios exactos</h3>
                                    <ReservationButton coworkingId={singleOffice.idCoworking} />
                                </div>
                            </div>
                        </div>
                        <div className="container with-border-bottom">
                            <h2 className="category">Ubicación</h2>
                        </div>
                        <div className="container">
                            <Map lat={singleOffice.latitude} lng={singleOffice.longitude} address={singleOffice.address} />
                        </div>
                        <div className="container with-border-bottom">
                            <h2 className="category">Qué tenés que saber</h2>
                        </div>

                        <div className="container policies">
                            <div>
                                <h3 className="category">Normas del Coworking</h3>
                                <p>{singleOffice.coworkingRulesPolicy}</p>
                            </div>
                            <div>
                                <h3 className="category">Salud y seguridad</h3>
                                <p>{singleOffice.healthSafetyPolicy}</p>
                            </div>
                            <div>
                                <h3 className="category">Politicas de cancelación</h3>
                                <p>{singleOffice.cancellationPolicy}</p>
                            </div>
                        </div>


                    </>
                ) : (<Loading />)}
        </>
    )
}