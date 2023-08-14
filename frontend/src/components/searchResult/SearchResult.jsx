import React, { useEffect, useState, useContext } from "react";
import { CoworkingCard } from "../coworkingCard/CoworkingCard";
import { Loading } from "../loading/Loading"
import AppContext from "../../context/AppContext";
import "./SearchResult.css";

export const SearchResult = () => {
  const { searchResults, showResults, selectedCity, searchResultsLoading } =
    useContext(AppContext);

  if (!showResults) {
    return <></>;
  }

  if (searchResultsLoading) {
    return (
      <div className="coworking-recommendation">
        <h2>Resultados</h2>
        <Loading/>
      </div>
    );
  }

  if (
    showResults &&
    selectedCity &&
    searchResults.length === 0 &&
    !searchResultsLoading
  ) {
    return (
      <h3 className="no-results">
        No se han encontrado resultados para su búsqueda.
      </h3>
    );
  }

  return (
    <div className="coworking-recommendation">
      <h2>Resultados</h2>
      {searchResults.length > 0 && (
        <div className="coworking-recommendation-container">
          {searchResults.map((item) => (
            <CoworkingCard product={{ ...item }} key={item.idCoworking} />
          ))}
        </div>
      )}
    </div>
  );
};
