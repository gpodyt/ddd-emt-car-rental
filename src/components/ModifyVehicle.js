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

const ModifyVehicle = () => {

    const [error, setError] = useState();

    const removeVehicle = (id) => {
        UserService.removeVehicle(id).then(() => {
            window.location.href = "/myvehicles";
            }
        )
    }

    const [manufacturers, setManufacturers] = useState([]);

    const [formData, setFormData] = useState({
        manufacturer:"",
        model: "",
        kilometers:"",
        rnCity:"",
        rnNumber:"",
        rnSuffix:"",
        horsePower:"",
        vehicleState:"",
        mileage:"",
        registrationNumber:"",
        id:""
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }


    useEffect(() => {
        UserService.getManufacturers().then(
            (response) => {
                setManufacturers(response.data);
            },
            (error) => {
                const _content =
                    (error.response && error.response.data) ||
                    error.message ||
                    error.toString();
                setError(_content);
            }
        );
        UserService.getVehicle(window.location.href.substring(window.location.href.lastIndexOf('/')+1)).then(
            (response) => {
                setFormData(response.data);
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

    const submitVehicle = (e) => {
        e.preventDefault();
        UserService.editVehicle({
            vehicleId: window.location.href.substring(window.location.href.lastIndexOf('/')+1),
            ownerId: currentUserId.id.id,
            kilometers: formData.kilometers==null?(formData.mileage.kilometers):(formData.kilometers),
            manufacturer: formData.manufacturer,
            model: formData.model,
            rnCity: formData.rnCity==null?(formData.registrationNumber.city):(formData.rnCity),
            rnNumber: formData.rnNumber==null?(formData.registrationNumber.number):(formData.rnNumber),
            rnSuffix: formData.rnSuffix==null?(formData.registrationNumber.suffix):(formData.rnSuffix),
            horsePower: formData.horsePower,
            vehicleState: formData.vehicleState
        }).then(() => {
            window.location.href = "/myvehicles";
        })
    }

    if(error || currentUserId===null)
        return (<div className="container">
            <header className="jumbotron">
                <h3 className={"text-danger text-center"}>You have no access to this page!</h3>
            </header>
        </div>)
    else
        return (
            <div className="container">
                <header className="jumbotron mb-3">
                    <h3>Edit your Vehicle</h3>
                </header>
                <div className={"text-center"}>
                    <form onSubmit={submitVehicle}>
                        <div className="form-group">
                            <label>Manufacturer</label>
                            <select name="manufacturer" className="form-control" onChange={handleChange} value={formData.manufacturer}>
                                {manufacturers.map((term) =>
                                    <option value={term}>{term}</option>
                                )}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="model">Model</label>
                            <input type="text"
                                   className="form-control"
                                   id="model"
                                   name="model"
                                   placeholder="ex. Astra 1.6L (without OPEL, select it above)"
                                   required
                                   onChange={handleChange}
                                   defaultValue={formData.model}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="kilometers">Mileage (in kilometers)</label>
                            <input type="number"
                                   className="form-control"
                                   id="kilometers"
                                   name="kilometers"
                                   required
                                   onChange={handleChange}
                                   defaultValue={formData.mileage.kilometers}
                            />
                        </div>
                        <div className="form-group">
                            <label>Registration Plate</label>
                            <div className={"row"}>
                                <div className={"col-2"}>
                                    <input type="text"
                                           className="form-control"
                                           id="rnCity"
                                           name="rnCity"
                                           placeholder="SK"
                                           required
                                           onChange={handleChange}
                                           minLength={2}
                                           maxLength={2}
                                           defaultValue={formData.registrationNumber.city}
                                    />
                                </div>
                                <div className={"col-8"}>
                                    <input type="text"
                                           className="form-control"
                                           id="rnNumber"
                                           name="rnNumber"
                                           placeholder="1234"
                                           required
                                           onChange={handleChange}
                                           minLength={4}
                                           maxLength={4}
                                           defaultValue={formData.registrationNumber.number}
                                    />
                                </div>
                                <div className={"col-2"}>
                                    <input type="text"
                                           className="form-control"
                                           id="rnSuffix"
                                           name="rnSuffix"
                                           placeholder="AB"
                                           required
                                           onChange={handleChange}
                                           minLength={2}
                                           maxLength={2}
                                           defaultValue={formData.registrationNumber.suffix}
                                    />
                                </div>
                            </div>
                            <div className="form-group">
                                <label htmlFor="kilometers">HorsePower</label>
                                <input type="number"
                                       className="form-control"
                                       id="horsePower"
                                       name="horsePower"
                                       required
                                       onChange={handleChange}
                                       defaultValue={formData.horsePower}
                                />
                            </div>
                        </div>
                        {formData.vehicleState!=="ON_RENT" &&
                        <div className="form-group">
                            <label>State</label>
                            <select name="vehicleState" className="form-control" onChange={handleChange} value={formData.vehicleState}>
                                <option value={"FREE"}>FREE</option>
                                <option value={"PRIVATE"}>PRIVATE</option>
                            </select>
                        </div>}
                        <button id="submit" type="submit" className="btn btn-primary w-100 mt-3 mb-3">Save vehicle</button>
                    </form>
                    <button className={"btn btn-danger w-100"} onClick={() => removeVehicle(formData.id.id)}>Remove vehicle!</button>
                </div>
            </div>
        );
};

export default ModifyVehicle;