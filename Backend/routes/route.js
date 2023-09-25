'use strict'; 


const { generateOTP } = require('../services/otpService');
const { sendMail } = require('../services/Mail');
const jwt = require('jsonwebtoken'); 
const bcrypt = require('bcrypt'); 
const User = require('../database/Users'); 

const authController = require("../controllers/auth")

const loginController = require("../controllers/authLogin")



module.exports = function(app){

 

    app.route('/').get((req,res)=>{
        res.send("Hello API")
    })



    //register a new user an e-mail then the authentication
    app.route('/auth/registernewuser').post( async (req, res, next)=>{
        
        // retrieve user information
        const {username,email,password} = req.body;

        // check if username or e-mail are still available (for now just e-mail)
        const takenEmail = authController.findUserByEmail(email); 

        if(!takenEmail){
            res.status(400).send({"message":"the email you sent is already taken."}); 
        }

        //generate otp 
        const otpGenerated = generateOTP();

        
        try{
            const user = new User({username, email, password, otp: otpGenerated}); 
            await user.save(); 

            //e-mail otp
            const emailStatus = await sendMail({ to: email, OTP: otpGenerated });

            if(!emailStatus){
                res.status(400).send({"User":user, "Message": "User successfully created but OTP send failed."})
            }

            res.status(200).send({"User":user, "Report": "Registered user successfully"})
        }catch(error){
            res.status(400).send(error)
        }
    })



    //verify user otp and set account to active
    app.route("/auth/verifyaccount").post(async (req,res)=>{
        const {email, otp} = req.body ;
        const response = await authController.validateUserSignUp(email,otp); 

        if(!response.status){
            res.status(400).send({"Error":"We are unable to retrieve this email"})
        }else{
            res.status(200).send({"Report":"Account successfully activated"})
        }

    })



    app.route('/auth/login').post(async (req, res,next)=>{
        const {email, password } = req.body; 
        
        try{
            const response = await User.checkInUser(email,password); 
            if(!response.user){
                return res.status(400).send(response);
            }
            res.status(200).send(response.user)
            const token = jwt.sign({userId: user._id}, process.env.SECRET_KEY, {expiresIn: '1 hour'}); 
            return { Token: token, Status: "Success"}
        }catch(err){
            next(err);
        }
    })


    app.route('/app/profile').get((req, res)=>{
        res.send('Hello from app')
    })


}