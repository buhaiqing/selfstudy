package GuiceSample;

import com.google.inject.Provider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Injector;

//class Connect implements Provider<Connect>
//{
//
//  @Override
//  public Object get()
//  {
//    return null;
//  }
//}
class TestServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(TestService.class).to(TestServiceImpl.class);
    }
}

interface TestProvider<T> {
    public T get();
}

interface Gum {
}

class GumProvider implements TestProvider<Gum> {

    @Override
    public Gum get() {
        return null;
    }
}

/**
 * Created by buha on 9/22/2015.
 */
public class Sample1 {

    //  private  TestService service;
    //
    //  @Inject
    //  public void Sample1(TestService service)
    //  {
    ////    Injector injector = Guice.createInjector(new TestServiceModule());
    ////    TestService instance = (TestService) injector.getInstance(TestService.class);
    //    this.service = service;
    //  }
    //
    //  @Test
    //  public void test1()
    //  {
    //
    //    this.service.sayHello();
    //  }
    @Inject
    TestService service;

    private void test(TestService service) {
        service.sayHello();
    }


    public static void main(String[] args) {

        //    Injector injector = Guice.createInjector(new TestServiceModule());
        Injector injector = Guice.createInjector();
        TestService service = injector.getInstance(TestService.class);
        IA a = injector.getInstance(IA.class);
        a.test();
    }
}


@ImplementedBy(Aimpl.class)
interface IA {
    void test();
}

@ImplementedBy(B.class)
interface IB {
    void test();
}

class B implements IB {
    @Override
    public void test() {
        System.out.println("b");
    }
}

@ImplementedBy(C.class)
interface IC {
    void test();
}


class C implements IC {

    @Override
    public void test() {
        System.out.println("C");
    }
}

class Aimpl implements IA {
    private final IB b;
    private final IC c;

    @Inject
    public Aimpl(final IB b, final IC c) {
        this.b = b;
        this.c = c;
    }


    @Override
    public void test() {
        this.b.test();
        this.c.test();
    }
}