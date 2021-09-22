import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const userLoggedIn = localStorage.getItem("user") !== null;

const finishRental = (id) => {
    UserService.finishRent(id).then(() => {
        window.location.reload();
    })
}

const AdminAllRentals = () => {
    const [allRentals, setAllRentals] = useState([{
        id:"",
        rentalOwnerId:"",
        vehicleId: "",
        customerId:"",
        rentalPeriod:"",
        rentalState:"",
        price:""
    }]);

    const [error, setError] = useState();

    useEffect(() => {
        UserService.getAllRentals().then(
            (response) => {
                setAllRentals(response.data);
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

    if(error || !userLoggedIn)
        return (<div className="container">
            <header className="jumbotron">
                <h3 className={"text-danger text-center"}>You have no access to this page!</h3>
            </header>
        </div>)
    else
        return (
            <div className="container">
                <header className="jumbotron">
                    <h3>All Rentals</h3>
                </header>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Vehicle Id</th>
                            <th scope={"col"}>Owner Id</th>
                            <th scope={"col"}>Rental Period</th>
                            <th scope={"col"}>Rental State</th>
                            <th scope={"col"} colSpan={2}>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        {allRentals.map(r => <tr>
                            <td><a href={"/vehicle/" + r.vehicleId.id}>{r.vehicleId.id}</a></td>
                            <td><a href={"/user/" + r.customerId.id}>{r.customerId.id}</a></td>
                            <td>{r.rentalPeriod.fromDate} to {r.rentalPeriod.toDate}</td>
                            <td>{r.rentalState}</td>
                            <td>{r.price.amount} {r.price.currency}</td>
                            {r.rentalState!=="FINISHED"&&<td><button className={'btn btn-sm btn-danger'} onClick={() => {finishRental(r.id.id)}}>Finish prematurely!</button></td>}
                        </tr>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
};

export default AdminAllRentals;