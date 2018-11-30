var mongoose = require('mongoose');


var itemSchema = new mongoose.Schema({

  name: {type: String, default: ""},
  description: {type: String, default: ""},
  type: {type: String, default: ""},
  price: {type: String, default: ""},
  available: {type: Boolean, default: true},
  tags: [String],
  image_url: {type: String, default: 'http://res.cloudinary.com/w8er/image/upload/v1543601007/w8er/item_default.jpg'},
  image_id: {type: String, default: ""}


});

module.exports = mongoose.model('Item', itemSchema);
