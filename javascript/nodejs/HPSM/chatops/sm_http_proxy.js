// var endpoint="https://hooks.slack.com/services/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 ";
// var payload='{"text": "<http://www.qq.com|tencent page> ","icon_emoji": ":ghost:", "username": "sm-devops-bot"}';
// var response = doHTTPRequest('POST', endpoint, [], payload);

// First, checks if it isn't implemented yet.
if (!String.prototype.format) {
  String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) { 
      return typeof args[number] != 'undefined'
        ? args[number]
        : match
      ;
    });
  };
}

//var result = '{0} is dead, but {1} is alive!   {0} {2}'.format('ASP', 'ASP.NET','buhaiqing');
//print(result);

var endpoint="https://hooks.slack.com/services/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 ";
var priority = "High"
var incident_id ="IM10001"
var creater="Falcon";
var payload='{ \
	"username": "sm-bot", \
    "attachments": [ \
        { \
            "color": "#36a64f", \
            "text": "A new incident {0} is created by {1}", \
            "fields": [ \
                { \
                    "title": "Priority", \
                    "value": "{2}", \
                    "short": false \
                }, \
				{ \
				"title": "Assignee", \
                "value": "<http://www.bing.com|Bing in sm>", \
                "short": false \
				} \
            ] \
        } \
    ] \
}'.format(incident_id,creater,priority);

var response = doHTTPRequest('POST', endpoint, [], payload);