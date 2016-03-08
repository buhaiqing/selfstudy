#  npm install node-slack
Slack = require('node-slack')
slack  = new Slack("https://hooks.slack.com/services/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"  )

slack.send({
    text: 'Hi everybody',
    channel: '#self-study',
    username: 'sm-devops-bot',
    icon_url: 'drnick.png' 
})