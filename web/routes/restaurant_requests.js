const router = require('express').Router();
let Restaurant = require('../schemas/restaurant');
let Item = require('../schemas/item');
const multer = require('multer');
const upload = multer({dest: 'upload/'});
const type = upload.single('recfile');
let uploader = require('../tools/uploader');
let fs = require('fs');

const expressValidator = require('express-validator');
router.use(expressValidator());

const MAX_PICTURES_PER_RESTAURANT = 5;

router.post('/create', function (req, res) {

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
                res.status(200).json(rest);
            }
        });
    }
});


router.get('/get-rest', function (req, res) {
    Restaurant.findOne({id: req.query.id}, function (err, rest) {
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


router.post("/edit-rest", function (req, res) {
    Restaurant.findOne({phone_number: req.phone_number}, function (err, rest) {
        if (err) {
            console.log("error in /edit-rest");
            res.status(500).json({message: err});
        }
        else {
            if (rest) {
                let updatedUser = req.body;
                rest.name = updatedUser.name ? updatedUser.name : rest.name;
                rest.tags = updatedUser.tags ? updatedUser.tags : rest.tags;
                rest.kosher = updatedUser.kosher ? updatedUser.kosher : rest.kosher;
                rest.address = updatedUser.address ? updatedUser.address : rest.address;
                rest.country = updatedUser.country ? updatedUser.country : rest.country;
                rest.hours = updatedUser.hours ? updatedUser.hours : rest.hours;
                rest.coordinates.lat = updatedUser.coordinates.lat ? updatedUser.coordinates.lat : rest.coordinates.lat;
                rest.coordinates.lng = updatedUser.coordinates.lng ? updatedUser.coordinates.lng : rest.coordinates.lng;
                rest.last_modified = Date.now();
                rest.save(function (err, rest) {
                    if (err) {
                        console.log(err);
                        res.status(500).json({message: err});
                    }
                    else {
                        res.status(200).json(rest);
                    }
                });
            }
            else {
                return res.status(404).json({message: 'no restaurant under this name'});
            }
        }
    });
})
;

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
                        if (rest.pictures.length >= MAX_PICTURES_PER_RESTAURANT) {
                            res.status(200).json({message: "to many pictures"})
                        }
                        else {
                            uploader.uploadimagetorestaurant(req.file, path, rest, res);
                        }
                    }
                    else {
                        fs.unlinkSync(path);
                        res.status(404).json({message: 'no such restaurant '})
                    }
                }
            });
        }
    }
});


router.post("/add-item", function (req, res) {
    Restaurant.findOne({phone_number: req.phone_number}, function (err, rest) {
        if (err) {
            console.log("error in /add-item-to-menu");
            res.status(500).json({message: err});
        }
        else {
            if (rest) {
                let type = req.body.type;
                if (type === "appetizer" || type === "main_course" || type === "dessert" || type === "drinks" || type === "deals" || type === "specials") {
                    let item = new Item({
                        name: req.body.name,
                        description: req.body.description,
                        price: req.body.price,
                        available: req.body.available,
                        tags: req.body.tags,
                        picture: req.body.picture
                    });
                    item.save(function (err, i) {
                        if (err) {
                            console.log(err);
                            res.status(500).json({message: err});
                        }
                        else {
                            let query = {$push: {}};
                            query['$push']['menu.' + type] = i.id;
                            rest.updateOne(query, function (err, rest) {
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
                else {
                    res.status(404).json({message: 'wrong type'});
                }
            }
            else {
                return res.status(404).json({message: 'no restaurant under this name'});
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
                item.picture = req.body.picture ? req.body.picture : item.picture;
                item.save(function (err, item) {
                    if (err) {
                        console.log(err);
                        res.status(500).json({message: err});
                    }
                    else {
                        res.status(200).json({message: 'item edited'});
                    }
                })
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
                Restaurant.findOne({phone_number: req.phone_number}, function (err, rest) {
                    if (err) {
                        console.log("error in /remove-item");
                        res.status(500).json({message: err});
                    }
                    else {
                        if (rest) {
                            let type = req.body.type;
                            if (rest.menu[type].includes(item.id)) {
                                let query = {$pull: {}};
                                query['$pull']['menu.' + type] = item.id;
                                rest.updateOne(query, function (err, rest) {
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
                                        })
                                    }
                                });
                            }
                            else {
                                return res.status(404).json({message: 'item not in restaurant'});
                            }
                        }
                        else {
                            return res.status(404).json({message: 'no restaurant under this name'});
                        }
                    }
                });
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
            Restaurant.findOne({phone_number: req.phone_number}, function (err, rest) {
                if (err) {
                    console.log("error in upload profile image restaurant");
                    fs.unlinkSync(path);
                    res.status(500).json({message: err});
                }
                else {
                    if (rest) {
                        uploader.uploadProfileImage(req.file, path, rest, ('restaurants/' + rest.id + "profile"), res);
                    }
                    else {
                        fs.unlinkSync(path);
                        res.status(404).json({message: 'no such restaurant ' + req.phone_number})
                    }
                }
            });
        }
    }
});

module.exports = router;





