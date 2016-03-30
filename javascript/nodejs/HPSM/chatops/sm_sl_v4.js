// var endpoint="https://hooks.slack.com/services/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 ";
// var payload='{"text": "<http://www.qq.com|tencent page> ","icon_emoji": ":ghost:", "username": "sm-devops-bot"}';
// var response = doHTTPRequest('POST', endpoint, [], payload);

// First, checks if it isn't implemented yet.
if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined' ? args[number] : match;
        });
    };
}
var WHITE_SPACE = ' ';
var _ = lib.Underscore.require();

var slack = {
    payload: null,
    options: null,
    result: null,
    webhook_url: null,
    pretty_print: function(obj) {
        var output = "";
        for (var property in obj) {
            output += property + ':' + obj[property] + ';';
        }
        return output;
    },
    sendRequest: function(payload) {
        print(payload);
        // for example:
        //    var endpoint = "https://hooks.slack.com/services/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";
        var endpoint = this.webhook_url;
        var response = doHTTPRequest('POST', endpoint, [], this.payload);
    },
    //  array of supported commands
    commands: {
        "create-room": {
            process_mandatoryfields: function(incident_obj, options) {
                var fields = options.mandatory_fields;

                if (-1 !== _.indexOf(fields, 'brief.description')) {
                    this.result.title = incident_obj['brief.description'];
                }

                if (-1 !== _.indexOf(fields, 'number')) {
                    this.result.id = 'major-incident-' + incident_obj['number'];
                    // TODO: generate Doc Engine URL
                }
            },
            proces_invitees: function(incident_obj, options) {
                var fields = options.invitees;
                this.result.invitees = [];

                for (var i in fields) {
                    k = fields[i];
                    if (-1 !== _.indexOf(fields, k)) {
                        var op = lib.operatorUtil.getOperatorByName(incident_obj[k]);
                        if (op !== null) {
                            this.result.invitees.push(op['email']);
                        }
                    }
                }

            },
            process_otherfields: function(incident_obj, options) {
                var fields = options.other_fields;

                for (var i in fields) {
                    var key = fields[i].replace(/\./g, '_');
                    this.result[key] = incident_obj[k];
                }
            },
            process_description: function() {
                // TODO: write description
                this.result.description ="";
            },
            buildPlayload: function(incident_obj, options) {
                this.webhook_url = options['webhook_url'];
                this.options = options;
                var payload_text = '!create-room' + WHITE_SPACE;
                this.result = {};

                // metaInfo
                this.result.metaInfo = {
                    server: system.functions.sysinfo_get("ServerNetAddress"),
                    port: system.functions.sysinfo_get("ServerNetPort")
                };
                _.extend(this.result.metaInfo, options.metaInfo);

                this.process_mandatoryfields(incident_obj, options);
                this.process_otherfields(incident_obj, options);
                this.proces_invitees(incident_obj, options);
                this.process_description();

                // utf-8 encode
                var encoded_str = lib.Base64Encoder.encode(system.library.JSON.json().stringify(this.result));
                payload_text = payload_text + encoded_str;
                this.payload = system.library.JSON.json().stringify({
                    text: payload_text
                });

                return this.payload;
            }
        }
    }
};



// in lib.slack
function synctoSlack(incident_obj, options) {
    // print(!!incident_obj['major.incident']);
    if (!!incident_obj['major.incident']) {
        var payload = slack.commands[options.command].buildPlayload(incident_obj, options);

        if (payload != null) {
            slack.sendRequest(payload);
        }
    }
}