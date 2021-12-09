import React, {useState} from 'react'
import {Navigate} from "react-router-dom";
import {useCookies} from "react-cookie";
import newtApi from "../../../../api";

export default function JoinButton({ id }) {
    const cookies = useCookies(["Auth"])[0];
    const [redirect, setRedirect] = useState(false);
    const navTo = `/conversations/${id}/view`

    function handleClick() {
        newtApi.joinConversation(cookies, id).then(() => setRedirect(true));
    }

    if (redirect) {
        return (
            <>
                <Navigate to={navTo}/>
            </>
        )
    } else {
        return(
            <button className="newtBigButton" onClick={handleClick}>Join</button>
        )
    }
}