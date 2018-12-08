var mongoose = require('mongoose');

var Table = new mongoose.Schema({
  restaurant_id: {
    type: String,
    required: true
  },
  color: {
    type: String,
    default: ''
  },
  capacity: {
    type: Number,
    required: true
  }, //maximum seats
  min_capacity: {
    type: Number,
    required: true
  }, //minimum amount of seats for reservation
  longtitude: {
    type: Number,
    required: true
  },
  latitude: {
    type: Number,
    required: true
  },
  isReservable: {
    type: boolean,
    default: true
  },
  isResered: {
    type: boolean,
    default: false
  },
});

module.exports = mongoose.model('Table', Table);
