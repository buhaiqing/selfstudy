// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy checkNamingConvention.groofy ${scan_path}


// the path you want to scan
def path = args.length !=0 ? args[0] : "."
def checker = new checkNamingConventionUtil (path)
checker.run()

 class checkNamingConventionUtil  {
   def path
   public checkNamingConventionUtil(String _path){
    path = _path
   }

   void run()
   {
    def dir = new File(path)

    File f = new File("checkNamingConvention.log")
    PrintWriter pw = new PrintWriter (f);

    dir.traverse(
             type: groovy.io.FileType.FILES,
             nameFilter:~/.*\.rb/,
             maxDepth:10
             )
     {
        def name = it.name
        if (name.contains("constants") || name.contains("_common") || name.contains("Constant"))
          return;

        if(it.name =~ /.*_?QCTP1E_?.*/)
           return;
        pw.println( it.canonicalPath)
     }

     pw.close()
   } 

 }
