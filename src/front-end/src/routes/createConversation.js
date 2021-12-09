import '../App.css';
import React from "react";
import Layout from "../components/layouts/layout";
import CookieCheck from "../components/cookieCheck";
import CreateConversationForm from "../components/forms/createConversationForm";

export default function CreateConversation() {
    return (
        <>
            <CookieCheck/>
            <Layout>
                <CreateConversationForm />
            </Layout>
        </>
    );
}