import React from "react";
import newtApi from "../../api.js";
import {instanceOf} from 'prop-types';
import {withCookies, Cookies} from 'react-cookie';

class CreateMessageForm extends React.Component{
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired,
    }

    constructor(props) {
        super(props);
        this.state = {redirect: false, body: ""};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.nav = `/conversations/${this.props.id}/view`;
    }

    handleChange(event) {
        // this syntax assigns one dictionary key, value pair without altering others
        this.setState({[event.target.name]: event.target.value});
    }

    async handleSubmit(event){
        event.preventDefault()
        const { cookies, id } = this.props;
        await newtApi.createMessage(cookies.cookies, id, this.state.body)
            .then(() => window.location.reload())

    }

    componentWillUnmount() {
        this.setState({redirect: false})
    }

    render(){
        return (
            <form onSubmit={this.handleSubmit} className="createMessageForm">
                <textarea name="body" required="required" placeholder="What do you think?"
                          onChange={this.handleChange} className="messageInput"/> <br />
                <input type="submit" value="Send" className="newtButton"/>
            </form>
        )
    }
}

export default withCookies(CreateMessageForm)