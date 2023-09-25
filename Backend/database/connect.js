require('dotenv').config(); 
const mongoose = require('mongoose');

const {SERVER_DB_URI} = require('../constants/constants'); 
 

const Connection = async () => {
  try {
    await mongoose.connect(SERVER_DB_URI, {
      useNewUrlParser: true,
      useUnifiedTopology: true 
    });
    console.log('Connected to MongoDB');
  } catch (error) {
    console.log('Error connecting to MongoDB:', error);
  }
};

module.exports = Connection; 





