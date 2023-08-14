import "./AdminHome.css";
import imageConfirm from "../../assets/CoworkingAdmin.png";
import { Link } from "react-router-dom";

const AdminHome = () => {
  return (
    <div className="adminhome-wrapper">
    <div className="adminhome-container">
        <img src={imageConfirm}></img>
        <div className="adminhome-body">
        <h3>Panel de Administrador</h3>
        <Link to="/admin/Products">
            <button className="btn-office">Oficinas</button> 
        </Link> 
        <Link to="/admin/Categories">
            <button className="btn-category">Categorias</button> 
        </Link>   
        <Link to="/admin/Cities">
            <button className="btn-city">Ciudades</button> 
        </Link>   
        <Link to="/admin/users">
            <button className="btn-user">Usuarios</button> 
        </Link>          
        </div>
    </div>
    </div>
  )
}

export default AdminHome;