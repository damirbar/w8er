var mongoose = require('mongoose');

var RestaurantSchema = new mongoose.Schema({

    name: {type: String, default: "", required: true},
    phone_number: {type: String, required: true},
    layout: {},
    capacity: {},
    owner: {type: String, default: ""},
    tags: [String],
    pictures: [String],
    last_modified: {type: Date, default: Date.now()},
    rating: {type: Number, default: 0},
    kosher: {type: Boolean, required: true, default: false},
    sessions: {},
    address: {type: String, default: ""},
    coordinates: {
        lat: {type: String, default: ""},
        lng: {type: String, default: ""}
    },
    reviews: [{
        amount: {type: Number},
        giver: {type: String, default: ""},
        message: {type: String, default: ""},
        date: {type: Date, default: Date.now()}
    }],
    menu: {
        appetizer: [String],
        main_course: [String],
        dessert: [String],
        drinks: [String],
        deals: [String],
        specials: [String]
    }

}, {usePushEach: true});

RestaurantSchema.index({
        name: "text",
        tags: "text",
    },
    {
        weights: {
            name: 10,
            tags: 5
        },
        name: "TextIndex"
    });
module.exports = mongoose.model('Restaurant', RestaurantSchema);