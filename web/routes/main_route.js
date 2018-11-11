let express = require('express');
let router = express.Router();
let jwt = require('jsonwebtoken');
let config = require('../config/config');

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
                return next();
            }
        });
    }
});

module.exports = router;