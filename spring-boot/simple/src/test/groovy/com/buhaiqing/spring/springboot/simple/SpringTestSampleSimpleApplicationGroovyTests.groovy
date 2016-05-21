/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.buhaiqing.spring.springboot.simple

import com.buhaiqing.spring.springboot.simple.service.HelloWorldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Shared

/**
 * Tests for {@link SampleSimpleApplication}.
 *
 * @author Dave Syer
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleSimpleApplication.class)
public class SpringTestSampleSimpleApplicationGroovyTests extends spock.lang.Specification {
    @Autowired
    ApplicationContext context

    @Autowired(required = false)
    HelloWorldService helloWorldService;
//	public void testContextLoads() throws Exception {
//	}
    def "testContextLoads"() {
        expect:
        context
        helloWorldService
//        when:
//        helloWorldService = context.getBean(HelloWorldService.class)
//        then:
//        helloWorldService
    }
//
//    def "test2"() {
//        when:
//        def r = context.getBean(HelloWorldService.class)
//        then:
//        r
//    }

}
