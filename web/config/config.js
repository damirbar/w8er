const email = {

    "name": "w8er Team",
    "email": "we.are.w8er@gmail.com‬",
    "password": "t34m@W83r",
    "secret": "w8er",

};

const cloudniary = {

    cloud_name: "w8er",
    api_key: '181628352118733',
    api_secret: 'YfpgjsJE6mmyim6un3vcMx1cn5g'

};

const passport = {

    googleClientId: '481912911563-m4r25vc2kbu14h288q8qk0or8sdn6i77.apps.googleusercontent.com',
    googleClientSecret: 'S4tYKir4C8hy5uqn4X4YNtWz',
    google_callback:"http://localhost:3000/auth/google/callback",

    FACEBOOK_APP_ID: '511191269358119',
    FACEBOOK_APP_SECRET: 'b22148c5ff681a4d10a161e499e6c1f4',
    facebook_callback:"http://localhost:3000/auth/facebook/callback"

};

const mongo = {

     mongoDB : 'mongodb://damir:damiri@cluster0-shard-00-00-00hhm.mongodb.net:27017,cluster0-shard-00-01-00hhm.mongodb.net:27017,cluster0-shard-00-02-00hhm.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin'

};

const ret = {cloudniary: cloudniary, email: email, passport: passport, mongo: mongo};
module.exports = ret;

