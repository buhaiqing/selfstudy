// define a class Person
var person = function (){
	return{
	  name : "buhaiqing",
	  address : "shanghai"
  }
}();


// internal function , not expose to other file
function interneralfunc(){
	return "hello world";
}


module.exports.sayhello =  function(){
	return interneralfunc();
};
module.exports.person = person;
