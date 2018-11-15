var express = require('express');
var router = express.Router();
var Restaurant = require("../schemas/restaurant");


router.get('/free-text-search', function (req, res) {

    let keywords = req.query.keyword;

    let numOfCollections = 1;
    let doneCounter = 0;

    let searchResults = {};

    let sendSearchResults = function () {
        res.status(200).json(searchResults);
    };

    Restaurant.find({$text: {$search: keywords}}, function (err, results) {
        if (err) {
            console.log("Error in search Restaurants");
            console.log(err);
        } else if (results.length > 0) {
            searchResults.restaurants = results;
        }
        doneCounter++;
        if (doneCounter === numOfCollections) {
            sendSearchResults();
        }
    });
});
module.exports = router;
