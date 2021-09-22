import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const AdminAllVehicles = () => {
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

    const removeVehicle = (id) => {
        UserService.removeVehicle(id).then(() => {
            window.location.reload();
        }
        )
    }

    useEffect(() => {
        UserService.getAllVehicles().then(
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
                <h3>All Vehicles</h3>
            </header>
            <div className={"table-responsive"}>
                <table className={"table table-striped"}>
                    <thead>
                    <tr>
                        <th scope={"col"}>Owner</th>
                        <th scope={"col"}>Manufacturer</th>
                        <th scope={"col"}>Model</th>
                        <th scope={"col"}>Mileage</th>
                        <th scope={"col"}>Registration Number</th>
                        <th scope={"col"}>Horse Power</th>
                        <th scope={"col"}>Vehicle State</th>
                        <th scope={"col"}></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr><td colSpan={8} className={"text-danger text-center"}>You have no access to this page!</td></tr>
                    </tbody>
                </table>
            </div>
        </div>)
    else
        return (

            <div className="container">
                <header className="jumbotron">
                    <h3>All Vehicles</h3>
                </header>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Owner</th>
                            <th scope={"col"}>Manufacturer</th>
                            <th scope={"col"}>Model</th>
                            <th scope={"col"}>Mileage</th>
                            <th scope={"col"}>Registration Number</th>
                            <th scope={"col"}>Horse Power</th>
                            <th scope={"col"} colSpan={2}>Vehicle State</th>
                        </tr>
                        </thead>
                        <tbody>
                        {content.map(v => <tr>
                            <td><a href={'/user/' + v.ownerId.id}>{v.ownerId.id}</a></td>
                            <td>{v.manufacturer}</td>
                            <td>{v.model}</td>
                            <td>{v.mileage.kilometers} km</td>
                            <td>{v.registrationNumber.city}-{v.registrationNumber.number}-{v.registrationNumber.suffix}</td>
                            <td>{v.horsePower} hp</td>
                            <td>{v.vehicleState}</td>
                            <td>{v.vehicleState === 'FREE'?(<button className={"btn btn-danger"} onClick={() => removeVehicle(v.id.id)}>Remove</button>):(<button className={"btn btn-danger disabled"}>Remove</button>)}</td>
                        </tr>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
};

export default AdminAllVehicles;