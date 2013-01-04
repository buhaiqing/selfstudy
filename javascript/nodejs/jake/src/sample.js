
/**
  @author: Bu Hai-Qing( Andy )
  Function: test
*/
function test	(){
	console.log("it is a test");
}

/**
  @class Person class definintion
*/
var Person = function(){
 this.name = "buhaiqing";
 this.city = "shanghai";
};

/**
  @parm VOID
  @return VOID
*/
Person.prototype.sayhello = function(){
  console.log("hello world");
};