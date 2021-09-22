import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const userLoggedIn = localStorage.getItem("user") !== null;
const currentUser = JSON.parse(localStorage.getItem("user"));

const addRentWithVehicleId = (vehicleId) => {
    window.location.href = "/addrental/" + vehicleId;
}

const addNewVehicle = () => {
    window.location.href = "/addvehicle";
}

const modifyVehicle = (id) => {
    window.location.href = "/modifyvehicle/" + id;
}

const MyVehicles = () => {
    const [content, setContent] = useState([{
        id:"",
        ownerId: "",
        manufacturer:"",
        model:"",
        mileage:"",
        registrationNumber:"",
        horsePower:"",
        vehicleState:"",
    }]);

    const [error, setError] = useState();

    useEffect(() => {
        UserService.getMyVehicles().then(
            (response) => {
                setContent(response.data);
            },
            (error) => {
                const _content =
                    (error.response && error.response.data) ||
                    error.message ||
                    error.toString();

                setError(_content);
            }
        );
    }, []);

    if(error || currentUser===null)
        return (<div className="container">
            <header className="jumbotron">
                <h3>Users</h3>
            </header>
            <div className={"table-responsive"}>
                <table className={"table table-striped"}>
                    <thead>
                    <tr>
                        <th scope={"col"}>Manufacturer</th>
                        <th scope={"col"}>Model</th>
                        <th scope={"col"}>Mileage</th>
                        <th scope={"col"}>Horse Power</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr><td colSpan={4} className={"text-danger text-center"}>You have no access to this page!</td></tr>
                    </tbody>
                </table>
            </div>
        </div>)
    else
        return (

            <div className="container">
                <header className="jumbotron">
                    <h3>Vehicles Available for Rent</h3>
                </header>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Manufacturer</th>
                            <th scope={"col"}>Model</th>
                            <th scope={"col"}>Mileage</th>
                            <th scope={"col"}>Horse Power</th>
                            <th scope={"col"}>Registration Number</th>
                            <th scope={"col"} colSpan={2}>State</th>
                        </tr>
                        </thead>
                        <tbody>
                        {content.map(v => <tr>
                            <td>{v.manufacturer}</td>
                            <td>{v.model}</td>
                            <td>{v.mileage.kilometers} km</td>
                            <td>{v.horsePower} hp</td>
                            <td>{v.registrationNumber.city}-{v.registrationNumber.number}-{v.registrationNumber.suffix}</td>
                            <td>{v.vehicleState}</td>
                            <td>{v.vehicleState==="ON_RENT"?(
                                <button className={"btn btn-warning disabled"}>Modify</button>
                            ):(
                                <button className={"btn btn-warning"} onClick={() => modifyVehicle(v.id.id)}>Modify</button>
                            )}</td>
                             </tr>)}
                        <tr><td colSpan={7}><button className={"btn btn-primary w-100"} onClick={addNewVehicle}>Add new Vehicle</button></td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
};

export default MyVehicles;