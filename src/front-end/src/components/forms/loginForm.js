import React from "react";
import { Navigate } from 'react-router-dom';
import newtApi from "../../api.js";
import {instanceOf} from 'prop-types';
import {withCookies, Cookies} from 'react-cookie';
import authUtil from '../../auth';

class LoginForm extends React.Component {
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        this.state = {redirect: false, tryAgain: false, username: '', password: ''};
        const { cookies } = props;
        if (Boolean(authUtil.hasAuth(cookies))) {
            // if user is authorized, send them to home page
            this.state.redirect = true;
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        // this syntax assigns one dictionary key, value pair without altering others
        this.setState({[event.target.name]: event.target.value});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const authToken = await newtApi.login(this.state.username, this.state.password);
        if (!authToken) {
            // ask to try again
            this.setState({tryAgain: true});
            // alternative to state change: alert("Incorrect password. Please try again!")
        } else {
            const { cookies } = this.props;
            cookies.set('Auth', authToken, {path: '/'});
            this.setState({redirect: true});
        }
    }

    render() {
        if (this.state.redirect) {
            return(
                <Navigate to="/" replace={true} />
            )
        } else if (this.state.tryAgain) {
            return(
                <>
                    <form onSubmit={this.handleSubmit}>
                        <input name="username" type="text" required="required" placeholder="Username"
                               value={this.state.username} onChange={this.handleChange} className="newtTextInput"/> <br />
                        <input name="password" type="password" required="required" placeholder="Password"
                               value={this.state.password} onChange={this.handleChange} className="newtTextInput"/> <br />
                        <input type="submit" value="Log In" className="newtButtonDark"/>
                    </form>
                    <div className="formWarningText">
                        <p>Incorrect password. Please try again!</p>
                    </div>
                </>
            );
        } else {
            return(
                <form onSubmit={this.handleSubmit}>
                    <input name="username" type="text" required="required" placeholder="Username"
                           value={this.state.username} onChange={this.handleChange} className="newtTextInput"/> <br />
                    <input name="password" type="password" required="required" placeholder="Password"
                           value={this.state.password} onChange={this.handleChange} className="newtTextInput"/> <br />
                    <input type="submit" value="Log In" className="newtButtonDark"/>
                </form>
            );
        }
    }
}

export default withCookies(LoginForm)