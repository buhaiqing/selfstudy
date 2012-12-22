// study node `assert` library
var assert = require('assert');
var equal = assert.equal;
var ok = assert.ok;
var notEqual = assert.notEqual;

// equal method
var arr = [1,2,3];
equal(3, arr.length);
equal(3, 3);
var s = "test";
var s1 = "test";
equal(s, "test");
equal(s, s1);

// ok method
ok(1 ==1 );
ok(s == s1);
ok(3 == arr.length);

// notEqual method
notEqual(2, 3);
notEqual(s, " a test" );

// fail method
fail("fail a test");

console.log("All tests are passed");