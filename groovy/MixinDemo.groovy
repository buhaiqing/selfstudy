class Class1 {
 def method1(){
   println "method1"
 }
}

class Class2 {
 def method2(){
   println "method2"
 }
}

@Mixin([Class1,Class2])
class Good{
}


obj = new Good()
obj?.method1()
obj?.method2()

// OR	
class Good1{}
Good1.mixin Class2

obj1 = new Good1()
obj1.metaClass.mixin Class1

obj1.method1()
obj1.method2()
