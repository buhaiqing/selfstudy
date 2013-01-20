//util in node.js
var util = require('util');
var ok = require('assert').ok;
var equal = require('assert').equal;
var notEqual = require('assert').notEqual;

// util.format
var i = "test";

ok(util.isArray([1]) === true);


var test = function() {
	this.a = 1;
	this.b = 2;
};

var test1 = function() {
	test.apply(this);
};

var test2 = function() {};
test2.prototype = new test1();

var test3 = function() {
	Object.create(this);
};

var o = new test();
var o1 = new test1();

console.log('==========prototype=============')
console.log( util.inspect(test.prototype));
console.log( util.inspect(test1.prototype));
console.log( util.inspect(test2.prototype));
console.log( util.inspect(test3.prototype));

console.log('==========instance=============')
console.log(util.inspect(new test()));
console.log(util.inspect(new test1()));
var o2 = new test2();
console.log(util.inspect(o2));

console.log("=======");
console.log(util.inspect(o.constructor));
console.log(o1.__proto__ === test1.prototype);

ok (o.prototype === o1.prototype);

function Class1(){    
	this.name = 'class1';   
    this.showTxt = function(){console.log(this.name)}
}

function Class2(){   
 this.name = 'class2';
 // this.showTxt = function(){console.log('showTxt in Class2')}
}
var class2 = new Class2();
Class1.call(class2);
// class2.name = 'test1';

class2.showTxt();
	



