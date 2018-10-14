    Gateways are a means of loosely coupling other application components from the SI API or other messaging API.
The gateway serves as a façade to a SI system.

    Gateways are defined by an interface. The gateway can either by synchronous (causing the application to block and wait
for the SI system to respond) or asynchronous (allowing the application to do other work while a long running SI system processes).

Asynchronous Gateway
While the application (Startup.java) is now void of SI knowledge, there is a new problem to
resolve. The gateway, as designed to this point in the lab, is synchronous. That is, the
application makes a request of the gateway (and the SI system under the covers) and
blocks waiting for a return from the gateway. In this simple example, that is not to terribly
bad since the String sent to the translation service is small and the work accomplished by
the translation server and the SI components gets accomplished quickly. Imagine that the
Spring Integration path was much larger with many more tasks (filtering, enriching,
transforming, etc.) to be accomplished in the path. In this case, the application could be
waiting for quite a while. To change this what is needed is a gateway that operates
asynchronously. That is, the application can make a request of the gateway service and
allow the SI system to take as long as it needs to respond without hampering the
application from continuing to work. At a designated time of the application’s choosing, it
should be able check for and get the response from the gateway service. In this step, you
make the PigLatinService asynchronous.
