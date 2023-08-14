import React, { useEffect, useState, useMemo } from "react";
import { CardProduct } from "../cardProduct/cardProduct";
import * as userService from "../../services/userService";
import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";
import { Icons } from "../icons/Icons";
import { Link } from "react-router-dom";
import { NavLink } from "react-router-dom";
import { Loading } from "../loading/Loading";
import Swal from "sweetalert2";
import { Dropdown } from "primereact/dropdown";
//import "primereact/resources/themes/soho-light/theme.css";
import "./Users.css";

const Users = () => {
  const [users, setUsers] = useState([]);
  const [role, setRole] = useState([]);

  useEffect(() => {
    userService
      .userAll()
      .then((response) => {
        return response;
      })
      .then((user) => {
        setUsers(user);
      });
  }, []);

  const confirmation = (data, actual) => {
    Swal.fire({
      title: "¿Esta seguro que desea cambiar el rol del usuario?",
      showDenyButton: true,
      confirmButtonText: "Si",
      denyButtonText: "Cancelar",
      confirmButtonColor: "#F2921D",
      denyButtonColor: "#A61F69",
    }).then((result) => {
      if (result.isConfirmed) {
        handleUpdate(data, actual.name);
      }
    });
  };

  const handleUpdate = (data, change) => {
    let newRoles = [];
    if (change == "Usuario") {
      newRoles = [{ id: 2, name: "ROLE_USER" }];
    } else {
      newRoles = [{ id: 1, name: "ROLE_ADMIN" }];
    }

    const newUser = {
      id: data.id,
      name: data.name,
      lastname: data.lastname,
      email: data.email,
      password: data.password,
      roles: newRoles,
    };
    userService.userUpdate(newUser).then(async (result) => {
      if (result.status >= 200 && result.status < 300) {
        Swal.fire({
          title: "Actualizado exitosamente",
          text: "El rol del usuario ha sido actualizado exitosamente.",
          icon: "success",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#A61F69",
        });

        const newUsers = users.map(function (us) {
          if (us.id == data.id) {
            us.roles = newRoles;
          }
          return us;
        });

        setUsers(newUsers);
      } else {
        Swal.fire({
          title: "Error",
          text: "No fue posible actualizar el rol del usuario.",
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

  const calcRoles = (roles) => {
    if (roles.find(({ name }) => name === "ROLE_ADMIN") != undefined) {
      return "Administrador";
    } else {
      return "Usuario";
    }
  };

  const roles = (data, props) => {
    if (data) {
      const roles = [{ name: "Administrador" }, { name: "Usuario" }];

      return (
        <Dropdown
          value={data}
          onChange={(e) => confirmation(data, e.value)}
          options={roles}
          optionLabel="name"
          placeholder={calcRoles(data.roles)}
          className="w-full md:w-14rem"
        />
      );

      //setRole(role)
      //return(<Dropdown value={data} onChange={(e) => confirmation(data)} options={roles} optionLabel="name"
      // placeholder={calcRoles(data.roles)} className="w-full md:w-14rem" />)
    }
  };

  return (
    <div className="cities-container">
      {users.length >= 1 ? (
        <DataTable
          value={users}
          stripedRows
          paginator
          rows={8}
          tableStyle={{ minWidth: "50rem" }}
        >
          <Column
            field="name"
            header="Nombre"
            sortable
            style={{ width: "25%" }}
          ></Column>
          <Column
            field="lastname"
            header="Apellido"
            sortable
            style={{ width: "25%" }}
          ></Column>
          <Column
            field="email"
            header="Email"
            sortable
            style={{ width: "25%" }}
          ></Column>
          <Column
            field="id"
            header="Rol"
            body={roles}
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

export default Users;
