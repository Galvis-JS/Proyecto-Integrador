import React, { useEffect, useState, useMemo } from "react";
import { CardProduct } from "../cardProduct/cardProduct";
import * as cityService from "../../services/cityServices";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Icons } from "../icons/Icons";
import { Link } from "react-router-dom";
import { Loading } from "../loading/Loading";
import { NavLink } from "react-router-dom";
import Swal from "sweetalert2";
//import "primereact/resources/themes/soho-light/theme.css";
import "./Cities.css";

const Cities = () => {
  const [cities, setCities] = useState([]);

  useEffect(() => {
    cityService
      .cityAll()
      .then((response) => {
        return response;
      })
      .then((city) => {
        setCities(city);
      });
  }, []);

  const confirmation = (id) => {
    Swal.fire({
      title: "¿Esta seguro que desea eliminar la ciudad?",
      showDenyButton: true,
      confirmButtonText: "Si",
      denyButtonText: "Cancelar",
      confirmButtonColor: "#F2921D",
      denyButtonColor: "#A61F69",
    }).then((result) => {
      if (result.isConfirmed) {
        handleDelete(id);
      }
    });
  };

  const handleDelete = (id) => {
    cityService.cityDelete(id).then(async (result) => {
      setCities(cities.filter((cit) => cit.idCity != id));
      if (result.status >= 200 && result.status < 300) {
        Swal.fire({
          title: "Eliminada exitosamente",
          text: "La ciudad ha sido eliminada correctamente.",
          icon: "success",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#A61F69",
        });
      } else {
        Swal.fire({
          title: "Error",
          text: "No fue posible eliminar la ciudad. Verifique que la ciudad no tenga oficinas asociadas.",
          icon: "error",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#F2921D",
        });
      }
    });
  };

  const actions = (data, props) => {
    return (
      <div className="actions-container">
        <div
          className="trash-container"
          onClick={() => {
            confirmation(data.idCity);
          }}
        >
          <Icons name="trash" />
        </div>
        <NavLink to={`/admin/editcity/${data.name}`} className="edit-container">
          <Icons name="edit" />
        </NavLink>
      </div>
    );
  };

  return (
    <div className="cities-container">
      {cities.length >= 1 ? (
        <DataTable
          value={cities}
          stripedRows
          paginator
          rows={8}
          tableStyle={{ minWidth: "50rem" }}
        >
          <Column
            field="country"
            header="Pais"
            sortable
            style={{ width: "25%" }}
          ></Column>
          <Column
            field="name"
            header="Ciudad"
            sortable
            style={{ width: "25%" }}
          ></Column>
          <Column
            field="idCity"
            header="Acciones"
            body={actions}
            style={{ width: "25%" }}
          ></Column>
        </DataTable>
      ) : (
        <div className="loading-container">
          <Loading />
        </div>
      )}
    </div>
  );
};

export default Cities;
