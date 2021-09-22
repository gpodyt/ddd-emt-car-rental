import React, {useState, useEffect, useCallback} from "react";
import UserService from "../services/user.service";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import { DateRangePicker} from "react-date-range";
import {isEmail} from "validator";
import AuthService from "../services/auth.service";
import { addDays } from 'date-fns';

const currentUserId = JSON.parse(localStorage.getItem("user"));

const AddRental = () => {
    const [date, setDate] = useState([
        {
            startDate: new Date(),
            endDate: addDays(new Date(), 7),
            key: 'selection'
        }
    ]);

    const [error, setError] = useState();

    const [vehicle, setVehicle] = useState({
        id:"",
        ownerId: "",
        manufacturer:"",
        model:"",
        mileage:"",
        registrationNumber:"",
        horsePower:"",
        vehicleState:""
    });

    useEffect(() => {
        UserService.getVehicle(window.location.href.substring(window.location.href.lastIndexOf('/')+1)).then(
            (response) => {
                setVehicle(response.data);
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

    const submitRental = () => {
        UserService.addNewRent({
            vehicleId: vehicle.id.id,
            customerId: currentUserId.id.id,
            fromDate: date[0].startDate,
            toDate: date[0].endDate
        }).then(() => {
            window.location.href = "/myrentals";
        })
    }

    if(error)
        return (<div className="container">
            <header className="jumbotron">
                <h3 className={"text-danger text-center"}>You have no access to this page!</h3>
            </header>
        </div>)
    else
        return (
            <div className="container">
                <header className="jumbotron mb-3">
                    <h3>Apply For Rental</h3>
                </header>
                <div className={"text-center"}>
                    <h4 className={'mb-3 fw-bold'}>Vehicle Details:</h4>
                    <p>
                        <strong>Manufacturer:</strong> {vehicle.manufacturer}
                    </p>
                    <p>
                        <strong>Model:</strong> {vehicle.model}
                    </p>
                    <p>
                        <strong>Mileage:</strong> {vehicle.mileage.kilometers}
                    </p>
                    <p>
                        <strong>Horsepower:</strong> {vehicle.horsePower}
                    </p>
                    <DateRangePicker
                        onChange={item => setDate([item.selection])}
                        showSelectionPreview={true}
                        moveRangeOnFirstSelection={false}
                        months={2}
                        ranges={date}
                        direction="horizontal"
                    />
                    <p>
                        <button className={"btn btn-lg btn-primary"} onClick={() => {submitRental()}}>Submit</button>
                    </p>
                </div>
            </div>
        );
};

export default AddRental;