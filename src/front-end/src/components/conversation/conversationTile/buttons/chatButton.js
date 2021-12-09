import React, {useState} from 'react'
import {Navigate} from "react-router-dom";

export default function ChatButton({ id }) {
    const [redirect, setRedirect] = useState(false);
    const navTo = `/conversations/${id}/view`

    function handleClick() {
        setRedirect(true);
    }

    if (redirect) {
        return (
            <>
                <Navigate to={navTo}/>
            </>
        )
    } else {
        return(
            <button className="newtBigButton" onClick={handleClick}>Chat</button>
        )
    }
}