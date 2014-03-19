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

/**
 * return true if find no noe ship the review ticket.
 */
class ReviewLinkStatusChecker
{
    def link

    public boolean check()
    {
        def html = link.toURL().text
        if (!html.contains('<div class="shipit">'))
        {
            println "No one ship the review ticket at ${link}"
            return true;
        }
        return false;
    }
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
        def issue_list1 = []
        def issue_list2 = []
        def pattern = 'http://reviewboard.chn.hp.com/reviews/r'
        def pattern1 = 'http://reviewboard.chn.hp.com/reviews/r/[0-9]+{4,5}'

        log.logentry.each {
            def msg = it['msg']
            def m = msg=~pattern1

            if (!msg.toString().contains(pattern))
            {
                issue_list1 << it
            }
            else
            {
                m.each { matcher ->
                    def _link = matcher.toString()
                    def test_obj = new ReviewLinkStatusChecker(link: _link)
                    if (test_obj.check())
                    {
                        issue_list2 << it
                    }
                }
            }
        }

        if (issue_list1.size() != 0)
        {
            pw.println("========================================")
            pw.println("change list that do not have review info(Total: ${issue_list1.size()})")
            issue_list1.each {
                pw.printf "Author : %-10s; revision : %7s;\n", it['author'], it.@'revision'

            }
        }
        
        if (issue_list2.size() != 0)
        {
            pw.println("========================================")
            pw.println("change list that no one ship the review ticket(Total: ${issue_list2.size()})")
            issue_list2.each {
                pw.printf "Author : %-10s; revision : %7s;\n", it['author'], it.@'revision'
            }
        }

        pw.close()
        if (issue_list1.size != 0 || issue_list2.size() != 0)
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

        // Native RC
        [project: "Native RC", logfile: "native_calendar_svncommit.log", scan_path: "./NewFeature/Applications/Calendar"]  ,
        [project: "Native RC", logfile: "native_calendar_nonpdpd_svncommit.log", scan_path: "./NewFeature/Applications/Calendar_nonePD"]  ,
        [project: "Native RC", logfile: "native_timeperiodmgmt_svncommit.log", scan_path: "./NewFeature/Applications/TimePeriodMgmt"],

        // HPE
        [project: "HPE", logfile: "hpe_clienttest_svncommit.log", scan_path: "./ClientTests"],
        [project: "HPE", logfile: "hpe_newfeature_client_svncommit.log", scan_path: "./NewFeature/Client"],

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
