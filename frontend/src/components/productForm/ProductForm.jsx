import React, { useContext, useEffect, useState } from "react";
import useForm from "../../hooks/useForm";
import AddressAutocomplete from "../addressAutocomplete/AddressAutocomplete";
import * as productService from "../../services/productServices";
import * as categoryService from "../../services/categoryServices";
import * as cityService from "../../services/cityServices";
import * as facilityService from "../../services/facilityServices";
import Swal from "sweetalert2";
import "./ProductForm.css";

const ProductForm = () => {
  const [
    {
      name,
      category,
      city,
      address,
      description,
      image,
      cancelation,
      rules,
      healthpolicy,
      facility,
    },
    handleInputChanges,
    handleFileChanges,
    reset,
  ] = useForm({
    name: "",
    category: "",
    city: "",
    address: "",
    description: "",
    image: "",
    cancelation: "",
    rules: "",
    healthpolicy: "",
    facility: "",
  });

  const [errors, setErrors] = useState("");
  const [categories, setCategories] = useState([]);
  const [cities, setCities] = useState([]);
  const [facilities, setFacilities] = useState([]);
  const [{ lat, lng }, setCoord] = useState({ lat: "", lng: "" });
  const [checkedItems, setCheckedItems] = useState(new Set());

  useEffect(() => {
    categoryService
      .categoryAll()
      .then((response) => {
        return response;
      })
      .then((categories) => {
        setCategories(categories);
      });
    cityService
      .cityAll()
      .then((response) => {
        return response;
      })
      .then((cities) => {
        setCities(cities);
      });
    facilityService
      .facilityAll()
      .then((response) => {
        return response;
      })
      .then((facilities) => {
        setFacilities(facilities);
      });
  }, []);

  const changeLatLng = (x, y) => {
    setCoord({ lat: x, lng: y });
  };

  const onChangeLatLng = ({ target }) => {
    target.name == "lat"
      ? setCoord({
          lng,
          lat: target.value,
        })
      : setCoord({
          lng: target.value,
          lat,
        });
  };

  const handleCheckboxChange = (id) => {
    const newSelectedItems = new Set(checkedItems);
    if (!newSelectedItems.has(id)) {
      newSelectedItems.add(id);
    } else {
      newSelectedItems.delete(id);
    }
    setCheckedItems(newSelectedItems);
  };

  const handleRegister = (event) => {
    event.preventDefault();
    if (validation()) {
      const data = new FormData();
      data.append("name", name);
      data.append("description", description);
      data.append("category", category);
      data.append("city", city);
      data.append("address", address);
      data.append("rating", 0);
      data.append("latitude", lat);
      data.append("longitude", lng);
      data.append("cancellation_policy", cancelation);
      data.append("coworking_rules_policy", rules);
      data.append("health_safety_policy", healthpolicy);
      data.append("facilities", Array.from(checkedItems).join(","));
      for (let i = 1; i <= image.length; i++) {
        data.append(`imageFile${i}`, image[i - 1]);
      }
      productService.productRegister(data).then(async (result) => {
        const response = await result.text();

        if (result.status == 404) {
          Swal.fire({
            title: "Aviso",
            text: "Ya existe una oficina con este nombre.",
            icon: "warning",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#A61F69",
          });
        } else {
          if (result.status >= 200 && result.status < 300) {
            Swal.fire({
              title: "Registro exitoso",
              text: "La oficina ha sido agregada correctamente.",
              icon: "success",
              confirmButtonText: "Aceptar",
              confirmButtonColor: "#A61F69",
            });
          } else {
            Swal.fire({
              title: "Error",
              text: "No fue posible registrar la oficina.",
              icon: "error",
              confirmButtonText: "Aceptar",
              confirmButtonColor: "#F2921D",
            });
          }
        }
      });
      setErrors("");
      reset();
      setCoord({ lat: "", lng: "" });
    }
  };

  const validation = () => {
    if (name && address && description && image) {
      if (image.length == 5) {
        return true;
      }
      setErrors("Debe subir 5 imagenes");
      return false;
    } else {
      setErrors("Todos los campos son obligatorios");
      return false;
    }
  };

  return (
    <div className="wrapper">
      <form className="product-form">
        <div className="form-tittle">
          <h3 className="form-header">Registrar Nueva Oficina</h3>
        </div>
        <div className="form-inputs-products">
          <div className="panels">
            <div className="left-panel">
              <h4>Datos Generales</h4>
              <label>
                <span>Nombre</span>
                <input
                  name="name"
                  type="text"
                  value={name || ""}
                  onChange={handleInputChanges}
                />
              </label>
              <label>
                <span>Categoría</span>
                <select
                  name="category"
                  value={category}
                  onChange={handleInputChanges}
                >
                  <option value="">Seleccione una categoria</option>
                  {categories.map((cat) => {
                    return (
                      <option key={cat.idCategory} value={cat.idCategory}>
                        {cat.name}
                      </option>
                    );
                  })}
                </select>
              </label>
              <label>
                <span>Ciudad</span>
                <select name="city" value={city} onChange={handleInputChanges}>
                  <option value="">Seleccione una ciudad</option>
                  {cities.map((cit) => {
                    return (
                      <option key={cit.idCity} value={cit.idCity}>
                        {cit.name}
                      </option>
                    );
                  })}
                </select>
              </label>
              <label>
                <span>Dirección</span>
                <AddressAutocomplete
                  address={address}
                  handleInputChange={handleInputChanges}
                  changeLatLng={changeLatLng}
                />
              </label>
              <label>
                <span>Latitud</span>
                <input
                  name="lat"
                  type="text"
                  value={lat}
                  onChange={onChangeLatLng}
                />
              </label>
              <label>
                <span>Longitud</span>
                <input
                  name="lng"
                  type="text"
                  value={lng}
                  onChange={onChangeLatLng}
                />
              </label>
              <label>
                <span>Descripción</span>
                <textarea
                  name="description"
                  value={description || ""}
                  onChange={handleInputChanges}
                />
              </label>
              <input
                name="image"
                type="file"
                accept="image/*"
                multiple="multiple"
                onChange={handleFileChanges}
              />
            </div>
            <div className="right-panel">
              <h4>Políticas</h4>

              <label>
                <span>Políticas de Cancelación</span>
                <textarea
                  name="cancelation"
                  value={cancelation || ""}
                  onChange={handleInputChanges}
                />
              </label>
              <label>
                <span>Normas del Coworking</span>
                <textarea
                  name="rules"
                  value={rules || ""}
                  onChange={handleInputChanges}
                />
              </label>
              <label>
                <span>Salud y Seguridad</span>
                <textarea
                  name="healthpolicy"
                  value={healthpolicy || ""}
                  onChange={handleInputChanges}
                />
              </label>
              <h4 className="facilities-tittle">Comodidades</h4>
              <div className="facilities">
                {facilities.map((facility) => {
                  return (
                    <>
                      <span className="facility-text" key={facility.name}>
                        <input
                          key={facility.name}
                          checked={checkedItems.has(facility.id)}
                          onChange={() => {
                            handleCheckboxChange(facility.id);
                          }}
                          type="checkbox"
                        />
                        {facility.name}
                      </span>
                    </>
                  );
                })}
              </div>
            </div>
          </div>
          <span style={{ color: "red" }}>{errors}</span>
          <button className="btn" onClick={handleRegister}>
            REGISTRAR
          </button>
        </div>
      </form>
    </div>
  );
};

export default ProductForm;
