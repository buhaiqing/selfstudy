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
