require('dotenv').config(); 

module.exports = {
    allowedOrigins: ['http://localhost:3000/'],
    SERVER_PORT: process.env.PORT || 6000,
    SERVER_DB_URI: process.env.dbURI,
    JWT_SECRET: process.env.SECRET_KEY,
    OTP_LENGTH: 6,
    OTP_CONFIG: {
        upperCaseAlphabets: false,
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