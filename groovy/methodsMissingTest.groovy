class methodsMissingTest{
  def methodMissing (String name, args){
    	println " function ${name} is not valid method for class methodsMissingTest"
    }
}

def o = new methodsMissingTest()
o.dd()