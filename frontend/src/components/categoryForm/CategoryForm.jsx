import React, { useEffect, useState } from "react";
import useForm from "../../hooks/useForm";
import * as categoryService from "../../services/categoryServices";
import Swal from "sweetalert2";
import "./CategoryForm.css";

const CategoryForm = () => {
  const [
    { name, description, image },
    handleInputChanges,
    handleFileChanges,
    reset,
  ] = useForm({
    name: "",
    description: "",
    image: "",
  });
  const [errors, setErrors] = useState("");

  const handleRegister = (event) => {
    event.preventDefault();
    if (validation()) {
      const data = new FormData();
      data.append("name", name);
      data.append("description", description);
      data.append("imageFile", image[0]);
      categoryService.categoryRegister(data).then(async (result) => {
        if (result.status >= 200 && result.status < 300) {
          Swal.fire({
            title: "Registro exitoso",
            text: "La categoría ha sido creada correctamente.",
            icon: "success",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#A61F69",
          });
        } else {
          Swal.fire({
            title: "Error",
            text: "No fue posible registrar la categoría.",
            icon: "error",
            confirmButtonText: "Aceptar",
            confirmButtonColor: "#F2921D",
          });
        }
      });
      setErrors("");
      //reset();
    }
  };

  const validation = () => {
    if (name && description && image) {
      return true;
    } else {
      setErrors("Todos los campos son obligatorios");
      return false;
    }
  };

  return (
    <div className="wrapper">
      <form className="category-form">
        <div className="form-tittle">
          <h3 className="form-header">Registrar Nueva Categoria</h3>
        </div>
        <div className="form-inputs">
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
            onChange={handleFileChanges}
          />
          <span style={{ color: "red" }}>{errors}</span>
          <button className="btn" onClick={handleRegister}>
            REGISTRAR
          </button>
        </div>
      </form>
    </div>
  );
};

export default CategoryForm;
