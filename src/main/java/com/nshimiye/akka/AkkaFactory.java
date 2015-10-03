package com.nshimiye.akka;

import akka.actor.ActorSystem;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 
 * @author mars
 *
 */
public class AkkaFactory {

	private static ActorSystem lsystem = null;
	private static ActorSystem rsystem = null;
	
	// configuration information for local akka system
	private static final String LOCAL = "local";
	private static final String LOCAL_NAME = "AKKASystem";
	
	//configuration information for remote akka system
	private static final String REMOTE_NAME = "AKKAREMOTESystem";
	private static final String REMOTE = "docker.remote"; // This can be either remote or docker.remote
	
	
	public static ActorSystem getActorSystem(SystemType type) {
		Config config = ConfigFactory.load();
		switch (type) {
		case LOCAL:
			if (lsystem == null) {
				lsystem = ActorSystem.create(LOCAL_NAME,
						config.getConfig(LOCAL).withFallback(config));
			}
			return lsystem;
		case REMOTE:
			if (rsystem == null) {
			
				System.err.println(config.getConfig(REMOTE).toString());
				
				rsystem = ActorSystem.create(REMOTE_NAME, 
						config.getConfig(REMOTE).withOnlyPath("akka").withFallback(config));
			}
			return rsystem;
		default:
			return null;
		}
	}
}
