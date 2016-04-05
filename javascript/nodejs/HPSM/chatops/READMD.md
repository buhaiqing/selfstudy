
manifest:
==========================
1. smslack.unl -- tailored incident workflow
2. slack.js -- add to SM Script library and ensure JS function named 'lib.slack.synctoSlack' is accessible

Notices:
1. In order to enable DocEngine URL feaure, ensure parameter 'querySecurity' is set to false in web.xml
''' web.xml
                   <init-param>
                     <param-name>querySecurity</param-name>
                     <param-value>false</param-value>
                   </init-param>
'''
2. add addtional configuration sm.ini if your network access is via http proxy. for example:
''' sm.ini
JVMOption0:-DproxySet=true
JVMOption1:-Dhttps.proxyHost=web-proxy.rose.hp.com
JVMOption2:-Dhttps.proxyPort=8080

'''