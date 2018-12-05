const router = require('express').Router();
const User = require("../schemas/user");
let nexmo = require('../config/config').nexmo;
let config = require('../config/config');
let jwt = require('jsonwebtoken');

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
                let random = generate(4);
                let nowplus5 = new Date(Date.now());
                nowplus5.setMinutes(nowplus5.getMinutes() + 5);

                user.updateOne({tmp_password: random, tmp_time: nowplus5}, function (err) {
                    if (err) {
                        console.log("Error while saving user in /login-signup");
                        res.status(500).json({message: err});
                    }
                    else {
                        //send tmp password to phone
                        const from = 'w8er';
                        const to = phone_number;
                        const text = 'your code is: ' + random + '                     .\n';
                        console.log(text);
                        res.status(200).json({message: "verification sent"})
                        // nexmo.message.sendSms(from, to, text, function (err, data) {
                        //     if (err) {
                        //         console.log("error in sending text in login");
                        //         res.status(404).json({message: err})
                        //     }
                        //     else {
                        //         if (data.messages[0]['error-text']) {
                        //             console.log("error in sending text in login: " + data.messages[0]['error-text']);
                        //             res.status(404).json({message: data.messages[0]['error-text']})
                        //         }
                        //         else {
                        //             res.status(200).json({message: "verification sent"})
                        //         }
                        //     }
                        // });
                    }
                });
            }
            else {
                //new user
                let random = generate(6);
                let nowplus5 = new Date(Date.now());
                nowplus5.setMinutes(nowplus5.getMinutes() + 5);

                const newUser = new User({
                    phone_number: phone_number,
                    tmp_password: random,
                    tmp_time: nowplus5
                });
                newUser.save(function (err, user) {
                    if (err) {
                        console.log("Error while creating user in /login-signup");
                        console.log(err);
                        res.status(500).json({message: err});
                    }
                    else {
                        //send tmp password to phone
                        const from = 'w8er';
                        const to = phone_number;
                        const text = 'your code is: ' + random + '                     .\n';
                        console.log(text);
                        res.status(200).json({message: "verification sent"})

                      // nexmo.message.sendSms(from, to, text, function (err, data) {
                        //     if (err) {
                        //         console.log("error in sending text in login");
                        //         res.status(404).json({message: err})
                        //     }
                        //     else {
                        //         if (data.messages[0]['error-text']) {
                        //             console.log("error in sending text in login: " + data.messages[0]['error-text']);
                        //             res.status(404).json({message: data.messages[0]['error-text']})
                        //         }
                        //         else {
                        //             res.status(200).json({message: "verification sent"})
                        //         }
                        //     }
                        // });
                    }
                });
            }
        }
    });
});

router.post('/verify', function (req, res) {
    if (!req.body.phone_number) {
        res.status(404).json({message: 'no user found to verify'})
    }
    else {
        User.findOne({phone_number: req.body.phone_number}, function (err, user) {
            if (err) {
                console.log("Error while finding user in /varify");
                res.status(500).json({message: err});
            }
            else {
                if (user) {
                    if (req.body.password === user.tmp_password && req.body.password !== "") {
                        if (user.tmp_time >= new Date(Date.now())) {
                            const token = jwt.sign(user.phone_number, config.email.secret);
                            user.tmp_password = "";
                            user.tmp_time = new Date(Date.now());
                            user.accessToken = token;
                            user.save(function (err, usr) {
                                if (err) {
                                    console.log("Error while uptading user in /verify");
                                    res.status(500).json({message: err})
                                }
                                else {
                                    res.status(200).json(usr);
                                }
                            });
                        }
                        else {
                            res.status(401).json({message: 'password expired'});
                        }
                    }
                    else {
                        res.status(401).json({message: 'wrong password'});
                    }

                }
                else {
                    res.status(404).json({message: 'no user found with phone_number ' + req.body.phone_number})
                }
            }
        });
    }
});

function generate(length) {
    let chars = '0123456789';
    let result = "";
    for (var i = length; i > 0; --i)
        result += chars[Math.round(Math.random() * (chars.length - 1))]
    return result
}

module.exports = router;
