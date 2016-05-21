/*
 * Copyright 2012-2013 the original author or authors.
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

package com.buhaiqing.spring.springboot.simple;

import com.buhaiqing.spring.springboot.simple.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.buhaiqing.spring.springboot.simple.service.HelloWorldService;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource(value = {"classpath:sampleSpring.xml"})
public class SampleSimpleApplication implements CommandLineRunner {

    // Simple example shows how a command line spring application can execute an
    // injected bean service. Also demonstrates how you can use @Value to inject
    // command line args ('--name=whatever') or application properties

    @Autowired
    private HelloWorldService helloWorldService;

    @Autowired
    private AddressService addressService;

    @Override
    public void run(String... args) {
        System.out.println(this.helloWorldService.getHelloMessage());
        addressService.sayHello();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SampleSimpleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
//		app.setShowBanner(false);
//		app.setLogStartupInfo(false);
        app.run(args);
        // SpringApplication.run(SampleSimpleApplication.class, args);
    }
}
