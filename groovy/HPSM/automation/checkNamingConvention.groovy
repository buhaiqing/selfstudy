// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy checkNamingConvention.groofy ${scan_path}


// the path you want to scan
//def path = args.length !=0 ? args[0] : "."
//def checker = new checkNamingConventionUtil (path)
//checker.run()
def ant = new AntBuilder()
ant.delete(file: '*.log')

projs = [
        [project: "In-tool Reporting", logfile: "intool_namingconvention.log", scan_path: "./NewFeature/Reporting"],
        [project: "UCMDB in 940", logfile: "UCMDB_sm940_namingconvention.log", scan_path: "./Integration/UCMDB/SM940"],
        [project: "PD Request", logfile: "pd-request_namingconvention.log", scan_path: "./ApplicationTests/RequestManagement"]
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
                    };

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

}
