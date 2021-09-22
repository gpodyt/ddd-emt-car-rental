import React from "react";
import AuthService from "../services/auth.service";

const Profile = () => {
    const currentUser = AuthService.getCurrentUser();

    return (
        <div className="container">
            <header className="jumbotron">
                <h3>
                    <strong>{currentUser.username}</strong> Profile
                </h3>
            </header>
            <p>
                <strong>Name:</strong> {currentUser.name}
            </p>
            <p>
                <strong>Surname:</strong> {currentUser.surname}
            </p>
            <p>
                <strong>Street:</strong> {currentUser.street}
            </p>
            <p>
                <strong>City:</strong> {currentUser.city}
            </p>
            <p>

                <strong>Role:</strong> {currentUser.role.includes('ADMIN') ? (<span className={'text-warning bg-dark'}>{currentUser.role}</span>) : (currentUser.role)}
            </p>
        </div>
    );
};

export default Profile;