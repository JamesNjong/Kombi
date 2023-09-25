'use strict'
const  express = require('express'); 
require('dotenv').config();

const bodyParser = require('body-parser'); 
const apiRoutes = require("./routes/router.js"); 

const dbDidConnect = require('./database/connect.js'); 

const {SERVER_PORT} = require('./constants/constants');

const app = express();

app.use(bodyParser.json())

dbDidConnect();


apiRoutes(app);

let port = SERVER_PORT ; 
app.listen(port,()=>{
    console.log("App is listening on port "+port); 
})