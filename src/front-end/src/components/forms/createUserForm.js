import React from "react";
import { Navigate } from 'react-router-dom';
import newtApi from "../../api.js";
import { instanceOf } from 'prop-types';
import {withCookies, Cookies} from 'react-cookie';

class CreateUserForm extends React.Component {
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        this.state = {redirect: false, tryAgain: false, username: '', password: '', interests: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        // this syntax assigns one dictionary key, value pair without altering others
        this.setState({[event.target.name]: event.target.value});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const formData = {
            username: this.state.username,
            password: this.state.password,
            interests: this.state.interests.split(',').map((value) => {return value.trim();})
        }
        const newUser = await newtApi.createUser(formData);

        if (!newUser) {
            this.setState({tryAgain: true});
        } else {
            this.setState({redirect: true});
        }
    }

    render() {
        if (this.state.redirect) {
            return (<Navigate to="/login" replace={false} />)
        } else if (this.state.tryAgain) {
            return (
                <form onSubmit={this.handleSubmit}>
                    <input name="username" type="text" required="required" placeholder="Username"
                           value={this.state.username} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="password" type="password" minLength="6" required="required" placeholder="Password"
                           value={this.state.password} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="interests" type="text" required="required" placeholder="Something you're interested in"
                           value={this.state.interests} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input type="submit" value="Create Account" className="newtButtonDark"/>
                    <div className="formWarningText">
                        <p>Username is in use already, sorry! Please try another!</p>
                    </div>
                </form>
            );
        } else {
            return (
                <form onSubmit={this.handleSubmit}>
                    <input name="username" type="text" required="required" placeholder="Username"
                           value={this.state.username} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="password" type="password" minLength="6" required="required" placeholder="Password"
                           value={this.state.password} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="interests" type="text" required="required" placeholder="Something you're interested in"
                           value={this.state.interests} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input type="submit" value="Create Account" className="newtButtonDark"/>
                </form>
            );
        }
    }
}

export default withCookies(CreateUserForm)