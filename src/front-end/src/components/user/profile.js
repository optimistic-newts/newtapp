import '../../App.css';
import logo from "../../images/logo.png";
import React, {useEffect, useState} from "react";
import ConversationTileTopic from "../conversation/conversationTile/conversationTileTopic";
import newtApi from "../../api";
import {useCookies} from "react-cookie";

function Profile({ userData }) {
    // TODO: implement dynamic Profile based on relationship to user (follows/blocked/is me)
    const cookies= useCookies(["Auth"])[0];
    let [follow, setFollow] = useState(false);

    let i = 0;
    const topics = userData.interests.map((topic) =>
        <ConversationTileTopic key={i++} topic={topic}/> )

    const followClick = () => setFollow(true);

    useEffect(() => {
        async function followUser() {
            await newtApi.follow(cookies, userData.username);
            setFollow(false);
        }
        if (follow) {
            followUser().then();
        }
    }, [follow, cookies, userData.username])

    return (
        <>
            <div className="userProfileBanner">
                <span className="userProfilePhotoContainer">
                    <img src={logo} alt="Logo" className="userProfilePhoto"/>
                </span>
                <span className="userProfileNameContainer">
                    <h1 className={"userProfileName"}>{userData.username}</h1>
                </span>
                <span className="userProfileFollowButton">
                    <button onClick={followClick} className="newtButton">Follow</button>
                </span>
            </div>
            <div className="userProfileContent">
                <div className="userProfileInfoPanel">
                    <ul className="userProfileInfo">
                        <li>Location: {userData.location}</li>
                        <li>Interests:</li>
                    </ul>
                    <div className="userProfileInterests">
                        {topics}
                    </div>
                </div>
                <div className="userProfileConversationsPanel">
                </div>
            </div>
        </>
    );
}

export default Profile;