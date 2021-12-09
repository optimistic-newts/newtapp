import '../App.css';
import {Link} from "react-router-dom";
import logo from "../images/logo128.png";
import React from "react";

// page not found. We'll steal some formatting from loginLayout ;)
export default function NotFound() {
    return (
        <>
            <main className="loginMain">
                <div className="loginSideDiv">
                    <div className="loginSideContent">
                        <h2>Sorry! We couldn't find that page.</h2>
                        <Link to="/"><img src={logo} alt="Logo" className="loginLogo"/></Link>
                    </div>
                </div>
            </main>
        </>
    );
}