class propertyMissingTest{
  def propertyMissing(String name){
    	println " property ${name} is not valid property for class propertyMissingTest"
    }
}

def o = new propertyMissingTest()
o.dd