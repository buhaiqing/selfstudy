# require 'test/unit'
# include Test::Unit::Assertions

# class Test1  < Test::Unit::TestCase
# 	def test_case1
#         aseert_equal(1+1, 2)
# 	end 
  
# end 


 


include Java


describe "test suite 1" do
	it "does something" do
	  a = []
	  a.push 1
	  a.size.should == 1
	end
	it "test" do
	    list = []
		list.push 1
		list.push 2
		list.push 3

		list.size.should == 3
	  
	end
  
end