import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:9092/api/";


const getAllUsers = () => {
    return axios.get(API_URL + "admin/users", { headers: authHeader() });
};

const getUserById = (id) => {
    return axios.get(API_URL + "user/" + id, { headers: authHeader() });
}

const getAllRentals = () => {
    return axios.get(API_URL + "admin/rentals", { headers: authHeader() });
};

const getAllVehicles = () => {
    return axios.get(API_URL + "admin/vehicles", { headers: authHeader() });
};


const getPublicVehicles = () => {
    return axios.get(API_URL + "vehicles"); //that are free and public
};


const getMyVehicles = () => {
    return axios.get(API_URL + "myvehicles", { headers: authHeader() });
};

const getMyRentals = () => {
    return axios.get(API_URL + "myrentals", { headers: authHeader() });
};

const getAskedRentals = () => {
    return axios.get(API_URL + "askedrentals", { headers: authHeader() });
};

const getManufacturers = () => {
    return axios.get(API_URL + "manufacturers", { headers: authHeader() });
};

const addNewVehicle = (data) => {
    return axios.post(API_URL + "addvehicle", data, { headers: authHeader() });
};

const getVehicle = (id) => {
    return axios.get(API_URL + "getvehicle/" + id, { headers: authHeader() });
};

const editVehicle = (data) => {
    return axios.post(API_URL + "editvehicle", data, { headers: authHeader() });
};

const removeVehicle = (id) => {
    return axios.post(API_URL + "removevehicle", id, { headers: authHeader() });
};

const addNewRent = (data) => {
    return axios.post(API_URL + "addrent", data, { headers: authHeader() });
}

const approveRent = (id) => {
    return axios.post(API_URL + "approverent", id, { headers: authHeader() });
};

const getRent = (id) => {
    return axios.get(API_URL + "getrent/" + id, { headers: authHeader() });
};

const finishRent = (id) => {
    return axios.post(API_URL + "finishrent", id, { headers: authHeader() });
};

export default {
    getAllRentals,
    getAllUsers,
    getUserById,
    getAllVehicles,
    getPublicVehicles,
    getMyVehicles,
    getMyRentals,
    getAskedRentals,
    getManufacturers,
    addNewVehicle,
    getVehicle,
    editVehicle,
    removeVehicle,
    addNewRent,
    approveRent,
    getRent,
    finishRent
};