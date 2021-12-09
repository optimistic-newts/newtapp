import '../../../App.css';
import React from "react";

// A component that displays a message. Input is a message.
// Nest step is dynamically sizing these to fit title/content!
function Message({ message, userMap }) {
    return (
        <>
            <div className="message">
                <div className="messageInfo">
                    <div className="messageAuthor">
                        {userMap[message.author]}
                    </div>
                    <div className="messageTime">
                        {message.writtenAt}
                    </div>
                </div>
                <div className="messageBody">
                    {message.body}
                </div>
            </div>
        </>
    )
}

export default Message;