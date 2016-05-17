package GuiceSample

import com.google.inject.ImplementedBy


/**
 * Created by buha on 9/22/2015.
 */
@ImplementedBy(TestServiceImpl.class)
interface TestService
{
    void sayHello();
}

class TestServiceImpl implements TestService
{
    int param1;
    int param2;

    public int getParam1()
    {
        return param1;
    }

    public void setParam1(final int param1)
    {
        this.param1 = param1;
    }


    public int getParam2()
    {
        return param2;
    }

    public void setParam2(final int param2)
    {
        this.param2 = param2;
    }


    @Override
    public void sayHello()
    {
        System.out.println("Param1 is " + this.getParam1() + " and Param2 is " + this.getParam2());
    }
}
