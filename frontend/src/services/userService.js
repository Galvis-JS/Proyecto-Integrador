import { constants } from "./constants"
import { getData, putData} from "./utils"

export const authorizationUser = () => {
    const userData = JSON.parse(atob(localStorage.getItem('data')))
    return btoa(`${userData.email}:${userData.password}`)
}

export const userAll = () => {
    return getData(constants.USERS_ENDPOINT )
}

export const userUpdate = (user) => {
    return putData(constants.USERS_ENDPOINT+ user.id, user)
}