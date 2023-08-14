import { useState } from "react";

const useForm = (initialState = {}) => {
  const [values, setValues] = useState(initialState);

  const initValues = (initValues) => {
    setValues({
      ...values,
      ...initValues,
    });
  };

  const handleInputChanges = ({ target }) => {
    setValues({
      ...values,
      [target.name]: target.value,
    });
  };

  const handleFileChanges = ({ target }) => {
    let file = target.files;
    //let reader = new FileReader();
    //let url = reader.readAsDataURL(file);

    /*reader.onloadend = function (e) {
            setValues({
                ...values,
                [target.name]: file
    
            })
                //console.log(reader.result)
          }*/

    setValues({
      ...values,
      [target.name]: file,
    });
  };

  const reset = () => {
    setValues(initialState);
  };

  return [values, handleInputChanges, handleFileChanges, reset, initValues];
};

export default useForm;
