import groovy.json.JsonOutput


// Json writer
class jsonOutputTest extends GroovyTestCase{
	void test1(){
		def objs = [
		 a: 1,
		 b: "test"
		]
		String out =  JsonOutput.toJson(objs)
		println JsonOutput.prettyPrint(out)

	}
}