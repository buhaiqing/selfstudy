// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy checksvnCommitComments.groovy start-date end-date
//   example: svn log -r {2014-02-02}:{2014-02-10} --xml


// the path you want to scan

import static groovyx.gpars.GParsPool.withPool

if (args.length != 2)
{
    Usage()
    return
}

class SvnCommitChecker
{
    def project, logfile, path
    def startdate, enddate

    public void check()
    {
        File f = new File(logfile)
        PrintWriter pw = new PrintWriter(f)

        // svn command to get xml format log entries
        def command = "svn log -r {${startdate}}:{${enddate}} --xml  ${path}"
        //println "command is ${command}"

        def text = command.execute().text
        //println text
        // parse the xml output and filter out the result set that follow our rules
        def log = new XmlSlurper().parseText(text)
        def issue_list = log.logentry.grep {
            def msg = it['msg']
            def pattern = 'http://reviewboard.chn.hp.com/reviews/r'
            !msg.toString().contains(pattern)
        }

        issue_list.each {
            pw.printf "Author : %-10s; revision : %7s;\n", it['author'], it.@'revision'

        }
        pw.close()
        if (issue_list.size != 0)
        {
            println "there are issues found on ** ${project} ** project.please check log file **${logfile}** for detail"
        }
    }
}

startdate = args[0]
enddate = args[1]

projs = [
        [project: "Case Exchange", logfile: "case_exchange_svncommit.log", scan_path: "NewFeature/Applications/CaseExchange"],
        [project: "UCMDB in 940", logfile: "UCMDB_sm940_svncommit.log", scan_path: "Integration/UCMDB/SM940"],
        //[project: "PDFramework in 940", logfile: "PDFramework_svncommit.log", scan_path: ""],
        //        [project: "NativeRC", logfile: "nativeRC_svncommit.log", scan_path: ""],
        //        [project: "SMS", logfile: "sms_svncommit.log", scan_path: ""],
        [project: "In-tool Reporting", logfile: "intool_svncommit.log", scan_path: "NewFeature/Reporting"],
        //        [project: "IDOL", logfile: "idol_svncommit.log", scan_path: ""],
        //        [project: "Mobility", logfile: "mobiltiy_svncommit.log", scan_path: ""],
        [project: "PD Request", logfile: "pd-request_svncommit.log", scan_path: "ApplicationTests/RequestManagement"]

]

withPool 16, {
    projs.eachParallel {
        def checker = new SvnCommitChecker(startdate: startdate, enddate: enddate)
        checker.project = it['project']
        checker.logfile = it['logfile']
        checker.path = it['scan_path']

        checker.check()
    }
}


def Usage()
{
    println """
  the arguments length is not correct!!
  
  Usage: groovy checksvnCommitComments.groovy startdate enddate
  """
}
