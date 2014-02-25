// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy checksvnCommitComments.groovy start-date end-date
//   example: svn log -r {2014-02-02}:{2014-02-10} --xml


// the path you want to scan

import static groovyx.gpars.GParsPool.withPool

if (args.length != 2){
  Usage()
  return
}


startdate = args[0]
enddate = args[1]

projs = [
[project:"In-tool Reporting" , logfile:"intool.log",scan_path:"./NewFeature/Reporting"],
[project:"UCMDB in 940" , logfile:"UCMDB_sm940.log",scan_path:"./Integration/UCMDB/SM940"],
[project:"PD Request" , logfile:"pd-request.log",scan_path:"./ApplicationTests/RequestManagement"]
]
withPool 16,{
  projs.eachParallel{
      def project = it['project']
      def logfile = it['logfile']
      def path = it['scan_path']
      
      File f = new File(logfile)
      PrintWriter pw = new PrintWriter(f)
      
      // svn command to get xml format log entries
      command = "svn log -r {${startdate}}:{${enddate}} --xml  ${path}"
      //println "command is ${command}"
      scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
      text =  command.execute(null , new File(scriptDir)).text
      // parse the xml output and filter out the result set that follow our rules
      def log  = new XmlSlurper().parseText(text)
      def issue_list = log.logentry.grep{
          def msg = it['msg']
          def pattern = 'http://reviewboard.chn.hp.com/reviews/r'
          !msg.toString().contains(pattern)
      }

      issue_list.each {
          pw.printf "Author : %-10s; revision : %7s;\n",it['author'], it.@'revision'

      }
      pw.close()
      if(issue_list.size != 0){
        println "there are issues found on ** ${project} ** project.please check log file **${logfile}** for detail"
      }
  }

}
def Usage(){
  println """
  the arguments length is not correct!!
  
  Usage: groovy checksvnCommitComments.groovy startdate enddate
  """
}
