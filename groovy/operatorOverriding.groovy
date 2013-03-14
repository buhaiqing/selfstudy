class Person{
	String name
	String address
	int age

	String plus( o ){
		return name+ o.name
	}
}

p1 = new Person()
p1.name="bu "
p2 = new Person()
p2.name ="hai-qing"
println p1+p2 // expected to output: bu hai-qing