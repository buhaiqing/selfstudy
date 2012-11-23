// how to use Groovy to handle xml file
// sample xml content
// <Persons>
//    <Person>andy</Person>
//    <Person>ada</Person>
// </Persons>
//
// link: http://www.ibm.com/developerworks/cn/java/j-pg05199/

//def xml = new XmlParser().parseText(xmltext)
def persons = new XmlParser().parse("persons.xml")

persons.each {
	println it.text()
}