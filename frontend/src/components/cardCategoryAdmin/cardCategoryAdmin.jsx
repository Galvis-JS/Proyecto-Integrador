import "./cardCategoryAdmin.css";
import { Link } from "react-router-dom";
import { Icons } from "../icons/Icons";
import { NavLink } from "react-router-dom";
import Swal from "sweetalert2";
import * as categoryService from "../../services/categoryServices";

export const CardCategoryAdmin = ({
  idCategory,
  image,
  name,
  results,
  description,
  onDelete,
}) => {
  // cardCategory va a recibir cada objeto Category del back
  // y vamos a mostrar una imagen representativa de la categoria
  // el nombre de la categoria y la cantidad de resultados para esa categoria aprox

  const confirmation = () => {
    Swal.fire({
      title: "¿Esta seguro que desea eliminar la categoria?",
      showDenyButton: true,
      confirmButtonText: "Si",
      denyButtonText: "Cancelar",
      confirmButtonColor: "#F2921D",
      denyButtonColor: "#A61F69",
    }).then((result) => {
      if (result.isConfirmed) {
        handleDelete();
      }
    });
  };

  const handleDelete = () => {
    categoryService.categoryDelete(idCategory).then(async (result) => {
      if (result.status >= 200 && result.status < 300) {
        onDelete();
        Swal.fire({
          title: "Eliminada exitosamente",
          text: "La categoria ha sido eliminada correctamente.",
          icon: "success",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#A61F69",
        });
      } else if (result.status == 409) {
        Swal.fire({
          title: "Error",
          text: "No es posible eliminar una categoria con productos asociados.",
          icon: "error",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#F2921D",
        });
      } else {
        Swal.fire({
          title: "Error",
          text: "No fue posible eliminar la categoria.",
          icon: "error",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#F2921D",
        });
      }
    });
  };

  return (
    <>
      <div className="admincard-category">
        <div className="admincard-category-container">
          <img src={image} className="admincard-category-img" alt="..." />
          <div className="trash-container" onClick={confirmation}>
            <Icons name="trash" />
          </div>
          {/* <NavLink
        to={`/admin/editproduct/${idCategory}`}
        className="edit-container"
      >
        <Icons name="edit" />
      </NavLink> */}
        </div>
        <div className="admincard-category-body">
          <h4>{name}</h4>
          <p>{description}</p>
          <p>{results} oficinas en esta categoria</p>
        </div>
      </div>
    </>
  );
};
