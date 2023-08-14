import { constants } from "./constants"
import { getData, postData } from "./utils"

export const facilityRegister = (facility) => {
    return postData(constants.FACILITIES_ENDPOINT, facility)
}

export const facilityAll = () => {
    return getData(constants.FACILITIES_ENDPOINT )
}

export const facilityUpdate = (facility) => {
    return putData(constants.FACILITIES_ENDPOINT+facility.idfacility, facility)
}

export const facilityDelete = (id) => {
    return deleteData(constants.FACILITIES_ENDPOINT + id)
}

export const facilityFindById = (id) => {
    return getData(constants.FACILITIES_ENDPOINT + id)
}