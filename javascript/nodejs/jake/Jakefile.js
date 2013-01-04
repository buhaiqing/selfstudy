var wrench = require('./lib/wrench'),
    path = require('path'),
    fs = require("fs"),
    sys = require('sys');
    

desc("default task");
task('default',['init','google-closure','less','lint','csslint','jsduck'], function(){
   // doLast
   sys.puts("Complete my jobs in jake. Cheers!");
});

desc("do initialization");
task('init',function(){
	
});


desc("minisfy all of the script files in the source directories");
task('google-closure',function(){
  var allfiles = wrench.readdirSyncRecursive('src');
  //var allfiles = jake.readdirR('src');
  // java -jar compiler.jar --js src/sample.js --js_output_file closure-output/sample-compiled.js
  for (var index in allfiles)  {
    var srcpath = path.join('src',allfiles[index]);
    var destpath = path.join('closure-output',path.basename(allfiles[index],'.js')+"-compiled.js");

    var cmd = 'java -jar compiler.jar --js ' + srcpath + ' --js_output_file ' + destpath;
     // sys.puts(cmd);
    jake.exec([cmd]);
  }
  complete();
});

// npm install -g less
desc("use lessc to pre-compile the CSS resoures");
task('less', function(){
  var ex = jake.createExec(['less style-less.less'], {printStdout: true});
  ex.addListener('error', function (msg, code) {
  });
  ex.addListener('stdout', function (msg, code) {
    fs.writeFileSync('style.css', msg);
  });
  ex.run();
  
  complete();
});

// npm install -g jslint
desc("Lints all of the script files in the source directories");
task('lint', function(){
	
    var cmd = 'jshint --jslint-reporter src | tee jshint.xml';
	jake.exec([cmd]);
});

// npm install -g csslint
desc("CSSLints all of the css files in the ss directories");
task('csslint', function(){
	
    var cmd = 'csslint css ';
	jake.exec([cmd],function(){}, {stdout:true});
	// sys.puts( cmd, function(){},{stdout:true} );
});

desc("generate JavaScript API documentation");
task('jsduck', function(){
  
  var cmd = 'jsduck src -o doc --title=Mass-Plaform-UI';
  jake.exec([cmd],function(){}, {stdout:true});
  // sys.puts( cmd, function(){},{stdout:true} );
  complete();
});