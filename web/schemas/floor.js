var mongoose = require('mongoose');

var Floor = new mongoose.Schema({
  restaurant_id: {
    type: String,
    reuqired: true
  },
  style_theme: {
    type: String,
    default: "default"
  },
  tables: {
    type: [String],
    default: []
  },
  length: {
    type: Number,
    reuqired: true
  },
  width: {
    type: Number,
    required: true
  },
  creation_date: {
    type: Date,
    default: Date.now()
  },
  modification_date: {
    type: Date,
    default: Date.now()
  }
});

module.exports = mongoose.model('Floor', Floor);
