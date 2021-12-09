import '../../../App.css';
import React, {useEffect, useState} from "react";
import Message from "./message";

// A component that displays list of supplied messages
function MessageList({ messageArray, userProfiles }) {
    const [scrolled, setScrolled] = useState(false);
    const [messagesEnd, setMessagesEnd] = useState(null);

    let userMap = {}
    for (const user of Object.values(userProfiles)) {
        userMap[user.id] = user.username;
    }

    let messages;
    if (messageArray !== []) {
        messages = messageArray.map((message) => <Message key={message.id} message={message}
                                                          userMap={userMap}/> )
    } else {
        messages = null;
    }

    useEffect(() => {
        if (messagesEnd != null && !scrolled) {
            setScrolled(true);
            messagesEnd.scrollIntoView({
                behavior: "instant"
            });
        }
    }, [scrolled, messagesEnd])

    return (
        <>
            <div className="messageList">
                {messages}
                <div style={{ float:"left", clear: "both" }}
                     ref={(el) => setMessagesEnd(el)}>
                </div>
            </div>
        </>
    );
}

export default MessageList;