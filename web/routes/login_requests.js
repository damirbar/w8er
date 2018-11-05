const router = require('express').Router();

router.get("/test",function (req, res) {
    res.send('Hello World!');
});

router.post("/new-user", function (req, res) {

    if (!req.body.email) {
        req.body.email = 'undefined';
    }
    const fname = req.body.first_name,
        lname = req.body.last_name,
        email = req.body.email.toLowerCase(),
        password = req.body.password,
        password_cnfrm = req.body.password_cnfrm,
        role = req.body.role;


    req.checkBody("first_name", "First Name is required").notEmpty();
    req.checkBody("last_name", "Last Name is required").notEmpty();
    req.checkBody("email", "Email is required").notEmpty();
    req.checkBody("password", "Password is required").notEmpty();
    req.checkBody("password_cnfrm", "Confirm password is required").notEmpty();
    req.checkBody("role", "role is required").notEmpty();

    const errors = req.validationErrors();

    if (!validator.validate(email)) {
        let err = {
            location: "body",
            msg: "Invalid Email",
            param: 'email',
            value: undefined
        };
        errors.push(err);
    }
    if (password !== password_cnfrm) {
        let err = {
            location: "body",
            msg: "Passwords don't match",
            param: 'password',
            value: undefined
        };
        errors.push(err);
    }
    if (errors) {
        let str = "";
        for (let i = 0; i < errors.length; ++i) {
            console.log(errors[i].msg);
            str += errors[i].msg + "  ";
        }
        res.status(409).json({message: str});
    }
    else {
        const newUser = new User({
            first_name: fname,
            last_name: lname,
            email: email,
            register_date: Date.now(),
            last_update: Date.now(),
            password: password,
            role: role,
            events:
                {
                    event: "sing up to wizeUp",
                }
        });

        bcrypt.genSalt(10, function (err, salt) {
            bcrypt.hash(newUser.password, salt, null, function (err, hash) {
                newUser.password = hash;
                newUser.save(function (err, user) {
                    if (err) {
                        if (err.name === 'MongoError' && err.code === 11000) {
                            // Duplicate username
                            console.log(email + ' already exists!');
                            return res.status(500).json({message: email + ' already exists!'});
                        }
                        else if (err.name === 'ValidationError') {
                            let str = "";
                            for (field in err.errors) {
                                console.log("you must provide: " + field + " field");
                                str += "you must provide: " + field + " field  ";
                            }
                            return res.status(500).json({message: str});
                        }
                        else {// Some other error
                            console.log(err);
                            return res.status(500).json({message: err});
                        }
                    }
                    else {
                        console.log(user);
                        emailService.sendMail(user.email, 'Registration to wizeUp', emailMessages.registration(user));
                        const newStudent = new Student({
                            user_id: user._id
                        });
                        newStudent.save();
                        res.status(200).json(user);
                    }
                });
            });
        });
    }
});


module.exports = router;