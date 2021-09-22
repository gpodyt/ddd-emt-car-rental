import React, {useState, useEffect} from "react";
import UserService from "../services/user.service";

const Vehicle = () => {
    const [vehicle, setVehicle] = useState({
        manufacturer:"",
        model:"",
        mileage:"",
        horsePower:"",
    });

    const [error, setError] = useState();

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

    if(error)
        return (<div className="container">
            <header className="jumbotron">
                <h3 className={"text-danger text-center"}>You have no access to this page!</h3>
            </header>
        </div>)
    else
        return (
            <div className="container">
                <header className="jumbotron">
                    <h3>
                        Vehicle Info
                    </h3>
                </header>
                <p>
                    <strong>Manufacturer:</strong> {vehicle.manufacturer}
                </p>
                <p>
                    <strong>Model:</strong> {vehicle.model}
                </p>
                <p>
                    <strong>Mileage:</strong> {vehicle.mileage.kilometers} km
                </p>
                <p>
                    <strong>Horsepower:</strong> {vehicle.horsePower} hp
                </p>
            </div>
        );
};

export default Vehicle;