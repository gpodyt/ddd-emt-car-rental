import React, {useState, useEffect} from "react";
import UserService from "../services/user.service";

const User = () => {
    const [user, setUser] = useState({
        username: "",
        name: "",
        surname: "",
        street: "",
        city: ""
    });

    const [error, setError] = useState();

    useEffect(() => {
        UserService.getUserById(window.location.href.substring(window.location.href.lastIndexOf('/')+1)).then(
            (response) => {
                setUser(response.data);
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
                    <strong>{user.username}</strong> Profile
                </h3>
            </header>
            <p>
                <strong>Name:</strong> {user.name}
            </p>
            <p>
                <strong>Surname:</strong> {user.surname}
            </p>
            <p>
                <strong>Street:</strong> {user.street}
            </p>
            <p>
                <strong>City:</strong> {user.city}
            </p>
        </div>
    );
};

export default User;