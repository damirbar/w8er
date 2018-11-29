const router = require('express').Router();
let Restaurant = require('../schemas/restaurant');
let Item = require('../schemas/item');

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
    req.checkBody("location", "Location is required").notEmpty();
    req.checkBody("location.coordinates[0]", "Longitude is required").notEmpty();
    req.checkBody("location.coordinates[1]", "Latitude is required").notEmpty();

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
        location: req.body.location
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
  Restaurant.findOne({_id: req.query.restId}, function (err, rest) {
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

router.get('/get-menu', function (req, res) {
  Restaurant.findOne({_id: req.query.restId}, function (err, rest) {
    if (err) {
      console.log("error in /get-menu");
      res.status(500).json({message: err});
    }
    else {
      if (rest) {
        Item.find({
          '_id': {$in: rest.menu[req.query.type]}
        }, function (err, items) {
          if (err) {
            console.log(err);
            res.status(500).json({message: err});
          }
          else {
            res.status(200).json({items: items});
          }
        });
      }
      else {
        res.status(404).json({message: 'restaurant ' + req.query.id + ' dose not exist'});
      }
    }
  })
});

module.exports = router;




