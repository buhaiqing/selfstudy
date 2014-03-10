// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy checkNamingConvention.groofy ${scan_path}


// the path you want to scan
//def path = args.length !=0 ? args[0] : "."
//def checker = new checkNamingConventionUtil (path)
//checker.run()
def ant = new AntBuilder()
ant.delete(file: '*.log')

projs = [
        [project: "Case Exchange", logfile: "case_exchange_namingconvention.log", scan_path: "./NewFeature/Applications/CaseExchange"],
        [project: "UCMDB in 940", logfile: "UCMDB_sm940_namingconvention.log", scan_path: "./Integration/UCMDB/SM940"],
        //[project: "PDFramework in 940", logfile: "PDFramework_namingconvention.log", scan_path: ""],

        //        [project: "SMS", logfile: "sms_namingconvention.log", scan_path: ""],
        [project: "In-tool Reporting", logfile: "intool_namingconvention.log", scan_path: "./NewFeature/Reporting"],
        //        [project: "IDOL", logfile: "idol_namingconvention.log", scan_path: ""],
        //        [project: "Mobility", logfile: "mobiltiy_namingconvention.log", scan_path: ""],
        [project: "PD Request", logfile: "pd-request_namingconvention.log", scan_path: "./ApplicationTests/RequestManagement"],

        // Native RC
        [project: "Native RC", logfile: "native_calendar_namingconvention.log", scan_path: "./NewFeature/Applications/Calendar"],
        [project: "Native RC", logfile: "native_calendar_nonpdpd_namingconvention.log", scan_path: "./NewFeature/Applications/Calendar_nonePD"],
        [project: "Native RC", logfile: "native_timeperiodmgmt_namingconvention.log", scan_path: "./NewFeature/Applications/TimePeriodMgmt"]

]

projs.each {
    path = it['scan_path']
    project = it['project']
    logfile = it['logfile']

    scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
    scriptFile = getClass().protectionDomain.codeSource.location.path

    def checker = new checkNamingConventionUtil(scriptDir + File.separator + path, project, logfile)
    checker.run()
}


class checkNamingConventionUtil
{
    def path
    def findissue
    def logfile
    def project

    public checkNamingConventionUtil(String _path, prj, _logfile)
    {
        path = _path
        findissue = false
        project = prj
        logfile = _logfile
    }

    void run()
    {

        def dir = new File(path)

        File f = new File(logfile)
        PrintWriter pw = new PrintWriter(f);

        dir.traverse(
                type: groovy.io.FileType.FILES,
                nameFilter: ~/.*\.rb/,
                maxDepth: 10
        )
                {
                    def name = it.name

                    if (name.contains("constants") || name.contains("_common") ||
                            name.contains("Constant"))
                    {
                        return
                    }
                    else if (escape_some_special_cases(it))
                    {
                        return
                    }
                    else
                    {
                        if (it.canonicalPath.tokenize(File.separator).any() { str ->
                            str == 'lib'
                        })
                        {
                            return
                        }

                    }

                    if (it.name=~/.*_?QCTP1E_?.*/)
                    {
                        return
                    };
                    pw.println(it.canonicalPath)
                    findissue = true
                }

        pw.close()

        if (findissue)
        {
            println "there are issue found in this scan in project --${project} --. Please check with the log for detail."
        }
    }

    boolean escape_some_special_cases(file)
    {
        def name = file.name

        // for Native RC
        if (name == "CalendarAPITest.rb" ||
                name == "CalendarFrameTest.rb")
        {
            return true
        }

        return false
    }

}
