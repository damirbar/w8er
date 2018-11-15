var mongoose = require('mongoose');


var itemSchema = new mongoose.Schema({

    name: {type: String, default: ""},
    description: {type: String, default: ""},
    price: {type: Number},
    available: {type: Boolean, default: true},
    tags: [String],
    picture: {type: String, default: ""}

});

module.exports = mongoose.model('Item', itemSchema);