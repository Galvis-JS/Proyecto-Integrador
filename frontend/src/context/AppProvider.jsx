import React, { useState } from 'react'
import AppContext from './AppContext'

export const AppProvider = ({ children }) => {

    const [searchResults, setSearchResults] = useState([])
    const [showResults, setShowResults] = useState(false)
    const [selectedCity, setSelectedCity] = useState('')
    const [checkIn, setCheckIn] = useState('')
    const [checkOut, setCheckOut] = useState('')
    const [searchResultsLoading, setSearchResultsLoading] = useState(true)

    const [globalState, setGlobalState] = useState({
        id: "",
        nombre: "",
        apellido: "",
        authenticado: false,
        email: "",
        roles: [],
    });

    const [reservation, setReservation] = useState({});


    // Aca van todos los metodos para extraer y setear los valores de las propiedades del Global Context,
    // una vez definidas recuerden pasarlas al value del context provider

    const getNameGlobalState = () => {
        return globalState.nombre;
    }

    const getSurnameGlobalState = () => {
        return globalState.apellido;
    }

    const getEmailGlobalState = () => {
        return globalState.email;
    }

    const isAuthGlobalState = () => {
        return globalState.authenticado;
    }

    const login = ({ id, nombre, apellido, email, roles }) => {
        setGlobalState({ ...globalState, id, nombre, apellido, email, authenticado: true, roles });
    }

    const getRolesGlobalState = () => {
        return globalState.roles;
    }

    const signOf = () => {
        localStorage.clear()
        setGlobalState({
            nombre: "",
            apellido: "",
            authenticado: false,
            email: "",
            roles: "",
        })
    }

    return (
        <AppContext.Provider value={{
            getNameGlobalState,
            getSurnameGlobalState,
            getEmailGlobalState,
            isAuthGlobalState,
            login,
            getRolesGlobalState,
            signOf,
            searchResults,
            setSearchResults,
            showResults,
            setShowResults,
            selectedCity,
            setSelectedCity,
            checkIn,
            setCheckIn,
            checkOut,
            setCheckOut, 
            searchResultsLoading,
            setSearchResultsLoading,
            setReservation,
            reservation,
            globalState
            }}>
            {children}
        </AppContext.Provider>
    )

}

export default AppProvider;
