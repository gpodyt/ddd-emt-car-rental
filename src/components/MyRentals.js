import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const userLoggedIn = localStorage.getItem("user") !== null;

const finishRental = (id) => {
    UserService.finishRent(id).then(() => {
        window.location.reload();
    })
}

const approveRental = (id) => {
    UserService.approveRent(id).then(() => {
        window.location.reload();
    })
}

const MyRentals = () => {
    const [myRentals, setMyRentals] = useState([{
        id:"",
        rentalOwnerId:"",
        vehicleId: "",
        customerId:"",
        rentalPeriod:"",
        rentalState:"",
        price:""
    }]);

    const [askedRentals, setAskedRentals] = useState([{
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
        UserService.getMyRentals().then(
            (response) => {
                setMyRentals(response.data);
            },
            (error) => {
                const _content =
                    (error.response && error.response.data) ||
                    error.message ||
                    error.toString();

                setError(_content);
            }
        );
        UserService.getAskedRentals().then(
            (response) => {
                setAskedRentals(response.data);
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
                    <h3>Vehicles Available for Rent</h3>
                </header>
                <h5>My Asked Rentals</h5>
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
                        {askedRentals.map(r => <tr>
                            <td>{r.rentalState==="WAITING_APPROVAL"?(<span className={'text-muted'}>Waiting Approval</span>):(<span><a href={"/vehicle/" + r.vehicleId.id}>{r.vehicleId.id}</a></span>)}</td>
                            <td>{r.rentalState==="WAITING_APPROVAL"?(<span className={'text-muted'}>Waiting Approval</span>):(<span><a href={"/user/" + r.customerId.id}>{r.customerId.id}</a></span>)}</td>
                            <td>{r.rentalPeriod.fromDate} to {r.rentalPeriod.toDate}</td>
                            <td>{r.rentalState}</td>
                            <td>{r.price.amount} {r.price.currency}</td>
                            {r.rentalState==="ONGOING" && <td><button className={'btn btn-sm btn-danger'} onClick={() => {finishRental(r.id.id)}}>Finish prematurely!</button></td>}
                        </tr>)}
                        </tbody>
                    </table>
                </div>
                <h5>My Rentals</h5>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Vehicle Id</th>
                            <th scope={"col"}>Customer Id</th>
                            <th scope={"col"}>Rental Period</th>
                            <th scope={"col"}>Rental State</th>
                            <th scope={"col"} colSpan={2}>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        {myRentals.map(r => <tr>
                            <td><a href={"/vehicle/" + r.vehicleId.id}>{r.vehicleId.id}</a></td>
                            <td><a href={"/user/" + r.customerId.id}>{r.customerId.id}</a></td>
                            <td>{r.rentalPeriod.fromDate} to {r.rentalPeriod.toDate}</td>
                            <td>{r.rentalState}</td>
                            <td>{r.price.amount} {r.price.currency}</td>
                            {r.rentalState==="WAITING_APPROVAL" && <td><button className={'btn btn-sm btn-primary'} onClick={() => {approveRental(r.id.id)}}>Approve</button></td>}
                            {r.rentalState==="ONGOING" && <td><button className={'btn btn-sm btn-danger'} onClick={() => {finishRental(r.id.id)}}>Finish prematurely!</button></td>}
                        </tr>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
};

export default MyRentals;