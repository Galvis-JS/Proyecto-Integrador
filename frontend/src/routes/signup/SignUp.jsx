import Swal from "sweetalert2";
import "./signup.css";
import React, { useContext, useState } from 'react';
import { useNavigate } from "react-router-dom";
import AppContext from "../../context/AppContext";
import { constants } from "../../services/constants";


export const SignUp = () => {

    const navigate = useNavigate();
    const [newUser, setNewUser] = useState({
        nombre: "",
        apellido: "",
        email: "",
        password: "",
        replyPassword: ""
    });
    const { login } = useContext(AppContext);

    const resetInputs = () => {
        setNewUser({
            nombre: "",
            apellido: "",
            email: "",
            password: "",
            replyPassword: ""
        })
    }

    const submitForm = (e) => {
        e.preventDefault();

        if (validation()) {

            fetch( constants.REGISTER_ENDPOINT , {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: newUser.nombre,
                    lastname: newUser.apellido,
                    email: newUser.email,
                    password: newUser.password
                })
            }).then(resp => {
                if (resp.ok) {
                    /*resp.json()
                        .then(userAuth => {
                            Swal.fire({
                                title: "Inicio de sesion exitoso!",
                                text: "Seras redirigido...",
                                icon: "success",
                                confirmButtonText: "Aceptar",
                                confirmButtonColor: "#F2921D",
                            }).then(() => {
                                login({
									nombre: userAuth.name,
									apellido: userAuth.lastname,
									email: userAuth.email,
								});
                                navigate("/")
                            });
                        })*/
                    Swal.fire({
                        title: "Registro exitoso",
                        text: "Por favor revisa tu correo y confirma el registro",
                        icon: "success",
                        confirmButtonText: "Aceptar",
                        confirmButtonColor: "#F2921D",
                    }).then(() => {
                        navigate('/')
                    })
                } else {
                    resp.text()
                        .then(errorMessage => {
                            Swal.fire({
                                title: "Error",
                                text: errorMessage,
                                icon: "error",
                                confirmButtonText: "Aceptar",
                                confirmButtonColor: "#F2921D",
                            })
                        })
                }
            })
        }
    }

    const validation = () => {

        if (newUser.nombre === "") {
            Swal.fire({
                title: "Error",
                text: "El nombre es obligatorio!",
                icon: "error",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#F2921D",
            })
            resetInputs();
            return false;
        }

        if (newUser.apellido === "") {
            Swal.fire({
                title: "Error",
                text: "El apellido es obligatorio!",
                icon: "error",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#F2921D",
            })
            resetInputs();
            return false;
        }

        if (newUser.email === "" || !newUser.email.includes("@")) {
            Swal.fire({
                title: "Error",
                text: "El email es invalido!",
                icon: "error",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#F2921D",
            })
            resetInputs();
            return false;
        }

        if (newUser.password.length < 8) {
            Swal.fire({
                title: "Error",
                text: "La contraseña debe tener al menos 8 caracteres!",
                icon: "error",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#F2921D",
            })
            resetInputs();
            return false;
        }

        if (newUser.password !== newUser.replyPassword) {
            Swal.fire({
                title: "Error",
                text: "Las contraseñas no coinciden!",
                icon: "error",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#F2921D",
            })
            resetInputs();
            return false;
        }

        return true;

    }


    return (

        <div className="signup-container" >

            <div className="signup">

                <form onSubmit={submitForm}>

                    <div className="signup-pt1" >
                        <h1>Crear cuenta</h1>
                        <input
                            className="input"
                            type="text"
                            placeholder="Ingrese su nombre"
                            id="nombre"
                            name="nombre"
                            value={newUser.nombre}
                            onChange={e => setNewUser({ ...newUser, nombre: e.target.value })} />
                        <input
                            className="input"
                            type="text"
                            placeholder="Ingrese su apellido"
                            id="apellido"
                            name="apellido"
                            value={newUser.apellido}
                            onChange={e => setNewUser({ ...newUser, apellido: e.target.value })} />
                        <input
                            className="input"
                            type="email"
                            placeholder="Ingrese su email..."
                            id="email"
                            name="email"
                            value={newUser.email}
                            onChange={e => setNewUser({ ...newUser, email: e.target.value })} />
                        <div className="password-container" >
                            <input
                                className="input"
                                type="password"
                                placeholder="Contraseña..."
                                id="password"
                                name="password"
                                value={newUser.password}
                                onChange={e => setNewUser({ ...newUser, password: e.target.value })} />
                            <p>La contraseña debe tener al menos 8 caracteres.</p>
                        </div>
                        <input
                            className="input"
                            type="password"
                            placeholder="Repita su contraseña..."
                            id="replyPassword"
                            name="replyPassword"
                            value={newUser.replyPassword}
                            onChange={e => setNewUser({ ...newUser, replyPassword: e.target.value })} />
                        <button type="submit" className="btn" >Registrarse</button>
                    </div>

                </form>

            </div>

        </div>

    )
}
