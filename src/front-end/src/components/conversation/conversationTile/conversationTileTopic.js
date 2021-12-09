import '../../../App.css';
import React from "react";

// A component that displays a collection of ConversationTiles. Input is an array of ConversationProfiles.
function ConversationTileTopic({ topic }) {
    return (
        <>
            <span className="conversationTileTopic">
                {topic}
            </span>
        </>
    );
}

export default ConversationTileTopic;