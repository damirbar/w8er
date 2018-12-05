var mongoose = require('mongoose');

const time = {

  open: {type: String, default: ""},
  close: {type: String, default: ""},
  days: {type: String, default: ""}

};
var RestaurantSchema = new mongoose.Schema({

  name: {type: String, default: "", required: true},
  phone_number: {type: String, required: true},
  layout: {},
  capacity: {},
  owner: {type: String, default: ""},
  tags: [String],
  pictures: [{
    url: {type: String, default: ""},
    id: {type: String, default: ""}
  }],
  last_modified: {type: Date, default: Date.now()},
  rating: {type: Number, default: 0},
  kosher: {type: Boolean, required: true, default: false},
  profile_img: {type: String, default: "http://res.cloudinary.com/w8er/image/upload/v1543601240/w8er/rest_default.jpg"},
  hours: [time],
  // hours = [{open: '08:00', close: '12:00', days: 'Sunday-Thursday'}, {open: '14:00', close: '18:00', days: 'Sunday-Thursday'}, {open: '08:00', close: '14:00', days: 'Friday'}];
  address: {type: String, default: ""},
  country: {type: String, default: ""},
  location: {
    type: {type: String, default: 'Point'},
    coordinates: []
  },
  reviews: [{
    amount: {type: Number},
    giver: {type: String, default: ""},
    image: {type: String, default: "https://res.cloudinary.com/w8er/image/upload/v1541594267/w8er/default.png"},
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
  },
  sessions: [String]

}, {usePushEach: true});

RestaurantSchema.index({location: "2dsphere"});

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
