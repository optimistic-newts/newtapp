import jwt_decode from "jwt-decode";

// Singleton to simplify JWT authorization interface for use in front end JS
const authUtil = {

    // Return true iff cookies contains valid, non-expired JWT
     hasAuth(cookies) {
        if (cookies === undefined) {
            return false;
        } else if (cookies.Auth === undefined) {
            return false;
        }
        let token = cookies.Auth;
        let decodedToken = "";
        try {
            decodedToken = jwt_decode(token);
        } catch (InvalidTokenError) {
            return false;
        }
        if (authUtil.isExpired(decodedToken)) {
            return false;
        }
        // token is valid
        return true;
    },

    // Return true iff token is expired
    isExpired(decodedToken) {
         const now = Math.round(Date.now() / 1000);
         return !((decodedToken.iat <= now) && (now <= decodedToken.exp));
    },

    // Return the username that belongs to a JWT
    getUsername(token) {
         let decodedToken = "";
         try {
             decodedToken = jwt_decode(token);
         } catch (InvalidTokenError) {
             return "";
         }
         return decodedToken.username;
    }

}

export default authUtil