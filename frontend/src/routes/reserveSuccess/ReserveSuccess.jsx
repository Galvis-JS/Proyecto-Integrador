import { useContext } from "react";
import "./reservesuccess.css";
import VerifiedIcon from '@mui/icons-material/Verified';
import AppContext from "../../context/AppContext";
import { useNavigate } from "react-router-dom";

export const ReserveSuccess = () => {

    const navigate = useNavigate();

    const { reservation, checkIn, checkOut, globalState: userGlobalState } = useContext(AppContext);

    return (
        <div className="container-success">
            <div className="success">
                <div className="body">
                    <VerifiedIcon className="verified-icon" />
                    <h1 className="title">¡Muchas gracias!</h1>
                    <p className="message">Su reserva se realizó con éxito.</p>
                    <div className="body-info">
                        <h4>Datos del servicio</h4>
                        <div className="body-sub-info">
                            <p>Coworking:</p>
                            <p>{reservation.coworking.name}</p>
                        </div>
                        <div className="body-sub-info">
                            <p>Ubicación:</p>
                            <p>{reservation.coworking.address}</p>
                        </div>
                        <div className="body-sub-info">
                            <p>Fecha de reserva:</p>
                            <p>{reservation.fechaReserva}</p>
                        </div>
                        <div className="body-sub-info">
                            <p>Check in:</p>
                            <p>{checkIn}</p>
                        </div>
                        <div className="body-sub-info">
                            <p>Check out:</p>
                            <p>{checkOut}</p>
                        </div>
                        <h4>Datos Personales</h4>
                        <div className="body-sub-info">
                            <p>Nombre:</p>
                            <p>{reservation.userInfo.nombre}</p>
                        </div>
                        <div className="body-sub-info">
                            <p>Apellido:</p>
                            <p>{reservation.userInfo.apellido}</p>
                        </div>
                        <div className="body-sub-info">
                            <p>Email:</p>
                            <p>{reservation.userInfo.email}</p>
                        </div>
                        
                    </div>
                </div>
                <button className="btn button-success"  onClick={() => navigate("/")}>Ok</button>
            </div>
        </div>
    )
}