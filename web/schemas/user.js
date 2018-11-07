var mongoose = require('mongoose');

var UserSchema = new mongoose.Schema({

    phone_number: {type: String, unique: true, required: true},
    first_name: {type: String, default: ""},
    last_name:  {type: String, default: ""},
    email:  {type: String, default: ""},
    password: {type: String, default: ""},
    about_me: {type: String, default: ""},
    events: [{
        type: {type: String, default: "general"},
        event: {type: String, default: ""},
        date: {type: Date, default: Date.now()}
    }],
    profile_img: {type: String, default: "fixMe"},
    is_admin: {type: Boolean, default: false},
    favorite_foods: [String],
    favorite_restaurants: [String],
    tmp_password:   {type: String, default: ""},
    tmp_time: {type: Date, default: Date.now()},
    accessToken: {type: String, default: ""},
    creation_date: {type: Date, default: Date.now()}

});

module.exports = mongoose.model('User', UserSchema);
