package com.nshimiye.cqrs.writer.akka;

import scala.collection.mutable.ArraySeq;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.nshimiye.akka.AkkaFactory;
import com.nshimiye.akka.SystemType;
import com.nshimiye.domain.Database;
import com.nshimiye.domain.Spending;
import com.nshimiye.messaging.Envelope;

/**
 * In charge of factorial calculation 
 * The result is to the parent (Master object)
 * Use of Untypedctor: 
 * “This is due to the fact its quite difficult to implement a Scala PartialFunction in Java 7 and below”
 *     Excerpt From: Duncan K. DeVore. “Reactive Application Development MEAP V03.” iBooks. 
 */
public class WriteWorker extends UntypedActor {

    @Override
    public void onReceive(Object message) {
    	
    	if (message instanceof Spending) {
        
            //1. accomplish the task in hand   
            
            //in this example, we save food and amount to db (list)
    		Spending savedEntity = Database.write( (Spending) message );
            
            //2. After the task is "completely" done,
            // send notification to kafka 
    		// we are using akka's event bus for now
            System.out.println("[ WriteWorker ] done writing to db");
            Envelope envelope = new Envelope("NewEntry", savedEntity);
            
            // look up the broker actor
            // we use the system that owns the actor, so we do not need the full path
            /*
             AkkaFactory.getActorSystem(SystemType.REMOTE).actorSelection("/user/brokerWorker");
             AkkaFactory.getActorSystem(SystemType.REMOTE).actorSelection("akka://AKKAREMOTESystem/user/brokerWorker");
             AkkaFactory.getActorSystem(SystemType.REMOTE).actorSelection("akka.tcp://AKKAREMOTESystem@127.0.0.1:2553/user/brokerWorker");
            
            all three above line give same results
            Note that you cannot do 
            
            `getContext().actorSelection("akka.tcp://AKKAREMOTESystem@127.0.0.1:2553/user/brokerWorker");`
            	because 1. writeActor run in a local context
            		2. writeActor is part of a different system than brokerWorker - TODO test this
            
             */
            
            ActorSelection brokerWorker =
            	AkkaFactory.getActorSystem(SystemType.REMOTE)
            		.actorSelection("/user/brokerWorker");
            
            brokerWorker.tell(envelope, getSelf());
    		brokerWorker.tell("I just added a new entry", getSelf());
    		
    		System.out.println("Actor reference: [brokerWorker] - " + brokerWorker.toString());
   	        System.out.println("Actor path: [brokerWorker] - " + brokerWorker.path());
   	        System.out.println("Actor path address: [brokerWorker] - " + brokerWorker.pathString());
   	        System.out.println("Actor path name: [brokerWorker] - " + brokerWorker.path());
   	        System.out.println("Actor path serial: [brokerWorker] - " + brokerWorker.toSerializationFormat());
            
            // getContext().system().eventStream().publish(envelope);
        
            
        } else
            unhandled(message);
    }

    public static Props createWorker() {
        return Props.create(WriteWorker.class, new ArraySeq<Object>(0));
    }
}