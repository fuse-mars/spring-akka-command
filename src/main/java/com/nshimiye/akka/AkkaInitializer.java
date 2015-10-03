package com.nshimiye.akka;

import org.springframework.beans.factory.annotation.Value;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.nshimiye.cqrs.writer.akka.WriteWorker;
import com.nshimiye.messaging.BrokerWorker;

/**
 * Akka systems have built-in Event Bus called "EventStream"
 * 
 * @author mars Design followed
 *         https://lostechies.com/jimmybogard/files/2012/08/image4.png
 */
public class AkkaInitializer {

	// only for subscription purposes
	private ActorRef writeWorker = null;
	private ActorRef brokerWorker = null;

	private @Value("${akka.actor.provider}") String akkaProvider;
	public void subscribeActors() {
		
		System.out.println("[subscribeActors]  starting subscription process");
		
		// Subscribe the writing worker - BrokerWorker
		if(writeWorker != null){
			System.out.println("[writeWorker - local] initialized but not subscribed - " + writeWorker.path().toSerializationFormat());
		}
		
		if(this.brokerWorker != null) {
			
			System.out.println("[brokerWorker - remote] initialized but not subscribed - " + this.brokerWorker.path().toSerializationFormat());
		
	        System.out.println("Actor reference: [] - " + this.brokerWorker.toString());
	        System.out.println("Actor path: [] - " + this.brokerWorker.path());
	        System.out.println("Actor path address: [] - " + this.brokerWorker.path().address());
	        System.out.println("Actor path name: [] - " + this.brokerWorker.path());
	        System.out.println("Actor path serial: [] - " + this.brokerWorker.path().toSerializationFormat());
			
		}
		
	}

	public AkkaInitializer() {
		this.writeWorker = AkkaFactory.getActorSystem(SystemType.LOCAL).actorOf(
				WriteWorker.createWorker(), "writeWorker");
		
		this.brokerWorker = AkkaFactory.getActorSystem(SystemType.REMOTE).actorOf(
				Props.create(BrokerWorker.class), 
				"brokerWorker");
		
		
		System.out.println("Actor path: [] - " + this.akkaProvider);
	}
}
