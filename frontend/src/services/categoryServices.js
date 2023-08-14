import { constants } from "./constants"
import { getData, postData, postDataWithFormData, deleteData } from "./utils"

export const categoryRegister = (category) => {
    return postDataWithFormData(constants.CATEGORIES_ENDPOINT, category)
}

export const categoryAll = () => {
    return getData(constants.CATEGORIES_ENDPOINT )
}

export const categoryUpdate = (category) => {
    return putData(constants.CATEGORIES_ENDPOINT+category.idcategory, category)
}

export const categoryDelete = (id) => {
    return deleteData(constants.CATEGORIES_ENDPOINT + id)
}

export const categoryFindByName = (name) => {
    return getData(constants.CATEGORIES_ENDPOINT + name)
}