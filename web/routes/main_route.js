let express = require('express');
let router = express.Router();
let jwt = require('jsonwebtoken');
let config = require('../config/config');
let User = require('../schemas/user');

router.all("*", function (req, res, next) {

    if (true
        || req.url === '/' || req.url === '/favicon.ico'
        || req.url.includes('/auth/login-signup')
        || req.url.includes('/auth/verify')) {

        return next();
    }

    let token = req.headers["x-access-token"] || req.query.token || req.body.token;
    if (!token) {
        res.redirect('/')
    }
    else {
        jwt.verify(token, config.email.secret, function (err, decoded) {
            if (err) {
                res.sendFile(path.join(__dirname + "/../index.html"));
                return res.status(401).json({success: false, message: 'Failed to authenticate token.'});
            }
            else {
                req.phone_number = decoded;
                if (req.url.includes('/rest/')) {
                    User.findOne({phone_number: decoded}, function (err, user) {
                        if (err) {
                            console.log(err);
                            res.status(500).json({message: err});
                        }
                        else {
                            if (user) {
                                if (user.is_admin) {
                                    req.user = user;
                                    return next();
                                }
                                else {
                                    res.status(404).json({message: 'user ' + req.phone_number + ' not admin'});
                                }
                            }
                            else {
                                res.status(404).json({message: 'user ' + req.body.phone_number + ' dose not exist'});
                            }
                        }
                    });
                }
                else {
                    return next();
                }
            }
        });
    }
});

module.exports = router;