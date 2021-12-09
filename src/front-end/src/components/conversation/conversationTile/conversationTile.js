import '../../../App.css';
import React from "react";
import ConversationTileTopic from "./conversationTileTopic";

// A component that displays a collection of ConversationTiles. Input is an array of ConversationProfiles.
function ConversationTile({ conversation, buttons }) {

    let i = 0;
    const topics = conversation.topics.map((topic) =>
        <ConversationTileTopic key={i++} topic={topic}/> )

    if (buttons === []) {
        return(
            <>
            <div className="conversationTile">
                <div className="conversationTileTitleBox">
                    <h2 className="conversationTileTitle">{conversation.title}</h2>
                </div>
                <div className="conversationTileTopics">
                    {topics}
                </div>
                <div className="conversationTileSidebar">
                    <div className="conversationTileSidebarExtra">
                        {conversation.location}<span aria-label="Globe Emoji" role="img" className="emoji"> &#x1F30D;</span>
                    </div>
                    <div className="conversationTileSidebarExtra">
                        {conversation.currSize} / {conversation.maxSize}<span aria-label="Newt Emoji" role="img" className="emoji"> &#x1F438;</span>
                    </div>
                </div>
            </div>
        </>
        )
    } else {
        return(
            <>
                <div className="conversationTile">
                    <div className="conversationTileTitleBox">
                        <h2 className="conversationTileTitle">{conversation.title}</h2>
                    </div>
                    <div className="conversationTileTopics">
                        {topics}
                    </div>
                    <div className="conversationTileSidebar">
                        <div className="conversationTileSidebarExtra">
                            {conversation.location}<span aria-label="Globe Emoji" role="img" className="emoji"> &#x1F30D;</span>
                        </div>
                        <div className="conversationTileSidebarExtra">
                            {conversation.currSize} / {conversation.maxSize}<span aria-label="Newt Emoji" role="img" className="emoji"> &#x1F438;</span>
                        </div>
                        <div className="conversationTileButton">
                            {buttons}
                        </div>
                    </div>
                </div>
            </>
        )
    }
}

export default ConversationTile;