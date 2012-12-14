var test = function (cfg) {
    // algrithm 1
    var add = function (a, b) {
        return a + b;
    };

    // algrithm 2
    var substract = function (a, b) {
        return a - b;
    };

    // mapping
    var map = {
        "add":add,
        "substract":substract
    };
    return  map[cfg];
};

console.log (map("add")(2,3)); // output : 5