var mongoose = require('mongoose');

var Search = new mongoose.Schema({
  word:  {type: String, unique: true, required: true},
  hits: {type: Number, default: 1}
});

module.exports = mongoose.model('Search', Search);
