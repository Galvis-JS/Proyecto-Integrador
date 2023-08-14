import "./ConfirmRegister.css";
import imageConfirm from "../../assets/Coworking.png";
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { confirmAccount } from "../../services/authService";
import { Loading } from "../loading/Loading";

const ConfirmRegister = () => {
    const [isLoading, setIsLoading] = useState(true)
    const [isConfirm, setIsConfirm] = useState(false)
    const {code} = useParams()

    useEffect(() => {
        
        confirmAccount(code).then(result => {
            console.log(code)
            console.log('result: ', result)
            if (result.status >= 200 && result.status < 300) {
                setIsLoading(false)
                setIsConfirm(true)
            } else {
                setIsConfirm(false)
            }
        })
    }, [])


  return (
    <>
    {isLoading ? <div className="confirm-wrapper"><Loading /></div> :
    isConfirm ? 
    <div className="confirm-wrapper">
    <div className="confirm-container">
        <img src={imageConfirm}></img>
        <div className="confirm-body">
        <h3>Bienvenido a</h3>
        <h3>Digital Booking</h3>
        <p>Gracias por confirmar tu registro.</p>
        <p>Empieza a disfrutar de la experiencia y encuentra tu espacio de trabajo ideal.</p> 
        <Link to="/login">
            <button>Comenzar</button> 
        </Link>        
        </div>

      
    </div>
</div>
    :
    <div className="confirm-wrapper">
        <div className="confirm-container">
            <img src={imageConfirm}></img>
            <div className="confirm-body">
            <h3>¡Oops..!</h3>
            <p>Tu enlace de confirmacion ha caducado.</p>
            <p>Por favor intenta registrarte nuevamente.</p> 
            <Link to="/signup">
                <button>Registrar</button> 
            </Link>            
            </div>

          
        </div>
    </div>    
    }
    </>

  );
};

export default ConfirmRegister;
