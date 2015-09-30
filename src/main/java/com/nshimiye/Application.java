package com.nshimiye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nshimiye.akka.AkkaInitializer;
/**
 * 
 * @author mars
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	
    	// create the actor system, actors, and perform any
    	// subscription needed
    	AkkaInitializer init = new AkkaInitializer();
    	init.subscribeActors();
    	
        SpringApplication.run(Application.class, args);
    }

}
