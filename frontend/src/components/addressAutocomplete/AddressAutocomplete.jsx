import React from "react";
import PlacesAutocomplete from "react-places-autocomplete";
import "./AddressAutocomplete.css";
import { geocodeByAddress, getLatLng } from "react-places-autocomplete";

const AddressAutocomplete = ({ address, handleInputChange, changeLatLng }) => {
  const searchOptions = {
    types: ["address"],
    componentRestrictions: { country: ["co", "ar"] },
  };

  const handleSelect = async (value) => {
    const results = await geocodeByAddress(value);
    const latlong = await getLatLng(results[0]);
    changeLatLng(latlong.lat, latlong.lng);

    handleInputChange({
      target: { value: value.split(",")[0], name: "address" },
    });
  };

  const onChange = (change) => {
    handleInputChange({ target: { value: change, name: "address" } });
  };

  return (
    <>
      <PlacesAutocomplete
        value={address}
        onChange={onChange}
        onSelect={handleSelect}
        searchOptions={searchOptions}
      >
        {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
          <div className="autocomplete">
            <input
              type="text"
              id="searchTextField"
              {...getInputProps({
                className: "location-search-input",
              })}
            />
            <div className="autocomplete-dropdown-container">
              {loading && <div className="loading">Cargando...</div>}
              {suggestions.map((suggestion) => {
                const className = suggestion.active
                  ? "suggestion-item--active"
                  : "suggestion-item";
                // inline style for demonstration purpose
                const style = { cursor: "pointer" };
                return (
                  <div
                    {...getSuggestionItemProps(suggestion, {
                      className,
                      style,
                    })}
                  >
                    <span className="suggestions">
                      {suggestion.description}
                    </span>
                  </div>
                );
              })}
            </div>
          </div>
        )}
      </PlacesAutocomplete>
    </>
  );
};

export default AddressAutocomplete;
