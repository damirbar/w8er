var express = require('express');
var router = express.Router();
let geocoder = require('../config/config').geocoder;
var Restaurant = require("../schemas/restaurant");
var Search = require("../schemas/search");

let SET_DISTANCE_IN_CITY = 5;

router.get('/free-text-search', function (req, res) {

  let keywords = req.query.keyword;

  let numOfCollections = 1;
  let doneCounter = 0;

  let searchResults = {};

  let sendSearchResults = function () {
    addToUser(req);
    addToSearch(req);
    res.status(200).json(searchResults);
  };

  Restaurant.find({$text: {$search: keywords}}, function (err, results) {
    if (err) {
      console.log("Error in search Restaurants");
      console.log(err);
    } else if (results.length > 0) {
      searchResults.restaurants = results;
    }
    doneCounter++;
    if (doneCounter === numOfCollections) {
      sendSearchResults();
    }
  });
});

router.post('/search-by-address-tags', function (req, res) {
  geocoder.geocode(req.body.address).then(function (result) {
    if (result.raw.status === "OK") {
      let dist = parseFloat(SET_DISTANCE_IN_CITY) * 1000; // for kilometers
      Restaurant.find({
        location: {
          $near: {
            $maxDistance: dist,
            $geometry: {
              type: "Point",
              coordinates: [parseFloat(result[0].longitude), parseFloat(result[0].latitude)]
            }
          }
        }
      }).find(function (err, results) {
        if (err) {
          console.log(err);
          res.status(500).json({message: err});
        }
        else {
          let len = results.length;
          let ans = [];
          for (let i = 0; i < len; ++i) {
            var target = req.body.tags;
            var checkArray = results[i].tags;
            if (target.some(x => checkArray.some(y => y === x))) {
              ans.push(results[i]);
            }
          }
          addToUser(req);
          addToSearch(req);
          res.status(200).json({restaurants: ans});
        }
      });
    }
    else {
      console.log("something went wrong in create restaurant with geocoder");
      console.log(result);
      return res.status(404).json({message: "problem with address"});
    }
  }).catch(function (e) {
    console.log("problem with geocoder in create restaurant:");
    console.error(e.message);
    return res.status(404).json({message: "problem with address"});
  });
});

router.post('/search-by-coord-tags', function (req, res) {
  let dist = parseFloat(SET_DISTANCE_IN_CITY) * 1000; // for kilometers
  Restaurant.find({
    location: {
      $near: {
        $maxDistance: dist,
        $geometry: {
          type: "Point",
          coordinates: req.body.location.coordinates
        }
      }
    }
  }).find(function (err, results) {
    if (err) {
      console.log(err);
      res.status(500).json({message: err});
    }
    else {
      let len = results.length;
      let ans = [];
      for (let i = 0; i < len; ++i) {
        var target = req.body.tags;
        var checkArray = results[i].tags;
        if (target.some(x => checkArray.some(y => y === x))) {
          ans.push(results[i]);
        }
      }
      addToUser(req);
      addToSearch(req);
      res.status(200).json({restaurants: ans});
    }
  });
});

router.get('/get-top-results', function (req, res) {
  Search.find({}, null, {limit: 10, sort: {word: -1}}, function (err, words) {
    if (err) {
      console.log("error in /get-top-results");
      res.status(500).json({message: err});
    }
    else {
      res.status(200).json({searches: words});
    }
  });
});

function addToUser(req) {
  let token = req.headers["x-access-token"] || req.query.token || req.body.token;
  if (token) {
    jwt.verify(token, config.email.secret, function (err, decoded) {
      if (err) {
        console.log(err);
      }
      else {
        req.phone_number = decoded;
        User.findOne({phone_number: decoded}, function (err, user) {
          if (err) {
            console.log(err);
            res.status(500).json({message: err});
          }
          else {
            if (user) {
              Restaurant.find({$text: {$search: req.body.tags[0]}}, function (err, results) {
                if (err) {
                  console.log("Error in search Restaurants");
                  console.log(err);
                } else if (results.length > 0) {
                  req.user.updateOne({$push: {searches: req.query.keyword}}, function (err, user) {
                    if (err) {
                      console.log(err);
                    }
                  });
                }
              });
            }
          }
        });
      }
    });
  }
}

function addToSearch(req) {
  Search.findOne({word: req.body.tags[0].toLowerCase()}, function (err, word) {
    if (err) {
      console.log(err);
      console.log("error in addToSearch");
    }
    else {
      if (word) {
        word.updateOne({hits: (word.hits + 1)}, function (err, ans) {
          if (err) {
            console.log(err);
          }
        });
      }
      else {
        Restaurant.find({$text: {$search: req.body.tags[0]}}, function (err, results) {
          if (err) {
            console.log("Error in search Restaurants");
            console.log(err);
          } else if (results.length > 0) {
            let word = new Search({
              word: req.body.tags[0].toLowerCase()
            });
            word.save()
          }
        });
      }
    }
  });
}

module.exports = router;
