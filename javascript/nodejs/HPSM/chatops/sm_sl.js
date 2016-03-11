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

//var result = '{0} is dead, but {1} is alive!   {0} {2}'.format('ASP', 'ASP.NET','buhaiqing');
//print(result);


function sendRequest(payload) {
    print(payload);
    var endpoint = "https://hooks.slack.com/services/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 ";
    var response = doHTTPRequest('POST', endpoint, [], payload);
}

function buildFieldItem(fields, k, v) {
    var o = {
        "title": "",
        "value": "",
        "short": false
    };
    o["title"] = k;
    o["value"] = v;
    fields.push(o);
}

function buildPlayload(incident_obj) {
    var payload = null;
    var incident_id = incident_obj['number'];
    var request_by = incident_obj['opened.by'];
    var title = incident_obj['brief.description'];
    var description = incident_obj['action'];
    var affected_service = incident_obj['affected.item'];
    var assignment_group = incident_obj['assignment'];
    var assignee = incident_obj['assignee.name'];

    //  only enable when it is a major incident
    if (!!incident_obj['major.incident']) {
        var po = {};
        po['username'] = "sm-bot";
        po["text"] = "A new major incident {0} is created {1}".format(incident_id, request_by);
        var attachments ={};
        po["attachments"] = attachments;
        attachments['color'] = "#36a64f";
        attachments['text'] = title;

        var fields = [];
        buildFieldItem(fields, "Description", description);
        buildFieldItem(fields, "Primary Affected Service", affected_service);
        buildFieldItem(fields, "Assignment Group", assignment_group);
        buildFieldItem(fields, "Assignee", assignee);
        attachments['text'] = fields;
        payload = system.library.JSON.json().stringify(po);
        //         payload =
        //             '{ \
        //         "username": "sm-bot", \
        //         "text":"A new major incident {0} is created {1}", \
        //         "attachments": [ \
        //             { \
        //                 "color": "#36a64f", \
        //                 "text": "{2}", \
        //                 "fields": [ \
        //                     { \
        //                         "title": "Description", \
        //                         "value": "{3}", \
        //                         "short": false \
        //                     }, \
        //                     { \
        //                         "title": "Primary Affected Service", \
        //                         "value": "{4}", \
        //                         "short": false \
        //                     }, \
        //                     { \
        //                         "title": "Assignment Group", \
        //                         "value": "{5}", \
        //                         "short": false \
        //                     }, \
        //                     { \
        //                     "title": "Assignee", \
        //                     "value": "{6}", \
        //                     "short": false \
        //                     } \
        //                 ] \
        //             } \
        //         ] \
        // }'.format(incident_id, request_by, title, description, affected_service, assignment_group, assignee);
    }

    return payload;
}

function synctoSlack(incident_obj) {
    print(!!incident_obj['major.incident']);
    var payload = buildPlayload(incident_obj);
    if (payload != null) {
        sendRequest(payload);
    }
}