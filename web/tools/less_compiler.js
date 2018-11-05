var less = require('less');
var fs = require('fs');
var path = require('path');
var lessCompiler = function () {

    var buffer = "";

    var files = fs.readdirSync(path.join(__dirname, "../public/stylesheets/less"));

    files.forEach(function (file) {
        buffer += fs.readFileSync(path.join(__dirname, "../public/stylesheets/less/") + file);
    });

    fs.writeFile(path.join(__dirname, "../public/stylesheets/main.less"), buffer, function (err) {
        if (err) {
            return console.log("Error writing css! " + err);
        }
        // console.log("Successfully wrote main Less file!");
    });


    fs.readFile('./public/stylesheets/main.less', function (err) {
        if (err) {
            return console.log("Error reading main.less. " + err);
        }
        less.render(buffer)
            .then(function (output) {
                    fs.writeFileSync('./public/stylesheets/css/main.css', output.css);
                },
                function (err) {
                    console.log("And error occurred during rendering less files. " + err);
                });

    });

};

module.exports = lessCompiler;