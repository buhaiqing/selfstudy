/**
 Mixin Class
*/
function augment(receivingClass, givingClass) {
	if(arguments[2]) { // Only give certain methods.
		for(var i = 2, len = arguments.length; i < len; i++) {
			receivingClass.prototype[arguments[i]] = givingClass.prototype[arguments[i]];
		}
	}
	else { // Give all methods.
		for(methodName in givingClass.prototype) {
			if(!receivingClass.prototype[methodName]) {
				receivingClass.prototype[methodName] = givingClass.prototype[methodName];
			}
		}
	}
}

var Mixin = function (){}
Mixin.prototype = {
	method1: function(){
		return "method1 in Mixin"
	}
	method2: function(){
		return "method2 in Mixin"
	}
}

function ClassA (n){
	this.name = n
}
ClassA.prototype.method1 = function(){
	return  this.name;
}

augment(ClassA,Mixin);

// test case
// var o = new ClassA("haiqing");
// console.log(o.method1()); //method1 in Mixin
// console.log(o.method2()); // buhaiqing