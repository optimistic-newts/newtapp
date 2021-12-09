import './App.css';
import React from 'react';
import {useCookies} from "react-cookie";
import {Navigate} from "react-router-dom";
import Layout from "./components/layouts/layout";

function App() {
    const [cookies, setCookie] = useCookies(["Auth"]);
    if (cookies.Auth === undefined){
        setCookie("Auth", null, {path: '/'});
        return (
            <Layout>
                <Navigate to="/login" replace={true} />
            </Layout>
        )
    }
    return (
        <Layout>
            <Navigate to={"/browse"} replace={true} />
        </Layout>
    );
}

export default App;
