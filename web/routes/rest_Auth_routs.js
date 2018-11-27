const router = require('express').Router();
let Item = require('../schemas/item');
const multer = require('multer');
const upload = multer({dest: 'upload/'});
const type = upload.single('recfile');
let uploader = require('../tools/uploader');
let fs = require('fs');

const MAX_PICTURES_PER_RESTAURANT = 5;

router.post("/edit-rest", function (req, res) {
  let updatedUser = req.body;
  let rest = req.rest;
  rest.name = updatedUser.name ? updatedUser.name : rest.name;
  rest.tags = updatedUser.tags ? updatedUser.tags : rest.tags;
  rest.kosher = updatedUser.kosher ? updatedUser.kosher : rest.kosher;
  rest.address = updatedUser.address ? updatedUser.address : rest.address;
  rest.country = updatedUser.country ? updatedUser.country : rest.country;
  rest.hours = updatedUser.hours ? updatedUser.hours : rest.hours;

  if (updatedUser.coordinates) {
    rest.coordinates.lat = updatedUser.coordinates.lat ? updatedUser.coordinates.lat : rest.coordinates.lat;
    rest.coordinates.lng = updatedUser.coordinates.lng ? updatedUser.coordinates.lng : rest.coordinates.lng;
  }
  rest.last_modified = Date.now();
  rest.save(function (err, rst) {
    if (err) {
      console.log(err);
      res.status(500).json({message: err});
    }
    else {
      res.status(200).json(rst);
    }
  });
});

router.post('/add-pic', type, function (req, res) {
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
      if (req.rest.pictures.length >= MAX_PICTURES_PER_RESTAURANT) {
        res.status(403).json({message: "to many pictures"})
      }
      else {
        uploader.uploadimagetorestaurant(req.file, path, req.rest, res);
      }
    }
  }
});

router.post("/add-item", function (req, res) {
  let type = req.body.type;
  if (type === "appetizer" || type === "main_course" || type === "dessert" || type === "drinks" || type === "deals" || type === "specials") {
    let item = new Item({
      name: req.body.name,
      description: req.body.description,
      price: req.body.price,
      available: req.body.available,
      tags: req.body.tags
    });
    item.save(function (err, i) {
      if (err) {
        console.log(err);
        res.status(500).json({message: err});
      }
      else {
        let query = {$push: {}};
        query['$push']['menu.' + type] = i.id;
        req.rest.updateOne(query, function (err, rest) {
          if (err) {
            console.log(err);
            res.status(500).json({message: err});
          }
          else {
            res.status(200).json({message: 'added item to menu'});
          }
        });
      }
    });
  }
});

router.post('/edit-item-photo', type, function (req, res) {
  Item.findOne({_id: req.body.id}, function (err, item) {
    if (err) {
      console.log("error in /edit-item-photo");
      res.status(500).json({message: err});
    }
    else {
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
          uploader.uploadItemPic(req.file, path, item, res);
        }
      }
    }
  });
});

router.post('/edit-item', function (req, res) {
  Item.findOne({id: req.id}, function (err, item) {
    if (err) {
      console.log("error in /edit-item");
      res.status(500).json({message: err});
    }
    else {
      if (item) {
        item.name = req.body.name ? req.body.name : item.name;
        item.description = req.body.description ? req.body.description : item.description;
        item.price = req.body.price ? req.body.price : item.price;
        item.available = req.body.available ? req.body.available : item.available;
        item.tags = req.body.tags ? req.body.tags : item.tags;
        item.save(function (err, item) {
          if (err) {
            console.log(err);
            res.status(500).json({message: err});
          }
          else {
            res.status(200).json({message: 'item edited'});
          }
        });
      }
      else {
        return res.status(404).json({message: 'no such item'});
      }
    }
  });
});

router.post('/remove-item', function (req, res) {
  Item.findOne({_id: req.body.id}, function (err, item) {
    if (err) {
      console.log("error in /remove-item");
      res.status(500).json({message: err});
    }
    else {
      if (item) {
        let type = req.body.type;
        if (req.rest.menu[type].includes(item.id)) {
          let query = {$pull: {}};
          query['$pull']['menu.' + type] = item.id;
          req.rest.updateOne(query, function (err, rest) {
            if (err) {
              console.log(err);
              res.status(500).json({message: err});
            }
            else {
              res.status(200).json({message: 'removed item from menu'});
              item.remove(function (err, item) {
                if (err) {
                  console.log('error in removing item ' + item.id);
                  console.log('the error is in /remove-item');
                }
              });
            }
          });
        }
        else {
          return res.status(404).json({message: 'item not in restaurant'});
        }
      }
      else {
        return res.status(404).json({message: 'no such item'});
      }
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
      uploader.uploadProfileImage(req.file, path, req.rest, ('restaurants/' + req.rest.id + "profile"), res);
    }
  }
});

router.get('/get-my-restaurants', function (req, res) {
  res.status(200).json({menu: req.user.restaurants})
});

module.exports = router;
