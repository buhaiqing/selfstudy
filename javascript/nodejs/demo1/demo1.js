var fs = require('fs'),
   sys = require('sys');

 // hello world in Node
 console.log('====================================');
 console.log('**** hello world up and running ****');
 console.log("hello world");
 
 // Node `process` object
 console.log('\n');
 console.log('====================================');
 console.log('**** use process global object ****');
 console.log(process.execPath);
 console.log(process.version);
 console.log(process.platform);
 

// child_process
console.log('\n');
console.log('====================================');
console.log('**** use child_process module ****');
var exec = require('child_process').exec;

cmd = 'ls -lrt'
exec(cmd, function(error, stdio, stderr) { 
  console.log('s: ' + stdio + '\n');
  // console.log('e: ' + stderr + '\n'); 
});


