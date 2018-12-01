var mongoose = require('mongoose');


var sessionSchema = new mongoose.Schema({

  sid: {type: String, unique: true, required: true},
  pass: {type: String, default: ""},
  members: [String],
  items: [{
    itemId: {type: String, default: ""},
    purchaser: {type: String, default: ""}
  }],
  start_time: {type: Date, default: Date.now()},
  end_time: {type: Date},

});

module.exports = mongoose.model('Session', sessionSchema);
