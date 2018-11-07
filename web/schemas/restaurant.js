var mongoose = require('mongoose');

var RestaurantSchema = new mongoose.Schema({

    name: {type: String, default: "", required: true},
    phone_number: {type: String, required: true},
    location: {},
    menu: {},
    layout: {},
    reviews: [{
        amount: {type: Number},
        giver: {type: String, default: ""},
        message: {type: String, default: ""},
        date: {type: Date, default: Date.now()}
    }],
    capacity: {},
    owner: {type: String, default: ""},
    tags: [String],
    pictures: [String],
    rating: {type: Number, default: 0},
    kosher: {type: Boolean, required: true, default: false},
    sessions: {}

});

module.exports = mongoose.model('Restaurant', RestaurantSchema);