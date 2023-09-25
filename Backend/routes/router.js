'use strict'; 


const { generateOTP } = require('../services/otpService');
const { sendMail } = require('../services/Mail');
const jwt = require('jsonwebtoken'); 
const bcrypt = require('bcrypt'); 
const User = require('../database/Users'); 

const controller = require("../controllers/controller"); 



module.exports = function(app){

 

    app.route('/').get((req,res)=>{
        res.send("Hello API")
    })


    app.route("/blvrd/signup/emailandpassword").post(async (req,res,next)=>{
        const {email, password, application} = req.body; 

        const response = await controller.signupUserWithEmailAndPassword(email, password, application); 

        if(response.status === 400){
            
            res.status(400).send(response)
            
        }else{

            res.status(200).send(response)
           
        }
        next();
    }); 

    app.route("/blvrd/signup/nameemailandpassword").post(async (req,res,next)=>{
        const {username, email, password,application} = req.body; 

        const response = await controller.signupUserWithEmailNameAndPassword(username,email, password,application); 

        if(response.status === 400){
            
            res.status(400).send(response)
            
        }else{
            res.status(200).send(response)
           
        }
        next();
    }); 

    app.route("/blvrd/signup/phonenumberandpin").post(async (req,res,next)=>{
        const {phonenumber, pin, application, otp} = req.body; 

        const response = await controller.createUserWithPhoneNumber(phonenumber, pin, application, otp);

        if(response.status === 400){
            
            res.status(400).send(response)
            
        }else{
            
            res.status(200).send(response)
           
        }
        next();
    }); 

    app.route("/blvrd/login/emailpassword").post(async (req,res,next)=>{
        const {email, password} = req.body; 
        const response = await controller.loginUserWithEmailAndPassword(email, password); 
        if(response.status === 400){
            res.status(400).send(response)
        }else{
            res.status(200).send(response)
        }
    }); 

    app.route("/blvrd/login/usernamepassword").post(async (req,res,next)=>{
        const {username, password} = req.body; 
        const response = await controller.loginUserWithNameAndPassword(username, password); 
        if(response.status === 400){
            res.status(400).send(response)
        }else{
            res.status(200).send(response)
        }
    }); 

    app.route("/blvrd/login/phonenumberpin").post(async (req,res,next)=>{
        const {phonenumber, pin} = req.body; 
        const response = await controller.loginUserWithPhoneNumberAndPin(phonenumber,pin); 
        if(response.status === 400){
            res.status(400).send(response)
        }else{
            res.status(200).send(response)
        }
    }); 

    app.route("/blvrd/signup/verifyemail").post(async (req,res,next)=>{
        const {email, otp} = req.body; 
        const response = await controller.validateUserEmail(email,otp); 
        if(response.status === 400){
            res.status(400).send(response)
        }else{
            res.status(200).send(response)
        }
    }); 

    app.route("/blvrd/tokenize").post(async (req,res,next)=>{

    }); 

}
 