
// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Usage: groovy checksvnCommitComments.groofy ${scan_path} {start-date} {end-date}
//   example: svn log -r {2014-02-02}:{2014-02-10} --xml


// the path you want to scan
def Usage(){
  println """
  the arguments length is not correct!!
  
  Usage: groovy checksvnCommitComments.groofy scan_path startdate enddate
  """
}

if (args.length != 3){
  Usage()
}

path = args[0] 
startdate = args[1]
enddate = args[2]

// svn command to get xml format log entries
command = "svn log -r {${startdate}}:{${enddate}} --xml  ${path}"
text =  command.execute().text
// parse the xml output and filter out the result set that follow our rules
def log  = new XmlSlurper().parseText(text)
def issue_list = log.logentry.grep{
    def msg = it['msg']
    def pattern = 'http://reviewboard.chn.hp.com/reviews/r'
    !msg.toString().contains(pattern)
}

issue_list.each {
    printf "Author : %-10s; revision : %7s;\n",it['author'], it.@'revision'
}
