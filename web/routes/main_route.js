let express = require('express');
let router = express.Router();
let jwt = require('jsonwebtoken');
let config = require('../config/config');
let User = require('../schemas/user');
let path = require('path');

router.all("/", function (req, res, next) {

  if (req.url === '/' || req.url === '/favicon.ico' ||
    req.url.includes('/auth/login-signup') ||
    req.url.includes('/auth/verify') ||
    req.url.includes('free-text-search') ||
    req.url.includes('get-rest')) {

    return next();
  }

  let token = req.headers["x-access-token"] || req.query.token || req.body.token;
  if (!token) {
    res.redirect('/')
  } else {
    jwt.verify(token, config.email.secret, function (err, decoded) {
      if (err) {
        res.sendFile(path.join(__dirname + "/../dist/w8erWebapp/index.html"));
        return res.status(401).json({
          success: false,
          message: 'Failed to authenticate token.'
        });
      } else {
        req.phone_number = decoded;
        if (req.url.includes('/rest/') || req.url.includes('/user/')) {
          User.findOne({
            phone_number: decoded
          }, function (err, user) {
            if (err) {
              console.log(err);
              res.status(500).json({
                message: err
              });
            } else {
              if (user) {
                if (req.url.includes('/user/') || user.is_admin) {
                  req.user = user;
                  return next();
                } else {
                  res.status(404).json({
                    message: 'user ' + req.phone_number + ' not admin'
                  });
                }
              } else {
                res.status(404).json({
                  message: 'user ' + req.body.phone_number + ' dose not exist'
                });
              }
            }
          });
        } else {
          return next();
        }
      }
    });
  }
});

router.get('/', function (req, res) {
  res.sendFile(path.join(__dirname + "/../dist/w8erWebapp/index.html"));
});

module.exports = router;
