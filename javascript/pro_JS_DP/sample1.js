/**
  Function.prototype.method allows you to add new methods to classes. It takes two arguments.
The first is a string to use as the name of the new method, and the second is a function
that will be added under that name.
*/

Function.prototype.method = function(name, fn) {
this.prototype[name] = fn;
return this;
};

var Anim= function(){
}

Anim
.method('start', function(){
 alert("hello");
 return this;
})
.method('stop', function(){
  alert("world");
  return this;
});


var o = new Anim();
o.start().stop();