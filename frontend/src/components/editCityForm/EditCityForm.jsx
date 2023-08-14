import React, { useEffect, useState } from "react";
import useForm from "../../hooks/useForm";
import * as cityService from "../../services/cityServices";
import Swal from "sweetalert2";
import "./EditCityForm.css";
import citiesList from "../../data/cities";
import { useParams } from "react-router-dom";

const EditCityForm = () => {
  const [countries, setCountries] = useState(citiesList);
  const [id, setId] = useState("");
  const [{ country, state, city}, handleInputChanges, handleFileChanges, reset, initValues] = useForm({
    country: "",
    state:"",
    city: "",
  });

  const [errors, setErrors] = useState("");
  const {name} = useParams()
  
  useEffect(() => {
      cityService.cityFindByName(name)
      .then(result => {
        return result
      })
      .then(city => {
        initValues({country: city.country, city: city.name})
        setId(city.idCity)
        findStateByCountryAndCity(city)
      })
  }, []);

  const handleUpdate= (event) => {
    event.preventDefault();
    if (validation()) {
      const newCity = {country:country, name:city, idCity:id}
      cityService.cityUpdate(newCity).then(async (result) => {
        if (result.status >= 200 && result.status < 300) {
          Swal.fire({
            title: "Actualizacion Exitosa",
            text: "La ciudad ha sido actualizada correctamente.",
            icon: "success",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#A61F69",
          });
        } else {
          Swal.fire({
            title: "Error",
            text: "No fue posible modificar la ciudad.",
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

  const findStateByCountryAndCity = (city) => {
    const stateSelected = countries.filter(count => {
      if("countryName" in count && count.countryName == city.country) {
        return true
      } else {
        return false
      }
    })[0]?.states?.filter(cit => {
      if('cities' in cit && cit.cities.includes(city.name)) {
        return true
      } else {
        return false
      }
    })[0]?.stateName
    initValues({country: city.country, city: city.name, state: stateSelected})
  }

  return (
    <div className="wrapper">
      <form className="city-form">
        <div className="form-tittle">
          <h3 className="form-header">Editar Ciudad</h3>
        </div>
        <div className="form-inputs">
          <label>
            <span>Pais</span>
            <select
              name="country"
              value={country}
              onChange={handleInputChanges}
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
              value={state}
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
              value={city}
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
          <button className="btn" onClick={handleUpdate}>
            GUARDAR
          </button>
        </div>
      </form>
    </div>
  );
};

export default EditCityForm;