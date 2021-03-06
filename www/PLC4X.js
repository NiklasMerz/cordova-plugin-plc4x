/*
  Copyright 2020 Niklas Merz

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/


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

PLC4X.prototype.read = function (items, successCallback, errorCallback) {
  cordova.exec(
    successCallback,
    errorCallback,
    "PLC4X",
    "read",
    [items]
  );
};

module.exports = new PLC4X();
