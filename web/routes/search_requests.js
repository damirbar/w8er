var express = require('express');
var router = express.Router();
let geocoder = require('../config/config').geocoder;
var Restaurant = require("../schemas/restaurant");


let SET_DISTANCE_IN_CITY = 5;

router.get('/free-text-search', function (req, res) {

  let keywords = req.query.keyword;

  let numOfCollections = 1;
  let doneCounter = 0;

  let searchResults = {};

  let sendSearchResults = function () {
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
          coordinates: req.coordinates
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
      res.status(200).json({restaurants: ans});
    }
  });
});
module.exports = router;
