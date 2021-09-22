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

const AddVehicle = () => {

    const [error, setError] = useState();

    const [manufacturers, setManufacturers] = useState([]);

    const [formData, setFormData] = useState({
        manufacturer:"",
        model: "",
        kilometers:"",
        rnCity:"",
        rnNumber:"",
        rnSuffix:"",
        horsePower:"",
        vehicleState:""
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
    }, []);

    const submitVehicle = (e) => {
        e.preventDefault();
        UserService.addNewVehicle({
            vehicleId: "",
            ownerId: currentUserId.id.id,
            kilometers: formData.kilometers,
            manufacturer: formData.manufacturer,
            model: formData.model,
            rnCity: formData.rnCity,
            rnNumber: formData.rnNumber,
            rnSuffix: formData.rnSuffix,
            horsePower: formData.horsePower
        }).then(() => {
            window.location.href = "/myvehicles";
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
                    <h3>Add your Vehicle</h3>
                </header>
                <div className={"text-center"}>
                    <form onSubmit={submitVehicle}>
                        <div className="form-group">
                            <label>Manufacturer</label>
                            <select name="manufacturer" className="form-control" onChange={handleChange}>
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
                                          maxLength={2}/>
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
                                />
                            </div>
                        </div>
                        <button id="submit" type="submit" className="btn btn-primary">Add vehicle</button>
                    </form>

                </div>
            </div>
        );
};

export default AddVehicle;