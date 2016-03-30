var _ = lib.Underscore.require();
var WHITE_SPACE = ' ';
// ======================================
//var options={
//    webhook_url:"https://hooks.slack.com/services/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",
//    title: "brief.description",
//    id: "number",
//    invitees:['owner'],
//    other_fields:{
//       Status:'problem.status'
//    },
//    description:"",  // reserved for Slack channel purpose
//    description_format:"${id} and  ${title}" // customized format for Slack channel purpose
//};
// ======================================
var webhook_url = "";
var slack = {
    payload:null,
    options:null,
    result:null,
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
        var endpoint = webhook_url;
        var response = doHTTPRequest('POST', endpoint, [], payload);
    },
    //  array of supported commands
    commands:{
        "create-room":{
            process_mandatoryfields:function (incident_obj, options) {
                var fields = _.omit(options, 'webhook_url', 'invitees', 'other_fields', 'description_format','description');

                var keys = _.keys(fields);
                var values = _.values(fields);
                var maps = _.zip(keys, values);

                for (var i in maps) {
                    var entry = maps[i];
                    this.result[entry[0]] = incident_obj[entry[1]];
                }

                this.result.room_name = 'M' + incident_obj['number'];
                // room name must be 21 characters or less, lower case, and cannot contain spaces and periods
                if (this.result.room_name.length > 21) {
                    this.result.room_name.substring(this.result.room_name.length - 21)
                }
//                in web.xml
//                    <init-param>
//                      <param-name>querySecurity</param-name>
//                      <param-value>false</param-value>
//                    </init-param>

                this.result.docengine_url = lib.urlCreator.getURLFromQuery('probsummary', 'number="' + this.result.id + '"', '');
            },
            process_invitees:function (incident_obj, options) {
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
//              Example: " ${id} and  ${title} "
                var description = this.options.description_format;
                var groups = description.match(/(\$\{.*?\})/g);
                for (var i in groups) {
                    var group = groups[i];
                    var key = group.replace(/[${}]/g, '');
                    description = description.replace(group, this.result[key]);
                }

//                print(description);
                this.result.description = description;
//                _.extend(this.result, {description:description});

            },
            buildPayload:function (incident_obj, options) {
                this.options = options;
                var payload_text = '!create-room' + WHITE_SPACE;
                this.result = {};
                webhook_url = options['webhook_url'];

                // metaInfo
                this.result.metaInfo = {
                    server:system.functions.sysinfo_get("ServerNetAddress"),
                    port:system.functions.sysinfo_get("ServerNetPort")
                };
                _.extend(this.result.metaInfo, options.metaInfo);

                this.process_mandatoryfields(incident_obj, options);
                this.process_otherfields(incident_obj, options);
                this.process_invitees(incident_obj, options);
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