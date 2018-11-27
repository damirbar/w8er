const router = require('express').Router();
let Restaurant = require('../schemas/restaurant');

const expressValidator = require('express-validator');
router.use(expressValidator());

router.post('/create', function (req, res) {

  if (!req.user.is_admin) {
    res.status(403).json({message: 'user not admin'})
  }
  else {
    req.checkBody("name", "Name is required").notEmpty();
    req.checkBody("address", "Address required").notEmpty();
    req.checkBody("country", "Country is required").notEmpty();
    req.checkBody("hours", "Hours are required").notEmpty();
    req.checkBody("coordinates", "Coordinates are required").notEmpty();
    req.checkBody("coordinates.lat", "Latitude is required").notEmpty();
    req.checkBody("coordinates.lng", "Longitude is required").notEmpty();

    const errors = req.validationErrors();

    if (errors) {
      let str = "";
      for (let i = 0; i < errors.length; ++i) {
        console.log(errors[i].msg);
        str += errors[i].msg + "  ";
      }
      res.status(409).json({message: str});
    }
    else {
      let rest = new Restaurant({
        name: req.body.name,
        phone_number: req.phone_number,
        owner: req.user.id,
        tags: req.body.tags,
        kosher: req.body.kosher,
        address: req.body.address,
        country: req.body.country,
        hours: req.body.hours,
        coordinates: {
          lat: req.body.coordinates.lat,
          lng: req.body.coordinates.lng
        }
      });
      rest.save(function (err, rest) {
        if (err) {
          console.log(err);
          res.status(500).json({message: err});
        }
        else {
          req.user.updateOne({'$push': {restaurants: rest.id}}, function (err, usr) {
            if (err) {
              console.log(err);
              res.status(500).json({message: err});
            }
            else {
              res.status(200).json(rest);
            }
          });
        }
      });
    }
  }
});

router.get('/get-rest', function (req, res) {
  Restaurant.findOne({_id: req.query.id}, function (err, rest) {
    if (err) {
      console.log("error in /get-rest");
      res.status(500).json({message: err});
    }
    else {
      if (rest) {
        res.status(200).json(rest);
      }
      else {
        res.status(404).json({message: 'restaurant ' + req.query.id + ' dose not exist'});
      }
    }
  })
});

module.exports = router;




