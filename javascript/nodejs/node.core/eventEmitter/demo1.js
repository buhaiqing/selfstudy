// demo for Node eventEmitter
var assert= require('assert');
var util = require('util');
var EventEmitter = require('events').EventEmitter;

// ============define a new class that inheritate from EventEmitter

var TestClass = function(){
	EventEmitter.call(this);
};
util.inherits(TestClass,EventEmitter);


// ==========================
var testobj = new TestClass();

var i = {
	value : 1
};
testobj.on("add",function() {
	for (var i = 0; i < arguments.length; i++) {
		console.log(arguments[i]);
	};
});

testobj.data = 23;
testobj.emit("add",1, 2,3,4,5);






