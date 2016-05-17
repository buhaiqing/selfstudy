package traits

/**
 * Created by buha on 1/6/2016.
 */
class SmartPhone {
    @Delegate  Phone phone = new Phone()
    @Delegate  Camera camera = new Camera()

    public static void main(String[] args) {
        SmartPhone sp = new SmartPhone()
        sp.takePicture()
        sp.telephone()
    }

}

class Phone {
    public void telephone() {
        println("I am on a call")
    }
}

class Camera {
    public void takePicture() {
        println("taking a picture")
    }
}
