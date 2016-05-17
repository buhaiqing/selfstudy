package groovyExample

import groovy.transform.InheritConstructors
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * Created by buha on 10/18/2015.
 */
@Mixin([ProgramerOfficeMixin.class, ProgramerHomeMixin.class])
@groovy.util.logging.Log
@TupleConstructor
class Programer
{

    String name;

    public void test()
    {
        log.info("Hello world")
    }

    protected String internaltest()
    {
        return "internal test";
    }

}

@InheritConstructors
@ToString
class SMProgrammer extends Programer
{

}
