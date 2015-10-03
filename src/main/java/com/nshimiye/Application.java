package com.nshimiye;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.nshimiye.akka.AkkaInitializer;
/**
 * 
 * @author mars
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	
    	// create the actor system, actors, and perform any
    	// subscription needed
    	AkkaInitializer init = new AkkaInitializer();
    	init.subscribeActors();
    	
        
    }
    
	@Bean
	public PropertyPlaceholderConfigurer propertyConfigurer()
			throws IOException {
		
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
		props.setLocations(new Resource[] { new ClassPathResource(
				"akka/application.remote.conf") });
		return props;
	}

}
