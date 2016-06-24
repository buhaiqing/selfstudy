let request = require('request')
let {ok, notEqual, equal} = require('assert');
var data_driven = require('mocha-data-driven')

describe('Auth api test', function () {
    var baseURI;
    beforeEach(function () {
        baseURI = "http://172.17.6.242:8810/dpos-auth-web/s";

    });

    data_driven(
        [
            { mobile: "13900000000", expected: true },
            { mobile: "13900000001", expected: true },
            { mobile: "13900000002", expected: false },
            { mobile: "xx", expected: false },
        ], () => {
            it('check MobileExist api -- {mobile} expect: {expected}', function (ctx, done) {
                request(`${baseURI}/auth/checkmobileexist?mobile=${ctx.mobile}`, function (error, response, body) {
                    ok(!error && response.statusCode == 200)

                    var json = JSON.parse(body);
                    equal(json.success, ctx.expected)
                    done();
                });
            });
        });

    data_driven([
        { login: "13900000000", password: "123456", expected: true },
        { login: "13900000001", password: "123456", expected: true },
        { login: "invalid_user", password: "123456", expected: false },
        { login: "13900000000", password: "invalid_password", expected: false },
        { login: null, password: null, expected: false },
        { login: "", password: "", expected: false }
    ], function () {

        it('{login} login with password {password} -- expected : {expected}', function (ctx, done) {
            request(`${baseURI}/auth/login/auto?login=${ctx.login}&password=${ctx.password}`, function (error, response, body) {
                ok(!error && response.statusCode == 200)

                var json = JSON.parse(body);
                equal(json.success, ctx.expected)
                done();
            });
        });

    });
});
