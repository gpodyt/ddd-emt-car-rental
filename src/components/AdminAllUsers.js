import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const AdminAllUsers = () => {
    const [users, setUsers] = useState([{
        username: "",
        name: "",
        surname: "",
        street: "",
        city: ""
    }]);

    const [error, setError] = useState();

    useEffect(() => {
        UserService.getAllUsers().then(
            (response) => {
                setUsers(response.data);
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
                    <h3>All Users</h3>
                </header>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Email</th>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Surname</th>
                            <th scope={"col"}>Street</th>
                            <th scope={"col"}>City</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.map(u => <tr>
                            <td>{u.username}</td>
                            <td>{u.name}</td>
                            <td>{u.surname}</td>
                            <td>{u.street}</td>
                            <td>{u.city}</td>
                        </tr>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
};

export default AdminAllUsers;