import React from "react";
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import "leaflet/dist/leaflet.css";


const Map = ({ lat, lng, address }) => {

  let DefaultIcon = L.icon({
    iconUrl: icon,
    shadowUrl: iconShadow,
    iconAnchor: [12, 41] // necesario para posicionar correctamente el Icono
  });

  L.Marker.prototype.options.icon = DefaultIcon;
  
  const location = { lat, lng }


  return (
    <MapContainer center={location} zoom={13} scrollWheelZoom={false} style={{ height: "500px", width: "100%" }}>
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <Marker position={location}>
        <Popup>
          {address}
        </Popup>
      </Marker>
    </MapContainer>
  )
}

export default Map;
