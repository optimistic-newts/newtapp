import '../App.css';
import Layout from "../components/layouts/layout";
import CookieCheck from "../components/cookieCheck";
import React, {useEffect, useState} from "react";
import newtApi from "../api";
import {useCookies} from "react-cookie";
import ConversationList from "../components/conversation/conversationList";

export default function Friends() {
    const cookies= useCookies(["Auth"])[0];
    const [sent, setSent] = useState(false);
    const [loaded, setLoaded] = useState(false);
    const [conversations, setConversations] = useState([]);

    useEffect(() => {
        async function getConversations() {
            setSent(true);
            setConversations(await newtApi.getRelevantConversationsByFollow(cookies));
        }
        if (!loaded && !sent) {
            getConversations().then();
        } else if (conversations !== []) {
            setLoaded(true);
        }
    }, [sent, loaded, conversations, cookies])

    if (!loaded) {
        return (
            <>
                <CookieCheck />
                <Layout>
                </Layout>
            </>
        )
    } else {
        return (
            <>
                <CookieCheck />
                <Layout>
                    <ConversationList conversations={conversations} buttonType="Join"/>
                </Layout>
            </>
        )
    }
}