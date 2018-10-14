package com.pascoal.service;

import java.util.concurrent.Future;

public interface PigLatinService {

//   String translator(String incomingMessagePayload);
    //replacing the above code to return Future<>. So in this case we are going to use an Asynchronous gateway

    Future<String> translator(String incomingMessagePayload);
    //Doing so, the application won't block while waiting the response
}
