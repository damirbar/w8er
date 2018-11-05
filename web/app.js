let express = require("express");
let app = express();
let http = require('http').Server(app);
let bodyParser = require("body-parser");
let path = require("path");
require("./tools/less_compiler")();
let mongoose = require("mongoose");
let config = require('./config/config');
// mongoose.Promise = require("bluebird");


let passport = require("passport");
let passportService = require('./tools/passport');
app.use(passport.initialize());
app.use(passport.session());
passportService.init();



let authRouts = require("./routes/login_requests");



app.use('/auth', authRouts);






app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.use(express.static(path.join(__dirname, 'public')));

mongoose.connect(config.mongo.mongoDB, { useNewUrlParser: true },function (err,connection) {
    if (err){
        console.log('error in mongo connection:\n' + err);
    }
    else{
        console.log('successfully connected to mongoDB');
    }
});


let db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error!\n'));


http.listen(3000, function () {
    console.log("listening on 3000");
});