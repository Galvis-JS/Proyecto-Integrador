import React, { useState } from "react";
import useForm from "../../hooks/useForm";
import * as cityService from "../../services/cityServices";
import citiesList from "../../data/cities";
import Swal from "sweetalert2";
import "./CityForm.css";

const CityForm = () => {
  const [countries, setCountries] = useState(citiesList);
  const [errors, setErrors] = useState("");

  const [{ country, state, city }, handleInputChanges, handleFileChanges, reset] = useForm({
    country: "",
    state:"",
    city: "",
  });

  const handleRegister = (event) => {
    event.preventDefault();
    if (validation()) {
      const data = {    
            name: city, 
            country: country
        }
      cityService.cityRegister(data).then(async (result) => {
        if (result.status >= 200 && result.status < 300) {
          Swal.fire({
            title: "Registro exitoso",
            text: "La ciudad ha sido creada correctamente.",
            icon: "success",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#A61F69",
          });
        } else {
          Swal.fire({
            title: "Error",
            text: "No fue posible registrar la ciudad. Verifique que la ciudad no se encuentre registrada.",
            icon: "error",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#F2921D",
          });
        }
      });
      setErrors("");
    }
  };

  const validation = () => {
    if (city && country && state) {
      return true;
    } else {
      setErrors("Todos los campos son obligatorios");
      return false;
    }
  };

  const filterByCountry = (obj) => {
    if ("countryName" in obj && obj.countryName == country) {
      return true;
    } else {
      return false;
    }
  };

  const filterByState = (obj) => {
    if ("stateName" in obj && obj.stateName == state) {
      return true;
    } else {
      return false;
    }
  };

  return (
    <div className="wrapper">
      <form className="city-form">
        <div className="form-tittle">
          <h3 className="form-header">Registrar Nueva Ciudad</h3>
        </div>
        <div className="form-inputs">
          <label>
            <span>Pais</span>
            <select
              name="country"
              values={country}
              onChange={handleInputChanges}
              size="1"
            >
              <option value="">Seleccione un pais</option>
              {countries.map((co) => {
                return (
                  <option key={co.countryName} value={co.countryName}>
                    {co.countryName}
                  </option>
                );
              })}
            </select>
          </label>
          <label>
            <span>Provincia / Departamento</span>
            <select
              name="state"
              values={state}
              onChange={handleInputChanges}
              size="1"
            >
              <option value="">Seleccione un departamento</option>
              {countries.filter(filterByCountry)[0]?.states.map((st) => {
                return (
                  <option key={st.stateName} value={st.stateName}>
                    {st.stateName}
                  </option>
                );
              })}
            </select>
          </label>
          <label>
            <span>Ciudad</span>{" "}
            <select
              name="city"
              values={city}
              onChange={handleInputChanges}
              size="1"
            >
              <option value="" selected="selected">
                Seleccione una ciudad
              </option>
              {countries
                .filter(filterByCountry)[0]
                ?.states.filter(filterByState)[0]
                ?.cities.map((ct) => {
                  return (
                    <option key={ct} value={ct}>
                      {ct}
                    </option>
                  );
                })}
            </select>
          </label>
          <button className="btn" onClick={handleRegister}>
            REGISTRAR
          </button>
        </div>
      </form>
    </div>
  );
};

export default CityForm;
