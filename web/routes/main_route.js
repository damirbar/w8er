let express = require('express');
let router = express.Router();
let jwt = require('jsonwebtoken');
let config = require('../config/config');
let User = require('../schemas/user');
let Restaurant = require('../schemas/restaurant');
let path = require('path');

router.all("*", function (req, res, next) {

  if (req.url === '/' || req.url === '/favicon.ico' ||
    req.url.includes('/auth/') ||
    req.url.includes('/free-text-search') ||
    req.url.includes('/find-near-location') ||
    req.url.includes('/get-rest')) {

    return next();
  }

  let token = req.headers["x-access-token"] || req.query.token || req.body.token;
  if (!token) {
    res.redirect('/')
  } else {
    jwt.verify(token, config.email.secret, function (err, decoded) {
      if (err) {
        res.sendFile(path.join(__dirname + "/../dist/w8erWebapp/index.html"));
        return res.status(401).json({message: 'Failed to authenticate token.'});
      } else {
        req.phone_number = decoded;
        User.findOne({phone_number: decoded}, function (err, user) {
            if (err) {
              console.log(err);
              res.status(500).json({message: err});
            } else {
              if (user) {
                req.user = user;
                if (req.url.includes('/restAuth')) {
                  if (user.restaurants.includes(req.body.restId) || user.restaurants.includes(req.query.restId)) {
                    Restaurant.findOne({$or: [{_id: req.body.restId}, {_id: req.query.restId}]}, function (err, rest) {
                      if (err) {
                        console.log("error in /finding restaurant");
                        res.status(500).json({message: err});
                      }
                      else {
                        if (rest) {
                          req.rest = rest;
                          return next();
                        }
                        else {
                          res.status(404).json({message: 'no such restaurant'})
                        }
                      }
                    });
                  }
                  else {
                    res.status(403).json({messasge: 'not permitted'})
                  }
                }
                else {
                  return next();
                }
              } else {
                res.status(404).json({
                  message: 'user ' + req.body.phone_number + ' dose not exist'
                });
              }
            }
          }
        );
      }
    });
  }
});

router.get('/', function (req, res) {
  res.sendFile(path.join(__dirname + "/../dist/w8erWebapp/index.html"));
});

module.exports = router;
