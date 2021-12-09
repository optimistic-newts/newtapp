import React from 'react'
import {Link} from "react-router-dom";
import logo from "../../images/logo128.png";

const LoginLayout = ({ children }) => {
    return(
        <>
            <main className="loginMain">
                <div className="loginSideDiv">
                    <div className="loginSideContent">
                        <h2>Welcome to Newt!</h2>
                        <Link to="/"><img src={logo} alt="Logo" className="loginLogo"/></Link>
                        <p>Please <Link to="/login" className="loginLink">Log In</Link> or <Link to="/create/user" className="loginLink">Create a new account</Link></p>
                        <div className="loginSideChildren">
                            {children}
                        </div>
                    </div>
                </div>
            </main>
        </>
    )
}

export default LoginLayout