// var endpoint="https://hooks.slack.com/services/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 ";
// var payload='{"text": "<http://www.qq.com|tencent page> ","icon_emoji": ":ghost:", "username": "sm-devops-bot"}';
// var response = doHTTPRequest('POST', endpoint, [], payload);

// First, checks if it isn't implemented yet.
if (!String.prototype.format) {
    String.prototype.format = function () {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function (match, number) {
            return typeof args[number] != 'undefined' ? args[number] : match;
        });
    };
}
var WHITE_SPACE = ' ';
var _ = lib.Underscore.require();
// ======================================
//var options={
//    webhook_url:"https://hooks.slack.com/services/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",
//    title: "brief.description",
//    id: "number",
//    invitees:['owner'],
//    other_fields:{
//
//    },
//    description_format:""
//};
// ======================================
var slack = {
    payload:null,
    options:null,
    result:null,
    webhook_url:null,
    pretty_print:function (obj) {
        var output = "";
        for (var property in obj) {
            output += property + ':' + obj[property] + ';';
        }
        return output;
    },
    sendRequest:function (payload) {
        print(payload);
        // for example:
        //    var endpoint = "https://hooks.slack.com/services/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";
        var endpoint = this.webhook_url;
        var response = doHTTPRequest('POST', endpoint, [], this.payload);
    },
    //  array of supported commands
    commands:{
        "create-room":{
            process_mandatoryfields:function (incident_obj, options) {
                var fields = _.omit(options, 'webhook_url', 'invitees', 'other_fields', 'description');

                var keys = _.keys(fields);
                var values = _.values(fields);
                var maps = _.zip(keys, values);

                for (var i in maps) {
                    var entry = maps[i];
                    this.result[entry[0]] = incident_obj[entry[1]];
                }

                this.result.room_name = 'M' + incident_obj['number'];
                if (this.result.room_name.length > 21) {
                    this.result.room_name.substring(this.result.room_name.length - 21)
                }
                this.result.docengine_url = lib.urlCreator.getURLFromQuery('probsummary', 'number="' + this.result.id + '"', '');
            },
            proces_invitees:function (incident_obj, options) {
                this.result.users = options.invitees;
//                var fields = options.invitees;
//                this.result.invitees = [];
//
//                for (var i in fields) {
//                    k = fields[i];
//                    if (-1 !== _.indexOf(fields, k)) {
//                        var op = lib.operatorUtil.getOperatorByName(incident_obj[k]);
//                        if (op !== null) {
//                            this.result.invitees.push(op['email']);
//                        }
//                    }
//                }

            },
            process_otherfields:function (incident_obj, options) {
                var fields = options.other_fields;
                var keys = _.keys(fields);
                var values = _.values(fields);
                var maps = _.zip(keys, values);

                for (var i in maps) {
                    var entry = maps[i];
                    this.result[entry[0]] = incident_obj[entry[1]];
                }
            },
            process_description:function () {
                // TODO: replace description format
//              Example: " ${number} and  ${brief.description} "
                this.result.description = this.options.description_format;
                var groups = this.result.description.match(/(\$\{.*?\})/g);
                for (var i in groups) {
                    var group = groups[i];
                    var key = group.replace(/[${}]/g, '');
                    this.result.description = this.result.description.replace(group, this.result[key]);
                }

            },
            buildPayload:function (incident_obj, options) {
                this.webhook_url = options['webhook_url'];
                this.options = options;
                var payload_text = '!create-room' + WHITE_SPACE;
                this.result = {};

                // metaInfo
                this.result.metaInfo = {
                    server:system.functions.sysinfo_get("ServerNetAddress"),
                    port:system.functions.sysinfo_get("ServerNetPort")
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
                    text:payload_text
                });

                return this.payload;
            }
        }
    }
};


// in lib.slack
function synctoSlack(incident_obj, options, command) {
    // print(!!incident_obj['major.incident']);
    if (!!incident_obj['major.incident']) {
        var payload = slack.commands[command].buildPayload(incident_obj, options);

        if (payload != null) {
            slack.sendRequest(payload);
        }
    }
}