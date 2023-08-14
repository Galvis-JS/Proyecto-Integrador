import "./search.css";
import * as cityService from "../../services/cityServices";
import * as productService from "../../services/productServices";
import * as reserveService from "../../services/reserveServices";
import React, { useEffect, useState, useContext } from "react";
import { Calendar } from "primereact/calendar";
import { Dropdown } from "primereact/dropdown";
import { locale, addLocale } from "primereact/api";
import "primereact/resources/primereact.css";
import "primeicons/primeicons.css";
import "primeflex/primeflex.css";
import "primereact/resources/themes/saga-orange/theme.css";
import calendarESP from "../../data/calendar";
import AppContext from "../../context/AppContext";
import moment from "moment";

export const Search = () => {
  const [cities, setCities] = useState([]);
  const [dates, setDates] = useState(null);
  const [products, setProducts] = useState([]);
  const [available, setAvailable] = useState(null);
  let minDate = new Date();
  const { setSearchResults, setShowResults, selectedCity, setSelectedCity, setSearchResultsLoading } =
    useContext(AppContext);

  addLocale("es", calendarESP);
  useEffect(() => {
    cityService
      .cityAll()
      .then((response) => {
        return response;
      })
      .then((cities) => {
        setCities(cities);
      });

    productService.productAll().then((productList) => {
      setProducts(productList);
    });
  }, []);

  const filterProductsByCity = (product) => {
    return product.city?.name == selectedCity.name;
  };

  const handleSearch = () => {
    if (dates) {
      const startDate = moment(new Date(dates[0])).format(
        "YYYY-MM-DDTHH:mm:ss"
      );
      const endDate = moment(new Date(dates[1])).format("YYYY-MM-DDTHH:mm:ss");
      setAvailable(null)
      setShowResults(true);
      setSearchResultsLoading(true)
      reserveService
        .reserveFindByDates(startDate, endDate)
        .then((available) => {
          setAvailable(available);
        });
    } else if (selectedCity){
      setAvailable(null)
      setShowResults(true);
      setSearchResultsLoading(true)
      setSearchResults(products.filter(filterProductsByCity));
      setSearchResultsLoading(false)
    }
  };

  useEffect(() => {
    if(available !== null)
    {
      if(selectedCity){
        setSearchResults(available.filter(filterProductsByCity));  
        setSearchResultsLoading(false)
      } else {
        setSearchResults(available);  
        setSearchResultsLoading(false)       
      }
    }
  }, [available]);

  return (
    <div className="search">
      <h2>Busca ofertas en oficinas...</h2>
      <div className="search-container">
        <Dropdown
          value={selectedCity}
          onChange={(e) => setSelectedCity(e.value)}
          options={cities}
          optionLabel="name"
          placeholder="Seleccione una Ciudad"
          filter
          showClear
        />
        <Calendar
          placeholder="Seleccione fechas disponibles"
          id="calendar-picker"
          value={dates}
          onChange={(e) => setDates(e.value)}
          numberOfMonths={2}
          selectionMode="range"
          locale="es"
          dateFormat="dd/mm/yy"
          minDate={minDate}
        />
        <button className="btn" onClick={handleSearch}>
          Buscar
        </button>
      </div>
    </div>
  );
};
