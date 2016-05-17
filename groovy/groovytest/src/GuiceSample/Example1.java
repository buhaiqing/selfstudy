package GuiceSample;

import com.google.inject.*;
import com.google.inject.name.Names;

import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by buha on 11/21/2015.
 */
public class Example1 {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new DeviceModule());
        Client client = injector.getInstance(Client.class);
        client.displayInfo();
        @Named("laptop") Laptop laptop = (Laptop) injector.getInstance(IDevice.class);
        @Named("laptop") Laptop laptop1 = (Laptop) injector.getInstance(IDevice.class);
        ITest o = injector.getInstance(ITest.class);
        System.out.println(o.say());
        checkNotNull(laptop);
        checkNotNull(laptop1);
    }
}


class TestA implements ITest {
    @Override
    public String say() {
        return "TestA";
    }
}

class TestB implements ITest {
    @Override
    public String say() {
        return "TestB";
    }
}


class Client {
    private IDevice laptop;
    private IDevice mobile;

    @Inject
    public Client(@Named("laptop") IDevice laptop, @Named("mobile") IDevice mobile) {
        this.mobile = mobile;
        this.laptop = laptop;
    }

    public void displayInfo() {
        System.out.println(laptop.getString());
        System.out.println(mobile.getString());

    }

    public String toString() {
        return laptop.getString() + "-- " + mobile.getString();
    }
}

@ImplementedBy(Laptop.class)
interface IDevice {
    String getString();
}

@Singleton
class Laptop implements IDevice {
    private String number;

    public Laptop() {
        this.number = "5433";

    }

    public String getString() {
        return "[Laptop]" + number;
    }
}

class Mobile implements IDevice {
    private String number;

    public Mobile() {
        this.number = "1221";
    }

    public String getString() {

        return "[Mobile]" + this.number;
    }
}

class DeviceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(IDevice.class).annotatedWith(Names.named("laptop")).to(Laptop.class);
        binder.bind(IDevice.class).annotatedWith(Names.named("mobile")).to(Mobile.class);
        binder.bind(ITest.class).toProvider(() -> {
            System.out.println(System.getProperty("user.home"));
            if (System.getProperty("user.home").equals("C:\\Users\\buha")) {
                return new TestA();
            }
            return new TestB();
        });
    }
}

interface IAdd {
    int add(int a, int b);

}

class Add implements IAdd {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

class AddModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(IAdd.class).to(Add.class);

    }
}