const router = require('express').Router();
let geocoder = require('../config/config').geocoder;
let Restaurant = require('../schemas/restaurant');

router.get('/address-to-coord', function (req, res) {
  geocoder.geocode(req.query.address).then(function (result) {
    if (result.raw.status === "OK") {
      res.status(200).json({lat: result[0].latitude, lng: result[0].longitude});
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

router.get('/find-near-location', function (req, res) {
  let dist = parseFloat(req.query.dist) * 1000; // for kilometers
  Restaurant.find({
    location: {
      $near: {
        $maxDistance: dist,
        $geometry: {
          type: "Point",
          coordinates: [parseFloat(req.query.lng), parseFloat(req.query.lat)]
        }
      }
    }
  }).find(function (err, results) {
    if (err) {
      console.log(err);
      res.status(500).json({message: err});
    }
    else {
      res.status(200).json({restaurants: results});
    }
  });
});

router.get('/coord-to-address', function (req, res) {
  geocoder.reverse({lat: 45.767, lon: 4.833}, function (err, res) {
    console.log(res);
  });
});
module.exports = router;
