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
    // favorite_foods: [String]
    // favorite_restaurants: [String]
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
            uploader.uploadProfileImage(req.file, path, user, ("profiles/" + user.id + "profile"), res);
        }
    }
});


//what if i already made a review???
router.post('/review', function (req, res) {
    Restaurant.findOne({_id: req.body.id}, function (err, rest) {
        if (err) {
            console.log(err);
            res.status(500).json({message: err});
        }
        else {
            if (rest) {
                let review = {
                    amount: req.body.amount,
                    giver: req.user.id,
                    message: req.body.message,
                };
                let new_rating = ((req.body.amount + rest.rating) / (rest.reviews.length + 1));
                rest.updateOne({
                    $set: {rating: new_rating},
                    $push: {'reviews': review}
                }, function (err, rest) {
                    if (err) {
                        console.log(err);
                        res.status(500).json({message: err});
                    }
                    else {
                        res.status(200).json({message: 'added review to restaurant'});
                    }
                });


            }
            else {
                res.status(404).json({message: 'no restaurant with id ' + req.body.id});
            }
        }
    });
});


module.exports = router;