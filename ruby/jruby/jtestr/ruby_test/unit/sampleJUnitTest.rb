require 'java'
java_import 'junit.framework.TestCase'


class SampleJUnitTest < TestCase
  def test_one
     assertEquals(1+1, 2)
  end	
end
