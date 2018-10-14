package com.pascoal;

import com.pascoal.service.PigLatinService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "/META-INF/spring/si-components.xml");

//        MessageChannel channel = context.getBean("requestChannel", MessageChannel.class);
//        Message<String> message = MessageBuilder.withPayload("Hello brave new world").build();
//        channel.send(message);

        PigLatinService pigLatinService = context.getBean("latinService", PigLatinService.class);
        Future<String> futureTranslator = pigLatinService.translator("Hello Asynchronous SI Gateway");

        // do more work here in a real application
        System.out.println("wait and bleed not block!");

        String messageOutput = "";
        try {
            // In a real application, the application is free to go about doing other work and is not
            //blocked when the call to translate( ) is made. The call to get( ) takes parameters to inform the
            //system how long to wait, if necessary, to retrieve the results from the asynchronous process –
            //which in this case is the return from the SI components.
             messageOutput = futureTranslator.get(5000, TimeUnit.SECONDS);



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(messageOutput);

        context.close();
    }
}
