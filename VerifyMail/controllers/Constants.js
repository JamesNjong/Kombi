'use-strict'

require("dotenv").config();


module.exports = { 
    SERVER_PORT: process.env.PORT || 6000,  
    OTP_LENGTH: 6,
    OTP_CONFIG: {
        upperCaseAlphabets: true,
        specialChars: false,
    },
    MAIL_SETTINGS: {
        service: 'gmail',
        host:"smtp.gmail.com", 
        port: 587,
        secure: false,
        auth: {
            user: process.env.MAIL_EMAIL,
            pass: process.env.MAIL_PASSWORD,
        },
    }
}

