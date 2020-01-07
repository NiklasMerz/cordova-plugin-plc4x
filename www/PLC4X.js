/*global cordova */

var PLC4X = function() {
};

PLC4X.prototype.connect = function (params, successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "PLC4X",
    "connect",
    [params]
  );
};

PLC4X.prototype.read = function (successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "PLC4X",
    "read",
    [{}]
  );
};

module.exports = new PLC4X();
