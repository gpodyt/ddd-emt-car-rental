import React, { useState, useEffect } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";
import AdminAllVehicles from "./components/AdminAllVehicles";
import AdminAllRentals from "./components/AdminAllRentals";
import AdminAllUsers from "./components/AdminAllUsers";
import AddRental from "./components/AddRental";
import MyRentals from "./components/MyRentals";
import MyVehicles from "./components/MyVehicles";
import User from "./components/User";
import Vehicle from "./components/Vehicle";
import AddVehicle from "./components/AddVehicle";
import ModifyVehicle from "./components/ModifyVehicle";


import EventBus from "./common/EventBus";


const App = () => {
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowAdminBoard(user.role.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/"} className="navbar-brand p-3 pt-0 pb-0">
            DDD Car Rentals
          </Link>

          {currentUser && (
              <ul className="navbar-nav mx-auto">
                <li className="nav-item">
                  <Link to={"/myvehicles"} className="nav-link">
                    My Vehicles
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to={"/myrentals"} className="nav-link">
                    My Rentals
                  </Link>
                </li>
              </ul>
          )}

          {showAdminBoard && (
              <ul className="navbar-nav mx-auto">
                <li className="nav-item">
                  <Link to={"/adminvehicles"} className="nav-link text-warning">
                    Vehicles
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to={"/adminrentals"} className="nav-link text-warning">
                    Rentals
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to={"/adminusers"} className="nav-link text-warning">
                    Users
                  </Link>
                </li>
              </ul>
          )}


          {currentUser ? (
              <ul className="navbar-nav ms-auto">
                {showAdminBoard ? (<li className="nav-item">
                  <Link to={"/profile"} className="nav-link text-warning">
                    My Profile
                  </Link>
                </li>) : (<li className="nav-item">
                  <Link to={"/profile"} className="nav-link">
                    My Profile
                  </Link>
                </li>)}
                <li className="nav-item">
                  <a href="/login" className="nav-link" onClick={logOut}>
                    LogOut
                  </a>
                </li>
              </ul>
          ) : (
              <ul className="navbar-nav ms-auto">
                <li className="nav-item">
                  <Link to={"/login"} className="nav-link">
                    Login
                  </Link>
                </li>

                <li className="nav-item">
                  <Link to={"/register"} className="nav-link">
                    Sign Up
                  </Link>
                </li>
              </ul>
          )}
        </nav>

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/home"]} component={Home} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/profile" component={Profile} />
            <Route path="/adminvehicles" component={AdminAllVehicles} />
            <Route path="/adminrentals" component={AdminAllRentals} />
            <Route path="/adminusers" component={AdminAllUsers} />
            <Route path="/addrental" component={AddRental} />
            <Route path="/addvehicle" component={AddVehicle} />
            <Route path="/myrentals" component={MyRentals} />
            <Route path="/myvehicles" component={MyVehicles} />
            <Route path="/modifyvehicle" component={ModifyVehicle} />
            <Route path="/vehicle" component={Vehicle} />
            <Route path="/user" component={User} />
          </Switch>
        </div>
      </div>
  );
};

export default App;