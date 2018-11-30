var mongoose = require('mongoose');


var itemSchema = new mongoose.Schema({

  name: {type: String, default: ""},
  description: {type: String, default: ""},
  type: {type: String, default: ""},
  price: {type: String, default: ""},
  available: {type: Boolean, default: true},
  tags: [String],
  pictures: [{
    url: {type: String, default: ""},
    id: {type: String, default: ""}
  }],

});

module.exports = mongoose.model('Item', itemSchema);
