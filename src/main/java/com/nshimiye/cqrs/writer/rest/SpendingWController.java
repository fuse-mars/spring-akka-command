package com.nshimiye.cqrs.writer.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;

import com.nshimiye.akka.AkkaFactory;
import com.nshimiye.akka.SystemType;
import com.nshimiye.domain.Spending;

@RestController
public class SpendingWController {

    private final AtomicLong counter = new AtomicLong();

    
    @RequestMapping("api/expenses/write")
    public Map<String, String> recordExpense(
        @RequestParam(value="name", defaultValue="Burrito from Chipotle") String name,
        @RequestParam(value="amount") double amount
        ) {
    	
        System.out.println("write route called");


        long count = counter.incrementAndGet();
        
        
        ActorSelection writeWorker = AkkaFactory.getActorSystem(SystemType.LOCAL)
        		.actorSelection("akka://AKKASystem/user/writeWorker");
        
        // send a command to save this information
        //non-blocking call uses "tell"
        writeWorker.tell(
            new Spending(count, name, amount),
            ActorRef.noSender()
        );

        //return a message showing the user what is being done
        Map<String, String> response = new HashMap<String, String>();
        response.put("id", Long.toString(count));
        response.put("message", "Entry has been received and is being saved");
        return response;
    }
}