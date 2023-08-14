import "./Loading.css";
import React from "react";

export const Loading = () => {
  return (
    <div className="load-container">
      <div className="loading">
        <div className="point"></div>
        <div className="point"></div>
        <div className="point"></div>
        <span className="load-text">Cargando...</span>
      </div>
    </div>
  );
};
