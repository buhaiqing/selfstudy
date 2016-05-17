package traits

/**
 * Created by buha on 11/26/2015.
 */
class testTrait extends Base implements Mytrait  {
    public static void main(String[] args) {
        def obj = new testTrait()
        println(obj.usrname)
    }
}

class Base {

}

trait Mytrait {
     def usrname = "andybu"
}
