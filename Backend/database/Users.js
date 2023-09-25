const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const validator = require('validator'); 
const jwt = require('jsonwebtoken'); 



const userSchema = new mongoose.Schema(
  {
    username: {
      type: String, 
      unique: true,
      trim: true
    },
    email: {
      type: String, 
      unique: true,
      trim: true,
      unique:true,
      lowercase:true, 
      validate: value =>{
        if(!validator.isEmail(value)){
          throw new Error({Error: "Invalid Email Address"})
        }
      }
    },
    password: {
      type: String,
      minLength:6,
      trim:true
    },
    phonenumber: {
      type: String,
      unique:true,
      trim:true
    },
    role: {
      type: String,
      enum: ['user', 'admin'],
      default: 'user'
    },
    created: {
      type: String,
      default: new Date().toISOString(),
    },
    lastActive: {
      type: String,
      required: false,
    },
    active: {
      type: Boolean,
      default: false,
    },
    status :{
      type:Boolean,
      default: false 
    },
    otp: {
      type: String,
      required: true,
    },
    application: {
      type: String,
      required: true, 
      trim:true,
      default:"test"
    },
    pin: {
      type: String,
      required: true, 
      trim:true,
      default:"00000"
    },
    tokens: [{
      token:{
        type:String, 
        required:true
      }
    }]
  },
  { timestamps: true }
  
);

// Hash the password before saving it to the database
userSchema.pre('save', async function (next) {
  const user = this;
  if (!user.isModified('password')) return next();

  try {
    const salt = await bcrypt.genSalt(8);
    user.password = await bcrypt.hash(user.password, salt);
    user.pin = await bcrypt.hash(user.pin,salt)
    next();
  } catch (error) {
    return next(error);
  }
});

// Compare the given password with the hashed password in the database
userSchema.statics.checkInUser = async (username,password) => { 

  const user = await User.findOne({username}); 

  if(!user){
    return { status:400,error:"Invalid credetials"}
  }  
  let match = await bcrypt.compare(password, user.password);
  
  if(!match){
    return {status:400, error:"Invalid Login Credentials"}; 
  }

  return{status:400, user:user}; 
};



const User = mongoose.model('User', userSchema);

module.exports = User;