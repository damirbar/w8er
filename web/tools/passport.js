// let passport = require('passport');
// // let GoogleStrategy = require('passport-google-oauth').OAuth2Strategy;
// // let FacebookStrategy = require('passport-facebook').Strategy;
// let LocalStrategy = require('passport-local').Strategy;
// // let config = require('../config/config');
// let User = require('../schemas/user');
//
//
// module.exports.init = function () {
//
//     passport.serializeUser(function (user, done) {
//         done(null, user.id);
//     });
//
//     passport.deserializeUser(function (id, done) {
//         User.findById(id, function (err, user) {
//             done(err, user);
//         });
//     });
//
//     //local
//     passport.use('local-signup', new LocalStrategy({
//             // by default, local strategy uses username and password, we will override with email
//             usernameField: 'phone_number',
//             passwordField: 'password',
//             passReqToCallback: true // allows us to pass back the entire request to the callback
//         },
//         function (req, phone_number, password, done) {
//             User.findOne({phone_number: phone_number}, function (err, user) {
//                 if (err) return done(err);
//                 if (user) {
//                     // user exists
//                 } else {
//                     // new user
//                     if (req.body.first_name && req.body.last_name) {
//                         const fname = req.body.first_name,
//                             lname = req.body.last_name,
//                             email = req.body.email.toLowerCase(),
//                             password = req.body.password,
//                             password_cnfrm = req.body.password_cnfrm,
//                             role = req.body.role;
//
//
//                         req.checkBody("first_name", "First Name is required").notEmpty();
//                         req.checkBody("last_name", "Last Name is required").notEmpty();
//                         req.checkBody("email", "Email is required").notEmpty();
//                         req.checkBody("password", "Password is required").notEmpty();
//                         req.checkBody("password_cnfrm", "Confirm password is required").notEmpty();
//                         req.checkBody("role", "role is required").notEmpty();
//
//                         const errors = req.validationErrors();
//
//                         if (!validator.validate(email)) {
//                             let err = {
//                                 location: "body",
//                                 msg: "Invalid Email",
//                                 param: 'email',
//                                 value: undefined
//                             };
//                             errors.push(err);
//                         }
//                         if (password !== password_cnfrm) {
//                             let err = {
//                                 location: "body",
//                                 msg: "Passwords don't match",
//                                 param: 'password',
//                                 value: undefined
//                             };
//                             errors.push(err);
//                         }
//                         if (errors) {
//                             let str = "";
//                             for (let i = 0; i < errors.length; ++i) {
//                                 console.log(errors[i].msg);
//                                 str += errors[i].msg + "  ";
//                             }
//                             return done({error: true, message: str}, null);
//                         }
//                         const newUser = new User({
//                             first_name: fname,
//                             last_name: lname,
//                             email: email,
//                             register_date: Date.now(),
//                             last_update: Date.now(),
//                             password: password,
//                             role: role,
//                             events:
//                                 {
//                                     event: "sing up to wizeUp",
//                                 }
//                         });
//
//                         bcrypt.genSalt(10, function (err, salt) {
//                             bcrypt.hash(newUser.password, salt, null, function (err, hash) {
//                                 newUser.password = hash;
//                                 newUser.save(function (err, user) {
//                                     if (err) {
//                                         if (err.name === 'MongoError' && err.code === 11000) {
//                                             // Duplicate username
//                                             console.log(email + ' already exists!');
//                                             return res.status(500).json({message: email + ' already exists!'});
//                                         }
//                                         else if (err.name === 'ValidationError') {
//                                             let str = "";
//                                             for (field in err.errors) {
//                                                 console.log("you must provide: " + field + " field");
//                                                 str += "you must provide: " + field + " field  ";
//                                             }
//                                             return res.status(500).json({message: str});
//                                         }
//                                         else {// Some other error
//                                             console.log(err);
//                                             return res.status(500).json({message: err});
//                                         }
//                                     }
//                                     else {
//                                         console.log(user);
//                                         email.sendMail(user.email, 'Registration to wizeUp', emailMessages.registration(user));
//                                         const newStudent = new Student({
//                                             user_id: user._id
//                                         });
//                                         newStudent.save();
//                                         res.status(200).json(user);
//                                     }
//                                 });
//                             });
//                         });
//                     }
//                     else {
//                         return done({error: true, message: 'No such user'}, null);
//                     }
//                 }
//
//             });
//         }));
// };
