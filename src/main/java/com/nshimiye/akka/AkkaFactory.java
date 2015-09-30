package com.nshimiye.akka;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

/**
 *
 */
public class AkkaFactory {

	private static ActorSystem lsystem = null;
	private static ActorSystem rsystem = null;

	public static ActorSystem getActorSystem(SystemType type) {

		switch (type) {
		case LOCAL:
			if (lsystem == null) {
				lsystem = ActorSystem.create("AKKASystem");
			}
			return lsystem;
		case REMOTE:
			if (rsystem == null) {
				Config config = ConfigFactory.parseFile(new File(
						"src/main/akka/application.conf"));
				System.err.println(config.toString());
				rsystem = ActorSystem.create("AKKAREMOTESystem", config);
			}
			return rsystem;
		default:
			return null;
		}
	}
}