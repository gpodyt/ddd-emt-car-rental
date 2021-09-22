import axios from "axios";

const API_URL = "http://localhost:9092/api/auth/";

const register = (name, surname, username, password, street, city) => {
    return axios.post(API_URL + "signup", {
        name,
        surname,
        username,
        password,
        street,
        city
    });
};

const login = async (username, password) => {
    let response = await signInResponseAndSetJWToken(username, password);
    let user = await getCurrentUserFromServer();
    localStorage.setItem("user", JSON.stringify(user.data));
    return response;
};

const logout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("JWT");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const signInResponseAndSetJWToken = (username, password) => {
    return axios
        .post(API_URL + "signin", {
            username,
            password,
        })
        .then((response) => {
            if (response.headers.authorization) {
                localStorage.setItem("JWT", response.headers.authorization);
            }
            return response.data;
        });
}

const getCurrentUserFromServer = () => {
    if(localStorage.getItem("JWT")===null)
        return axios.get(API_URL + "currentuser").then((response) => {
            return response;
        });
    else
        return axios.get(API_URL + "currentuser", {
            headers: {
                'Authorization': localStorage.getItem("JWT")
            }
        }).then((response) => {
            return response;
        });
};

export default {
    register,
    login,
    logout,
    getCurrentUser
};