// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy addqcid.groofy ${scan_path}

def path = args.length !=0 ? args[0] : "."
def dir = new File(path)
dir.traverse(
             type: groovy.io.FileType.FILES,
             nameFilter:~/.*\.rb/,
             maxDepth:10
             )
             {
                def filename = it.name
                if(filename.contains("QCTP1E"))
                {
                    casename = findQCCaseNumber(filename)
                    
                    insert_qcid(it.canonicalPath,casename)
                }
             }



def findQCCaseNumber(str)
{
    def result = ""
    if (str.contains('QCTP1E')){
        int index = str.indexOf("QCTP1E")
        substr = str.substring(index+ "QCTP1E".length())

        for (int i =0; i < substr.length(); i++){
           if(substr[i] =~ /\d/){
              result += substr[i]
              
           }
        }
    }
    return "QCTP1E${result}"
}
    
// insert metadata :qcid into ruby test case 
// also a placeholder for pd_tag
def insert_qcid(source,casename)
{
    def tmp = source+".tmp"
    def f = new File(tmp)

    def script = new File(source)
    script.eachLine { line->

        if(line =~ /TestFrame/){
            line += 
"""
    def initialize
      @qc_id= "${casename}"
      @pd_tag=[]
    end 
"""
    }
        f << line
        f << "\r\n" // Windows format
    }

    def ant = new AntBuilder();
    ant.copy(file:tmp, tofile:source)
    ant.delete(file:tmp )
}

