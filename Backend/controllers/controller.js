'use strict';

const jwt = require('jsonwebtoken'); 
const bcrypt = require('bcrypt');  
const nodemailer = require('nodemailer');  
const { generateOTP } = require('../services/otpService');
const { sendMail } = require('../services/Mail');
const User = require('../database/Users');



//sign up methods


module.exports.signupUserWithEmailAndPassword = async (email, password, application) => {

  if(!isValidEmail(email)){
    return  {"status": 400, "Report":'Invalid email address'};
  }
    
  const isExisting = await findUserByEmail(email);
  if (isExisting) {
    return  {"status": 400, "Report":'User already exist'};
  }
  
   //generate otp 
  const otpGenerated = generateOTP();

  try{
    const user = new User({email, password, otp: otpGenerated,application}); 
    await user.save(); 
 
    //e-mail otp
    const emailStatus = await sendMail({ to: email, OTP: otpGenerated });
    if(!emailStatus){
        return  {"status": 400, "Report":'User successfully created but OTP send failed.'}; 
    }
        return  {"status": 200, "Report":'Account created successfully.', User:user};
    }catch(error){
        return  {"status": 200, "Report":error.message , User:user};
    }

} 


module.exports.signupUserWithEmailNameAndPassword = async (username, email, password,application) => {
    const isExisting = await findUserByEmail(email);
    if (isExisting) {
        return  {"status": 400, "Report":'User already exist'};
    }
    
    //generate otp 
    const otpGenerated = generateOTP();

    try{
        const user = new User({username, email, password, otp: otpGenerated,application}); 
        await user.save(); 
        //e-mail otp
        const emailStatus = await sendMail({ to: email, OTP: otpGenerated });
        if(!emailStatus){
            return  {"status": 400, "Report":'User successfully created but OTP send failed.'}; 
        }
        return  {"status": 200, "Report":'Account created successfully.', User:user};
    }catch(error){
        return  {"status": 400, "Report":error.message };
    }
}



module.exports.validateUserEmail = async (email,otp)=>{

    const user = await User.findOne({ email});
    if (!user) {
        return {"status": 400, "Report":'User not found'};
    }
    if (user && user.otp !== otp ) {
        return {"status": 400, "Report":'Invalid OTP'};
    }
    const updatedUser = await User.findByIdAndUpdate(user._id, {
        $set: { active: true },  
    });

    if(!updatedUser.active){
        return {"status": 400, "Report":'Unable to update user'};
    }
    return {"status": 200, "Report":'Update completed successfully.', user:updatedUser};
}



module.exports.createUserWithPhoneNumber = async (phonenumber, pin, application, otp) => {
  const isExisting = await findUserByEmail(email);
  if (isExisting) {
    return  {"status": 400, "Report":'User already exist'};
  }

  try{
    const user = new User({phonenumber, pin, application, otp}); 
    await user.save(); 
   
    return  {"status": 200, "Report":'Account created successfully.', User:user};
  }catch(error){
    return  {"status": 200, "Report":error.message , User:user};
  }

}



//login methods 
module.exports.loginUserWithPhoneNumberAndPin = async (phonenumber, pin)=>{
    const user = findUserByPhone(phonenumber); 
    if(!user){
        return  {"status": 400, "Report":'Unable to locate user.'};
    }

    const matchPin = await bcrypt.compare(pin, user.pin); 

    if(user && !matchPin){
        return  {"status": 400, "Report":'Cannot recognize this PIN.'};
    }

    const login = await loginUser(user._id); 

    if(!login.status){
        return  {"status": 400, "Report":login.error};
    }else{
        return  {"status": 200, "Report":'User logged in successfully', user: login.updatedUser};
    }
}

module.exports.loginUserWithEmailAndPassword = async (email,password) =>{
    const user = findUserByEmail(email); 
    if(!user){
        return  {"status": 400, "Report":'Unable to locate user.'};
    }
    const matchPassword = await bcrypt.compare(password, user.password); 
    if(user && !matchPassword){
        return  {"status": 400, "Report":'Invalid login credentials'};
    }
    
    const login = await loginUser(user._id); 

    if(!login.status){
        return  {"status": 400, "Report":login.error};
    }else{
        return  {"status": 200, "Report":'User logged in successfully', user: login.updatedUser};
    }
}

module.exports.loginUserWithNameAndPassword = async (username, password) => {
    const user = await findUserByName(username); 
    if(!user){
        return  {"status": 400, "Report":'Unable to locate user.'};
    }
    
    const matchPassword = await bcrypt.compare(password, user.password); 
    if(user && !matchPassword){
        return  {"status": 400, "Report":'Invalid login credentials'};
    }
    
    const login = await loginUser(user._id); 

    if(!login.status){
        return  {"status": 400, "Report":login};
    }else{
        return  {"status": 200, "Report":'User logged in successfully', user: login};
    }
}


 
const findUserByEmail = async (email) =>{
    const user = await User.findOne({ email });
    if (!user) {
        return false;
    }
    return user;
}

const findUserByName = async (username) =>{
    const user = await User.findOne({ username });
    if (!user) {
        return false;
    }
    return user;
}

const findUserByPhone = async (phonenumber) =>{
    const user = await User.findOne({ phonenumber });
    if (!user) {
        return false;
    }
    return user;
}


const loginUser = async(id) => {
    try{
        const updatedUser = await User.findByIdAndUpdate(id,{$set:{status:true}})
        return {"Status":true,updatedUser}
    }catch(error){
        return {"Status":false,error}
    }
}


const isValidEmail = (email) =>{
    const emailRegex = new RegExp(/^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,}$/);

    const isEmailValid = emailRegex.test(email);

   return isEmailValid; 

}