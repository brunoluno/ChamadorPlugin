var exec = require('cordova/exec');

exports.getMedias = function (success, error) {
    exec(success, error, 'ChamadorPlugin', 'getMedias', []);
};
