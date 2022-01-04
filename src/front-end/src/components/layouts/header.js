import {Link} from "react-router-dom";
import React from 'react';
import logo from '../../images/logo128.png';
import logo2 from '../../images/logo.png';
import {useCookies} from "react-cookie";
import authUtil from "../../auth";

export default function Header() {
    const cookies = useCookies(['Auth'])[0];
    const username = authUtil.getUsername(cookies.Auth);
    const profile = "/" + username;
    return (
        <>
        <nav>
            <span className="leftNavLinks">
            <Link to="/browse" className="navLink">Browse</Link>
            <Link to="/friends" className="navLink">Friends</Link>
            <Link to="/conversations" className="navLink">My Conversations</Link>
            </span>
            <Link to="/"><img src={logo} alt="Logo" className="navLogo"/></Link>
            <span className="rightNavLinks">
                <Link to="/conversations/create" className="navLink">Create</Link>
                <Link to={profile}><img src={logo2} alt="Logo" className="navUserPhoto"/></Link>
            </span>

        </nav>
        </>
    );
}