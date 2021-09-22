import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const userLoggedIn = localStorage.getItem("user") !== null;
const currentUser = JSON.parse(localStorage.getItem("user"));

const addRentWithVehicleId = (vehicleId) => {
    window.location.href = "/addrental/" + vehicleId;
}

const Home = () => {
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
        UserService.getPublicVehicles().then(
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

    if(error)
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
                    <tr><td colSpan={4} className={"text-danger text-center"}>Server error</td></tr>
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
                            <th scope={"col"} colSpan={2}>Horse Power</th>
                        </tr>
                        </thead>
                        <tbody>
                        {content.map(v => <tr>
                            <td>{v.manufacturer}</td>
                            <td>{v.model}</td>
                            <td>{v.mileage.kilometers} km</td>
                            <td>{v.horsePower} hp</td>
                            {userLoggedIn&&currentUser.id.id!==v.ownerId.id&&(<td><button className={'btn btn-sm btn-primary'} onClick={() => {addRentWithVehicleId(v.id.id)}}>Rent</button></td>)}
                        </tr>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
};

export default Home;