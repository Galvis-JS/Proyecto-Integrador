import { constants } from "./constants"
import { putData, getData, deleteData, postDataWithFormData, postData } from "./utils"

export const productRegister = (product) => {
    return postDataWithFormData(constants.PRODUCTS_ENDPOINT, product)
}

export const productDelete = (id) => {
    return deleteData(constants.PRODUCTS_ENDPOINT + id)
}

export const productAll = () => {
    return getData(constants.PRODUCTS_ENDPOINT)
}

export const productFindById = (id) => {
    return getData(constants.PRODUCTS_ENDPOINT + id)
}

export const productUpdate = (product) => {
    return putData(constants.PRODUCTS_ENDPOINT+product.idCoworking, product)
}