import '../../App.css';
import React from "react";
import ConversationTile from "./conversationTile/conversationTile";
import ChatButton from "./conversationTile/buttons/chatButton";
import JoinButton from "./conversationTile/buttons/joinButton";

// A component that displays one provided ConversationProfile
function ConversationList({ conversations, buttonType }) {
    console.log(conversations);
    let tiles;
    if (buttonType === "Chat") {
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