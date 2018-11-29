let express = require('express');
let router = express.Router();
let Restaurant = require("../schemas/restaurant");
const multer = require('multer');
const upload = multer({dest: 'upload/'});
const type = upload.single('recfile');
let uploader = require('../tools/uploader');
let fs = require('fs');

router.get("/get-profile", function (req, res) {
  res.status(200).json(req.user);
});

router.post("/edit-profile", function (req, res) {
  let updatedUser = req.body;
  let user = req.user;
  user.first_name = updatedUser.first_name ? updatedUser.first_name : user.first_name;
  user.last_name = updatedUser.last_name ? updatedUser.last_name : user.last_name;
  user.birthday = updatedUser.birthday ? updatedUser.birthday : user.birthday;
  user.gender = updatedUser.gender ? updatedUser.gender : user.gender;
  user.about_me = updatedUser.about_me ? updatedUser.about_me : user.about_me;
  user.email = updatedUser.email ? updatedUser.email : user.email;
  user.address = updatedUser.address ? updatedUser.address : user.address;
  user.country = updatedUser.country ? updatedUser.country : user.country;

  if (updatedUser.coordinates) {
    user.coordinates.lat = updatedUser.coordinates.lat ? updatedUser.coordinates.lat : user.coordinates.lat;
    user.coordinates.lng = updatedUser.coordinates.lng ? updatedUser.coordinates.lng : user.coordinates.lng;
  }

  user.last_modified = Date.now();
  user.save(function (err, user) {
    if (err) {
      console.log('error in saving user in /edit-profile');
      return res.status(500).json({message: err});
    }
    else {
      return res.status(200).json(user);
    }
  });
});

router.post('/post-profile-image', type, function (req, res) {
  if (!req.file) {
    console.log("no file");
    res.status(400).json({message: 'no file'});
  }
  else {
    const path = req.file.path;
    if (!req.file.originalname.toLowerCase().match(/\.(jpg|jpeg|png)$/)) {
      fs.unlinkSync(path);
      res.status(400).json({message: 'wrong file'});
      console.log("wrong file type");
    }
    else {
      uploader.uploadProfileImage(req.file, path, req.user, ("profiles/" + req.user.id + "profile"), res);
    }
  }
});

router.post('/review', function (req, res) {
  Restaurant.findByIdAndUpdate(req.body.id, {$pull: {reviews: {giver: req.user.id}}}, {new: true}, function (err, rest) {
    if (err) {
      console.log(err);
      res.status(500).json({message: err});
    }
    else {
      let review = {
        amount: req.body.amount,
        giver: req.user.id,
        message: req.body.message,
      };
      let query = {
        '$push': {},
        'set': {}
      };
      if (rest.reviews.length === 0) {
        query['$push'] = {'reviews': review};
        query['$set'] = {'rating': review.amount};
        Restaurant.findByIdAndUpdate(req.body.id, query, {new: true}, function (err, rst) {
          if (err) {
            console.log(err);
            res.status(500).json({message: err});
          }
          else {
            res.status(200).json(rst)
          }
        });
      }
      else {
        let count = 0;
        let avg = review.amount;
        let len = (rest.reviews.length);
        rest.reviews.forEach(function (rev) {
          count++;
          avg += rev.amount;
          if (count === len) {
            avg = avg / (len + 1);
            query['$push'] = {'reviews': review};
            query['$set'] = {'rating': avg};
            Restaurant.findByIdAndUpdate(req.body.id, query, {new: true}, function (err, rst) {
              if (err) {
                console.log(err);
                res.status(500).json({message: err});
              }
              else {
                res.status(200).json(rst)
              }
            });
          }
        });
      }
    }
  });
});

//test if restaurant exists?
router.post('/add-to-favorites', function (req, res) {
  if (!req.user.favorite_restaurants.includes(req.body._id)) {
    req.user.updateOne({'$push': {favorite_restaurants: req.body._id}}, function (err, usr) {
        if (err) {
          console.log(err);
          res.status(500).json({message: err});
        }
        else {
          res.status(200).json({message: 'added restaurant to favorites'});
        }
      }
    );
  }
  else {
    res.status(200).json({message: 'restaurant already in favorites'});
  }
});

router.post('/remove-from-favorites', function (req, res) {
  req.user.updateOne({'$pull': {favorite_restaurants: req.body._id}}, function (err, usr) {
      if (err) {
        console.log(err);
        res.status(500).json({message: err});
      }
      else {
        res.status(200).json({message: 'removed restaurant from favorites'});
      }
    }
  );
});

router.get('/get-favorites', function (req, res) {
  Restaurant.find({
    '_id': {$in: req.user.favorite_restaurants}
  }, function (err, rests) {
    if (err) {
      console.log(err);
      res.status(500).json({message: err});
    }
    else {
      res.status(200).json({restaurants: rests});
    }
  });


});

router.get('/get-my-restaurants', function (req, res) {
  Restaurant.find({
    '_id': {$in: req.user.restaurants}
  }, function (err, rests) {
    if (err) {
      console.log(err);
      res.status(500).json({message: err});
    }
    else {
      res.status(200).json({restaurants: rests});
    }
  });
});
module.exports = router;
