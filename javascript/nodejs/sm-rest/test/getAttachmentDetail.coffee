mocha = require('mocha')
http = require 'http'
chai = require 'chai'
chai.should()
{ok,fail,equal,notEqual} = require 'chai'


describe 'test suite to get Attachment Detail', ->
  beforeEach ()->
    @username = 'falcon'
    @password = ''

    @_auth = 'Basic ' + new Buffer(@username + ':' + @password).toString('base64')
    @_host = '16.186.74.220'
    @_port = '13080'

    @options =
      host: @_host
      port: @_port
      path: '/SM/9/rest/incidents/IM10002/attachments/cid:519b1b40000070c08094fd68'
      method: 'GET'
      headers:
       'accept': 'image/jpeg '
       'accept-language': 'en-US,en;q=0.9'
       'authorization': @_auth
       'user-agent': 'nodejs rest client'
  
  it 'test case1' , ()->
    @req = http.request @options, (res) ->
       console.log res.statusCode
       console.log res.headers
       res.headers['cont-type'].should.equal 'image/jpeg'

       res.on 'data', (chunk)->
    #    console.log('BODY: ' + chunk)
    #    data = JSON.parse(chunk)

    @req.on 'error', (e)->
      console.log('problem with request: ' + e.message)

  afterEach  ()->
    @req.end()
