import React from "react";
import { Navigate } from 'react-router-dom';
import newtApi from "../../api.js";
import { instanceOf } from 'prop-types';
import {withCookies, Cookies} from 'react-cookie';
import FormWarning from "./createUserFormWarning";

class CreateUserForm extends React.Component {
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        this.state = {redirect: false, warning: '', username: '', password: '', confirmedPassword: '', interests: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        // this syntax assigns one dictionary key, value pair without altering others
        this.setState({[event.target.name]: event.target.value});
    }

    async handleSubmit(event) {
        event.preventDefault();
        if (this.state.password !== this.state.confirmedPassword) {
            this.setState({warning: 'passwordMismatch'})
            return null;
        }
        const formData = {
            username: this.state.username,
            password: this.state.password,
            interests: this.state.interests.split(',').map((value) => {return value.trim();})
        }
        const newUser = await newtApi.createUser(formData);

        if (!newUser) {
            this.setState({warning: 'badUser'});
        } else {
            this.setState({redirect: true});
        }
    }

    render() {
        if (this.state.redirect) {
            return (<Navigate to="/login" replace={false} />)
        } else {
            return (
                <form onSubmit={this.handleSubmit}>
                    <input name="username" type="text" required="required" placeholder="Username" maxLength="30"
                           value={this.state.username} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="password" type="password" minLength="6" required="required" placeholder="Password"
                           value={this.state.password} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="confirmedPassword" type="password" minLength="6" required="required"
                           placeholder="Confirm Password" value={this.state.confirmedPassword}
                           onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input name="interests" type="text" required="required" placeholder="Interests"
                           value={this.state.interests} onChange={this.handleChange} className="newtTextInput"/> <br/>
                    <input type="submit" value="Create Account" className="newtButtonDark"/>
                    <FormWarning warning={this.state.warning} />
                </form>
            );
        }
    }
}

export default withCookies(CreateUserForm)