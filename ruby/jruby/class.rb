class A
 attr_accessor :name

 # static method
 def self.test
 	 'test'
 end

 def test1
 	 'test1'
 end
end

a = A.new
a.name ="buhaiqing"

puts "name is #{a.name}"
puts a.test1
puts A.test