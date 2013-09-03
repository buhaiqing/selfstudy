
class ConfigSlurperTest extends GroovyTestCase{
	void test1(){
      def cfg = new ConfigSlurper().parse(new File("config.groovy").toURL())

      assertSame( cfg.prop1, "Hello" )
      assertEquals(cfg.prop2, 12)
	}

}