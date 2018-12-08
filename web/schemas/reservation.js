var mongoose = require('mongoose');

var Reservation = new mongoose.Schema({
  
  table_id: {
    type: String,
    reuqired: true
  },
  restaurant_id: {
    type: String,
    reuqired: true
  },
  creator_id: {
    type: String,
    reuqired: true
  },
  members: {
    type: [String],
    default: []
  },
  num_of_members: {
    type: number,
    required: true
  },
  creation_date: {
    type: Date,
    default: Date.now()
  },
  moification_date: {
    type: Date,
    default: Date.now()
  },
  start_date: {
    type: Date,
    required: true
  },
  end_date: {
    type: Date,
    required: true
  },
  isOnGoing: {
    type: boolean,
    default: false
  }
});

module.exports = mongoose.model('Reservation', Reservation);