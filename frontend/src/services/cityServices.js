import { constants } from "./constants"
import { getData, postData, putData, deleteData } from "./utils"

export const cityRegister = (city) => {
    return postData(constants.CITIES_ENDPOINT, city)
}

export const cityAll = () => {
    return getData(constants.CITIES_ENDPOINT )
}

export const cityUpdate = (city) => {
    return putData(constants.CITIES_ENDPOINT+city.idCity, city)
}

export const cityDelete = (id) => {
    return deleteData(constants.CITIES_ENDPOINT + id)
}

export const cityFindByName = (name) => {
    return getData(constants.CITIES_ENDPOINT + name)
}