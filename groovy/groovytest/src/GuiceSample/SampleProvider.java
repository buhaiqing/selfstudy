package GuiceSample;

import com.google.inject.Provider;

/**
 * Created by buha on 3/27/2016.
 */
class SampleProvider implements Provider {
    @Override
    public ITest get() {
        if (System.getProperty("user.home").equals("hello")) {
            new TestA();
        }
        return new TestB();
    }
}