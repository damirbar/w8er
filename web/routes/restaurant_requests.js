const router = require('express').Router();
let Restaurant = require('../schemas/restaurant');
let User = require('../schemas/user');
const multer = require('multer');
const upload = multer({dest: 'upload/'});
const type = upload.single('recfile');
let uploader = require('../tools/uploader');
let fs = require('fs');

const MAX_PICTURES_PER_RESTAURANT = 5;

router.post('/create',function (req,res) {
    let phone_number = req.phone_number;
    User.findOne({phone_number: phone_number}, function (err, user) {
        if (err) {
            console.log(err);
            res.status(500).json({message: err});
        }
        else {
            if(user){
                if(user.is_admin){
                    let rest = new Restaurant({
                        name: req.body.name,
                        phone_number: req.phone_number,
                        owner: user.id,
                        tags: req.body.tags,
                        kosher: req.body.kosher
                    });

                    rest.save(function (err,rest) {
                        if(err){
                            console.log(err);
                            res.status(500).json({message: err});
                        }
                        else{
                            res.status(200).json(rest);
                        }
                    })
                }
                else{
                    res.status(404).json({message: 'user ' + req.phone_number + ' not admin'});
                }
            }
            else{
                res.status(404).json({message: 'user ' + req.body.phone_number + ' dose not exist'});
            }
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
            Restaurant.findOne({phone_number: req.phone_number}, function (err, rest) {
                if (err) {
                    console.log("error in upload image to restaurant");
                    fs.unlinkSync(path);
                    res.status(500).json({message: err});
                }
                else {
                    if (rest) {
                        if(rest.pictures.length >= MAX_PICTURES_PER_RESTAURANT){
                            res.status(200).json({message: "to many pictures"})
                        }
                        else {
                            uploader.uploadimagetorestaurant(req.file, path, rest, res);
                        }
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





