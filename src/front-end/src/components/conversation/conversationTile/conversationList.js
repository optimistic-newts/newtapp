import '../../../App.css';
import React from "react";
import ConversationTile from "./conversationTile";
import ChatButton from "./buttons/chatButton";
import JoinButton from "./buttons/joinButton";

// A component that displays one provided ConversationProfile
function ConversationList({ conversations, buttonType }) {
    let tiles;
    if (conversations === []) {
        tiles = null;
    } else if (buttonType === "Chat") {
        tiles = conversations.map((conversation) => <ConversationTile key={conversation.id}
                                                                      conversation={conversation}
                                                                      buttons={[<ChatButton key="Chat"
                                                                                            id={conversation.id}/>]}/>)
    } else if (buttonType === "Join"){
        tiles = conversations.map((conversation) => <ConversationTile key={conversation.id}
                                                                      conversation={conversation}
                                                                      buttons={[<JoinButton key="Join"
                                                                                            id={conversation.id}/>]}/>)
    }



    return (
        <>
            <div className="conversationList">
                {tiles}
            </div>
        </>
    );
}

export default ConversationList;