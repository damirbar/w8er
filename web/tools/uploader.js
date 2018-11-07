const cloudinary = require('../config/config').cloudniary;
const fs = require('fs');


let uploadService = {

    uploadProfileImage: function (file, path, user, res) {
        console.log("starting to upload " + file.originalname);
        cloudinary.v2.uploader.upload(path,
            {
                public_id: "profiles/" + user.id + "profile",
                width: 1000,
                height: 1000,
                crop: 'thumb',
                gravity: 'face',
                radius: 20
            }, function (err, result) {
                fs.unlinkSync(path);
                if (err) {
                    console.log(err);
                    res.status(500).json({message: err});
                }
                else {
                    console.log("uploaded " + file.originalname);
                    user.updateOne({profile_img: result.url}, function (err) {
                        if (err) {
                            console.log(err);
                            res.status(500).json({message: err});
                        }
                        else {
                            res.status(200).json({message: 'changed user profile image'});
                        }
                    });
                }
            });
    },

    uploadimagetorestaurant: function (file, path, rest, res) {
        console.log("starting to upload " + file.originalname);
        cloudinary.v2.uploader.upload(path,
            {
                public_id: "restaurants/" + rest.id + "_images",
                width: 1000,
                height: 1000,
                crop: 'thumb',
                radius: 20
            }, function (err, result) {
                fs.unlinkSync(path);
                if (err) {
                    console.log(err);
                    res.status(500).json({message: err});
                }
                else {
                    console.log("uploaded " + file.originalname);
                    rest.updateOne({ $push: { pictures: result.url } }, function (err) {
                        if (err) {
                            console.log(err);
                            res.status(500).json({message: err});
                        }
                        else {
                            res.status(200).json({message: 'added picture to restaurant'});
                        }
                    });
                }
            });
    },

};

module.exports = uploadService;