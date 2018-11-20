let express = require('express');
let router = express.Router();
let User = require("../schemas/user");
const multer = require('multer');
const upload = multer({dest: 'upload/'});
const type = upload.single('recfile');
let uploader = require('../tools/uploader');
let fs = require('fs');

router.get("/get-profile", function (req, res) {
    let phone_number = req.phone_number;
    User.findOne({phone_number: phone_number}, function (err, user) {
        if (err) {
            console.log(err);
            res.status(500).json({message: err});
        }
        else {
            if (user) {
                res.status(200).json(user);
            }
            else {
                res.status(404).json({message: 'user ' + req.phone_number + ' dose not exist'});
            }
        }
    });
});

router.post("/edit-profile", function (req, res) {
    let phone_number = req.phone_number;
    // let phone_number = "+972526071827";
    User.findOne({phone_number: phone_number}, function (err, user) {
        if (err) {
            console.log("error in /edit-profile");
            res.status(500).json({message: err});
        }
        else {
            if (user) {
                let updatedUser = req.body;
                user.first_name = updatedUser.first_name ? updatedUser.first_name : user.first_name;
                user.last_name = updatedUser.last_name ? updatedUser.last_name : user.last_name;
                user.birthday = updatedUser.birthday ? updatedUser.birthday : user.birthday;
                user.gender = updatedUser.gender ? updatedUser.gender : user.gender;
                user.about_me = updatedUser.about_me ? updatedUser.about_me : user.about_me;
                user.email = updatedUser.email ? updatedUser.email : user.email;
                user.last_modified = Date.now();
                user.address.lat = updatedUser.address.lat ? updatedUser.address.lat : user.address.lat;
                user.address.lng = updatedUser.address.lng ? updatedUser.address.lng : user.address.lng;
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
            }
            else {
                return res.status(404).json({message: 'user ' + req.phone_number + ' dose not exist sorry'});
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
            User.findOne({phone_number: req.phone_number}, function (err, user) {
                if (err) {
                    console.log("error in upload profile image");
                    fs.unlinkSync(path);
                    res.status(500).json({message: err});
                }
                else {
                    if (user) {
                        uploader.uploadProfileImage(req.file, path, user, res);
                    }
                    else {
                        fs.unlinkSync(path);
                        res.status(404).json({message: 'no such user ' + req.phone_number})
                    }
                }
            });
        }
    }
});

module.exports = router;