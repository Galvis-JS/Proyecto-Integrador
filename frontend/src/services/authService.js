import { constants } from "./constants"
import { getDataWithoutJson } from "./utils"

export const confirmAccount = (code) => {
    return getDataWithoutJson(constants.CONFIRM_REGISTER_ENDPOINT+'?code='+code)
}
