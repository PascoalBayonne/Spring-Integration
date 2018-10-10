Routers: direct messages to an appropriate message channel typically based on what is in
the payload or header of a message. Routers do not alter the message like a transformer.
Routers don’t remove messages from the system like a filter can. They simply provide
forks in the road of message channels in a Spring Integration (SI) application.

The XPath router is a type of content router as it uses some part of the content of
the message – in this case an element from the XML – to route the message through
its intended path in the system.

There is two kind of Routers:
                            - Content Router: examine the incoming message and use the payload type or value of the Header
                            to determine which channel will receive message.

                            - Recipient Router: this one doesn't examine the content, simple route the message to the channels
