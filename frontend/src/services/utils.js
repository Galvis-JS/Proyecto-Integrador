import customParseFormat from 'dayjs/plugin/customParseFormat';
import dayjs from 'dayjs';

dayjs.extend(customParseFormat);

export const postDataWithToken = async (url, data) => {
    const response = await fetch(url, {
        method: 'POST',
        headers: buildHeadersJSON(),
        body: JSON.stringify(data),
    })
    return response;
}

export const postData = async (url, data) => {
    const response = await fetch(url, {
        method: 'POST',
        headers: buildHeadersJSON(),
        body: JSON.stringify(data)
    })
    return response
}

export const putData = async (url, data) => {
    const response = await fetch(url, {
        method: 'PUT',
        headers: buildHeadersJSON(),
        body: JSON.stringify(data)
    })
        
    return response
}
export const postDataWithFormData = async (url, data) => {
    const response = await fetch(url, {
        method: 'POST',
        headers: buildHeaders(),
        body: data,
    })
    return response
}

export const getData = async (url) => {
    try {
        const requestConfig = {
            method: 'GET',
        }

        const response = await fetch(url, requestConfig);
        return await response.json();
    }
    catch (error) {
        console.log(error);
        return null;
    }
}

export const getDataWithoutJson = async (url) => {
    const requestConfig = {
        method: 'GET',
    }

    const response = await fetch(url, requestConfig);
    return response;
}

export const getDataAuth = async (url) => {
    try {
        const requestConfig = {
            method: 'GET',
            headers: buildHeadersJSON(),
        }

        const response = await fetch(url, requestConfig);
        return await response.json();
    }
    catch (error) {
        console.log(error);
        return null;
    }
}

export const deleteData = async (url) => {
    const response = await fetch(url, {
        method: 'DELETE',
        headers: buildHeadersJSON(),
    })
    return response
}

const buildHeaders = () => {
    const headers = new Headers()
    const userData = JSON.parse(atob(localStorage.getItem('data')))
    const token =  userData.token
    //headers.append("Content-Type", "multipart/form-data");
    headers.append("Authorization", "Bearer "+ token);

    return headers
}

const buildHeadersJSON = () => {
    const headers = new Headers()
    const userData = JSON.parse(atob(localStorage.getItem('data')) ?? '{}')
    const token =  userData.token
    headers.append("Content-Type", "application/json");
    headers.append("Authorization", "Bearer "+ token);
    return headers
}

export const getDateFromString = (dateString) => {
    try {
        const date = dayjs(dateString, 'DD/MM/YYYY').toDate();
        if (date.toString() === 'Invalid Date') {
            console.error('Formato de fecha invalido', dateString);
            return null;
        }
        return date
    } catch (error) {
        console.error('Formato de fecha invalido', dateString);
        return null;
    }
    
}

export const getStringFromDate = (date) => {
    try {
        return dayjs(date).format('DD/MM/YYYY');
    } catch (error) {
        console.error('Formato de fecha invalido', date);
        return null;
    }
    
}
 export const mapDateStringFromDateRequest = (date) => {
    try {
        return dayjs(date).format("YYYY-MM-DDTHH:mm:ss")
    } catch (error) {
        console.error('Formato de fecha invalido', date);
        return null;
    }
 }