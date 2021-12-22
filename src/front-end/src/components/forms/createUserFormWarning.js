import React from 'react'

const FormWarning = ({ warning }) => {
    // warning must be one of badUser, passwordMismatch to display a warning.
    if (warning === 'badUser') {
        return (
            <div className="formWarningText">
                <p>Username is in use already, sorry! Please try another!</p>
            </div>
        )
    } else if (warning === 'passwordMismatch') {
        return (
            <div className="formWarningText">
                <p>Passwords do not match! Try again!</p>
            </div>
        )
    } else {
        return null
    }
}

export default FormWarning