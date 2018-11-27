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

//fix
router.get('/find-near-location', function(req, res){
  Restaurant.find({
    location: {
      $near: {
        $maxDistance: 1000,
        $geometry: {
          type: "Point",
          coordinates: [35, 32]
        }
      }
    }
  }).find((error, results) => {
    if (error) console.log(error);
    console.log(JSON.stringify(results, 0, 2));
  });
});



module.exports = router;
