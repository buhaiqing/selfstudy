package groovyExample

import sun.security.util.AbstractAlgorithmConstraints

/**
 * Created by buha on 10/18/2015.
 */

class ProgrammerMixinTest extends GroovyTestCase {

    private Programer programmer;

    void testBehaviorDependsOnContext() {

        //        Programer.mixin  ProgramerOfficeMixin

        programmer = new Programer(name: "alex")

        programmer.writeCode()
        programmer.test()


    }

    void testInternaltest() {

        programmer = new Programer(name: "alex")
        assertEquals(programmer.internaltest(), "internal test")
    }

    void testBehaviorDependsOnContext2() {

        //        Programer.mixin  ProgramerHomeMixin

        programmer = new Programer(name: "alex")

        programmer.takeCareOfBaby()

    }


    class Todo {
        String name
        int age
        String address
    }

}
