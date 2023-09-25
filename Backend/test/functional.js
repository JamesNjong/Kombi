'use strict'

let chaiHttp = require("chai-http"); 
let chai = require("chai"); 
let assert = chai.assert; 
let server = require("../server")

chai.use(chaiHttp); 

const sample = {
    "username": "Jamzy", 
	"application":"TestApp",
	"password":"testosteron",
	"email" : "njongjames@outlook.com",
    "phonenumber": "+237 674 811 226"
}


suite("Functional Test:", function(){
    //testing the signup with phone number end
    suite("POST /blvrd/signup/emailandpassword/{userdata} => valid email and password", function(){
        test("Valid email and password", function(donw){
            chai
            .request(server).post("/blvrd/signup/emailandpassword")
            .send({
                "email" : "njongjames@outlook.com",
                "password":"testosteron",
                "application":"TestApp"
            }).end(function(err, res){
                assert.equal(res.status, 200); 
                assert.equal(res.body.Report, )
            })
        })

    })

    suite("POST /blvrd/signup/nameemailandpassword/{userdata} => valid email and password", function(){

    })

    suite("POST /blvrd/signup/phonenumberandpin/{userdata} => valid email and password", function(){

    })

    suite("POST /blvrd/login/emailpassword/{userdata} => valid email and password", function(){

    })

    suite("POST /blvrd/login/usernamepassword/{userdata} => valid email and password", function(){

    })

    suite("POST /blvrd/login/phonenumberpin/{userdata} => valid email and password", function(){

    })

    suite("POST /blvrd/signup/verifyemail/{userdata} => valid email and password", function(){

    })
})