package com.nshimiye.messaging;

import com.nshimiye.akka.AkkaFactory;
import com.nshimiye.akka.SystemType;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * The idea is that an actor requests to be subscribed by the publisher,
 * By sending a message to the publisher actor
 * Then the publisher actor uses its system to subscribe the requesting actor
 * All actors that need to publish event go through this worker
 * @author mars
 * TODO make it usable
 */
public class BrokerWorker extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		
		if(message instanceof ActorRef) {
			
			// PRE-REQUISITE (ActorRef) message == getSender();
			
			getContext().system().eventStream()
			.subscribe(getSender(), Envelope.class);

			getContext().system().eventStream()
			.subscribe(getSender(), String.class);
			
			
			System.out.println("[ BrokerWorker ] done subscribing - " + getSender().toString());
		} else if(message instanceof Envelope) {
            // publish to remote system
            getContext().system()
            		.eventStream().publish((Envelope) message);
            System.out.println("[ BrokerWorker ] done publishing the envelope");
		} else if(message instanceof String) {
            // publish to remote system
            AkkaFactory.getActorSystem(SystemType.REMOTE)
            		.eventStream().publish((String) message);
            System.out.println("[ BrokerWorker ] done publishing the message");
		} else {
			System.err.println("[ BrokerWorker ] unknown message - " + message);
			unhandled(message);
		}

		
	}

}
