def dir = new File('.')
def count = 0
dir.traverse(
         type: groovy.io.FileType.FILES,
         nameFilter:~/.*\.rb/,
         maxDepth:20
         )
{
	if(it.name.contains(/QCTP1E/))
	{
	  count ++
	}

}

println "ruby test cases: ${count}"

