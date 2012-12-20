var pkg = require('../person')

 describe("scenario 1", function() {
        it("test case for module funcion", function() {
            expect(pkg.sayhello()).toEqual("hello world");
        });

        it("test case for module property", function() {
            expect(pkg.person.address).toEqual("shanghai");
        });
    });