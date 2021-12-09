import React, {useState} from 'react';
import {useCookies} from "react-cookie";
import {Navigate} from 'react-router-dom';
import newtApi from "../../../../api";

export default function LeaveButton({ id }) {
    const cookies = useCookies(["Auth"])[0];
    const [redirect, setRedirect] = useState(false);
    const navTo = `/conversations`

    function handleClick() {
        if (window.confirm("Are you sure you want to Leave this Conversation?")) {
            newtApi.leaveConversation(cookies, id).then(() => setRedirect(true));
        }
    }

    if (redirect) {
        return (
            <>
                <Navigate to={navTo}/>
            </>
        )
    } else {
        return(
            <button className="newtBigButton" onClick={handleClick}>Leave</button>
        )
    }
}