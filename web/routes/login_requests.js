const router = require('express').Router();
const User = require("../schemas/user");
let randomstring = require("randomstring");
let nexmo = require('../config/config').nexmo;


router.post('/login-signup', function (req, res) {
    let phone_number = req.body.phone_number;
    User.findOne({phone_number: phone_number}, function (err, user) {
        if (err) {
            console.log("Error while finding user in /login-signup");
            res.status(500).json({message: err});
        }
        else {
            if (user) {
                //user exists
                let random = randomstring.generate(6);
                let nowplus5 = new Date(Date.now());
                nowplus5.setMinutes(nowplus5.getMinutes() + 5);

                let password = {
                    pass: random,
                    time: nowplus5,
                };

                user.updateOne({tmp_password: password}, function (err) {
                    if (err) {
                        console.log("Error while saving user in /login-signup");
                        res.status(500).json({message: err});
                    }
                    else {
                        //send tmp password to phone
                        const from = 'w8er';
                        const to = '+972526071827';
                        const text = 'your code is: ' + random + '                     .\n';
                        nexmo.message.sendSms(from, to, text, (err, data) => {
                            console.log(err, data)
                        });

                        res.status(200).json({user})
                    }
                });
            }
            else {
                //new user
                let random = randomstring.generate(6);
                let nowplus5 = new Date(Date.now());
                nowplus5.setMinutes(nowplus5.getMinutes() + 5);

                let password = {
                    pass: random,
                    time: nowplus5,
                };
                const newUser = new User({
                    phone_number: phone_number,
                    tmp_password: password
                });
                newUser.save(function (err, user) {
                    if(err){
                        console.log("Error while creating user in /login-signup");
                        console.log(err);
                        res.status(500).json({message: err});
                    }
                    else{
                        //send tmp password to phone
                        const from = 'w8er';
                        const to = '+972526071827';
                        const text = 'your code is: ' + random + '                     .\n';
                        nexmo.message.sendSms(from, to, text, (err, data) => {
                            console.log(err, data)
                        });

                        res.status(200).json({user})
                    }
                });
            }
        }
    });
});


router.post('/varify',function (req, res) {
    if(!req.body.phone_number){
        res.status(404).json({message: 'no user found to varify'})
    }
    else {
        User.findOne({phone_number: req.body.phone_number}, function (err, user) {
            if (err) {
                console.log("Error while finding user in /varify");
                res.status(500).json({message: err});
            }
            else {
                if (user) {
                    if(req.body.password === user.tmp_password.pass){
                        if(user.tmp_password.time >= new Date(Date.now())){
                            res.status(200).json(user);
                            let reset = {
                                pass: "",
                                time: new Date(Date.now())
                            };
                            user.updateOne({tmp_password: reset},function (err,res) {
                                if (err) {
                                    console.log("Error while uptading user in /varify");
                                }
                            })
                        }
                        else{
                            res.status(304).json({message:'password expired'});
                        }
                    }
                    else{
                        res.status(304).json({message:'wrong password'});
                    }

                }
                else {
                    res.status(404).json({message: 'no user found with phone_number ' + req.body.phone_number})
                }
            }
        });
    }
});
module.exports = router;