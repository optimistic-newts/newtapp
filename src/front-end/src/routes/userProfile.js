import '../App.css';
import React, {useEffect, useState} from 'react'
import {useParams, Navigate} from "react-router-dom";
import Layout from "../components/layouts/layout";
import newtApi from "../api";
import CookieCheck from "../components/cookieCheck";
import Profile from "../components/user/profile";

const UserProfile = () => {
    const { username } = useParams()
    const [sent, setSent] = useState(false);
    const [loaded, setLoaded] = useState(false);
    const [user, setUser] = useState(null);

    useEffect(() => {
        async function getUser() {
            setSent(true);
            setUser(await newtApi.getUser(username));
            setLoaded(true);
        }
        if (!loaded && !sent) {
            getUser().then();
        } else if (loaded && user.id == null) {
            setSent(false);
        }
    }, [sent, loaded, user, username])

    if (!loaded) {
        return (
            <>
                <CookieCheck />
                <Layout>
                </Layout>
            </>
        )
    } else if (loaded && !sent) {
        return (
            <Navigate to="/" />
        )
    } else {
        return (
            <>
                <CookieCheck />
                <Layout>
                    <Profile userData={user} />
                </Layout>
            </>
        )
    }
}

export default UserProfile