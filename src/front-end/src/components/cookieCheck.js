import '../App.css';
import React from 'react';
import {useCookies} from "react-cookie";
import {Navigate} from "react-router-dom";
import authUtil from "../auth";

// quick check for Auth cookie. If no cookie or expired we can quickly redirect to
// log in before doing anything else.
export default function CookieCheck() {
    const [cookies, setCookie] = useCookies(["Auth"]);
    if (cookies.Auth === undefined){
        setCookie("Auth", null, {path: '/'});
        return (<Navigate to="/login" replace={true} />)
    } else if (!authUtil.hasAuth(cookies)) {
        setCookie("Auth", null, {path: '/'});
        alert("Session expired. Please log in again!")
        return (<Navigate to="/login" replace={true} />)
    }
    return (
        <></>
    );
}