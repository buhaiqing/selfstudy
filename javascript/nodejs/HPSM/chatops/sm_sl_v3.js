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
var  WHITE_SPACE =' ';

function sendRequest(payload) {
    print(payload);
    // please configure your slack api service token
    //var endpoint = "https://hooks.slack.com/services/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 ";
    var endpoint = "https://hooks.slack.com/services/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";
    var response = doHTTPRequest('POST', endpoint, [], payload);
}

function buildPlayload(incident_obj) {
    var sminfo ={
     server: system.functions.sysinfo_get("ServerNetAddress"),
     port: system.functions.sysinfo_get("ServerNetPort")
    }
    
    var payload_text = '!create-room' + WHITE_SPACE;

    var op = lib.operatorUtil.getOperatorByName(incident_obj['assignee.name']);
    var op1 = lib.operatorUtil.getOperatorByName(incident_obj['owner']);
    var invitees = [];
    if(op!=null){
      invitees.push(op['email']);
    }
    if(op1!=null){
      invitees.push(op1['email']);
    }

    var incident_info = {
        metaInfo: sminfo,
        id: incident_obj['number'],
        name: '',
        title: incident_obj['brief.description'],
        description: incident_obj['action']["0"],
        users: invitees,
        affected_service: incident_obj['affected.item'],
        affected_ci: incident_obj['logical.name']
    };
    //print(system.library.JSON.json().stringify(incident_info));
    // utf-8 encode
    incident_info_encoded = lib.Base64Encoder.encode(system.library.JSON.json().stringify(incident_info));
    payload_text = payload_text + incident_info_encoded;
    payload = system.library.JSON.json().stringify({text:payload_text});

    return payload;
}

// in lib.slack
function synctoSlack(incident_obj) {
    // print(!!incident_obj['major.incident']);
    if (!!incident_obj['major.incident']) {
        var payload = buildPlayload(incident_obj);

        if (payload != null) {
            sendRequest(payload);
        }
    }
}