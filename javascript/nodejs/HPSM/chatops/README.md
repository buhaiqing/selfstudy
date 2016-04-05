
## Manifest:

1. smslack.unl -- tailored incident workflow
2. slack.js -- add to SM Script library and ensure JS function named 'lib.slack.synctoSlack' is accessible

## Notice:
1. In order to enable DocEngine URL feaure, ensure parameter 'querySecurity' is set to false in web.xml

                   <init-param>
                     <param-name>querySecurity</param-name>
                     <param-value>false</param-value>
                   </init-param>

2. add addtional configuration sm.ini if your network access is via http proxy. for example:

		JVMOption0:-DproxySet=true 
		
		JVMOption1:-Dhttps.proxyHost=proxy_server 
		
		JVMOption2:-Dhttps.proxyPort=port 
