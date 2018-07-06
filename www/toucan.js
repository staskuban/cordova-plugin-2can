var exec = require('cordova/exec');
// метод оплаты картами
exports.pay = function (arg0, success, error) {
  exec(success, error, 'toucan', 'pay', [arg0]);
};

