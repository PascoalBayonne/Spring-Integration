package com.pascoal.lab1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("/META-INF/spring/si-components.xml");


//        MessageChannel channel = context.getBean("messageChannel",
//                MessageChannel.class);
//
//        Message<String> message1 = MessageBuilder.withPayload(
//                "Hello world - one!").build();
//
//        Message<String> message2 = MessageBuilder.withPayload(
//                "Hello world - two!").build();
//
//        Message<String> message3 = MessageBuilder.withPayload(
//                "Hello world - three!").build();


//        System.out.println("sending message1");
//        channel.send(message1);
//        System.out.println("sending message2");
//        channel.send(message2);
//        System.out.println("sending message3");
//        channel.send(message3);
//        System.out.println("done sending messages");
    }
}
