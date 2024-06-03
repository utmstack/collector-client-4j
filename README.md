# Collector client for java
## Description
This is a java client to interact with agent manager collector's methods via gRPC, also can interact with the log auth proxy to authenticate and send logs. 
The agent manager is an intermediate component responsible for all the collector's services and methods. The log auth proxy is other intermediate component 
responsible for the authentication of the logs and forwarding to UTMStack platform.

## Contents
- [Adding client to your project](#adding-client-to-your-project)
    - [Maven dependency](#maven-dependency)
- [Using the client](#using-the-client)
    - [Connecting to the server](#connecting-to-the-server)
    - [Services](#services)
        + [Register a collector](#register-a-collector)
        + [Get collector's configuration](#get-collectors-configuration)
        + [Remove collector](#remove-collector)
        + [Configuration stream](#configuration-stream)
        + [List collectors](#list-collectors)
        + [List collector's hostnames](#list-collectors-hostnames)
    - [Important classes](#important-classes)
        + [AuthResponse](#authresponse)

## Adding client to your project
[Back to Contents](#contents)<br>
In order to use the client in your project, first, you must add the following [dependency](#maven-dependency) below to your pom.xml file (
change the version in the example as you need):

### Maven dependency
~~~
...
<dependencies>
  <dependency>
    <groupId>com.utmstack.grpc.jclient</groupId>
    <artifactId>collector-client-4j</artifactId>
    <version>1.2.5</version>
  </dependency>
</dependencies>
...
~~~

## Using the client
[Back to Contents](#contents)<br>
The client is very simple to use, only needs a valid connection responsible to create the channel between your application and the agent manager, use the methods and handling exceptions, next we will provide 
code examples about how to use the client with each method.

### Connecting to the server
To connect to the agent manager server you must import the following classes: 

~~~
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
~~~

Then you must create a connection specifying the host (String) and port (int) where the agent manager is running, 
also you need to specify one of the [interceptors](#interceptors) using the following code:

~~~
try {
  GrpcConnection con = new GrpcConnection();
  con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());
} catch (GrpcConnectionException e) {
   // Your exception handling here when the channel can't be created
}
~~~
When creating the connection, the specified interceptor will be applied to all calls, all the service's methods 
called will create the needed interceptor based on the parameters, so, when creating the connection you must set the
GrpcEmptyAuthInterceptor as default.

### Services
[Back to Contents](#contents)<br>
The client has some services used to perform ping from a collector, register, remove, list and other actions over a collector.
Also has methods to send logs to UTMStack platform as mentioned before. To use a service you need first to instantiate 
the service class and, then, use the method.
#### Register a collector
This method is used to register a collector in the agent manager.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.RegisterRequest;
import agent.CollectorOuterClass.CollectorModule;
import agent.Common.AuthResponse;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
~~~
<br>**Usage**
~~~
try {
  GrpcConnection con = new GrpcConnection();
  con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());
  
  // Getting the connection key of your UTMStack instance, used as authentication
  String connectionKey = "your-instance's connection key";
  // Creating the request
  RegisterRequest req = RegisterRequest.newBuilder()
                        .setIp("Collector's IP")
                        .setHostname(Collector's hostname)
                        .setVersion("Version of the collector")
                        .setCollector(CollectorModule.AS_400)
                        .build();

// Instantiating the collector service with a connection to the agent manager
CollectorService serv = new CollectorService(con);
AuthResponse response = serv.registerCollector(req, connectionKey);

} catch (GrpcConnectionException e) {
   // Your exception handling here when the channel can't be created
} catch (CollectorServiceGrpcException e) {
   // Your exception handling here when the collector can't be registered
}
~~~
**Note:** When you use non-streaming methods like before, ensure that you close the channel with:
~~~
// Close the connection channel
con.getConnectionChannel().shutdown();
~~~

#### Get collector's configuration
[Back to Contents](#contents)<br>
This method is used to get the collector's configuration. This configuration refers to the credential information
of the devices managed by this collector.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.CollectorModule;
import agent.CollectorOuterClass.CollectorConfig;
import agent.CollectorOuterClass.ConfigRequest;
import agent.Common.AuthResponse;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
~~~
<br>**Usage**
~~~
try {
  GrpcConnection con = new GrpcConnection();
  con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

// Instantiating the collector service with a connection to the agent manager
CollectorService serv = new CollectorService(con);

// Authentication information
String collectorKey = "the collector key";
String collectorId = "the collector's database id"

ConfigRequest req = ConfigRequest.newBuilder().setModule(CollectorModule.AS_400).build();
AuthResponse collector = AuthResponse.newBuilder().setKey(collectorKey)
                    .setId(collectorId).build();
                    
// Requesting configuration
CollectorConfig config = s.requestCollectorConfig(req, collector);

} catch (GrpcConnectionException e) {
   // Your exception handling here when the channel can't be created
} catch (CollectorServiceGrpcException e) {
   // Your exception handling here when the collector's config isn't returned
}
~~~
**Note:** When you use non-streaming methods like before, ensure that you close the channel with:
~~~
// Close the connection channel
con.getConnectionChannel().shutdown();
~~~

#### Remove collector
[Back to Contents](#contents)<br>
This method is used to remove all information of a collector, including its configuration.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.CollectorModule;
import agent.CollectorOuterClass.CollectorDelete;
import agent.Common.AuthResponse;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
~~~
<br>**Usage**
~~~
try {
  GrpcConnection con = new GrpcConnection();
  con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

// Instantiating the collector service with a connection to the agent manager
CollectorService serv = new CollectorService(con);

// Authentication information
String collectorKey = "the collector key";
String collectorId = "the collector's database id"
String deletedBy = "a user name, or IP, or hostname"; // Something that indicates who performed the action

CollectorDelete req = CollectorDelete.newBuilder()
                        .setDeletedBy(deletedBy)
                        .build();
AuthResponse collector = AuthResponse.newBuilder().setKey(collectorKey)
                    .setId(collectorId).build();
                    
// Removing the collector via gRPC
serv.deleteCollector(req, collector);

} catch (GrpcConnectionException e) {
   // Your exception handling here when the channel can't be created
} catch (CollectorServiceGrpcException e) {
   // Your exception handling here when the collector can't be removed
}
~~~
**Note:** When you use non-streaming methods like before, ensure that you close the channel with:
~~~
// Close the connection channel
con.getConnectionChannel().shutdown();
~~~

#### Configuration stream
[Back to Contents](#contents)<br>
This method is a bidirectional stream used to receive collector's configurations asynchronously from the server and send confirmation back when received. 
This method is more complex than the others because needs that you implement you own action when receiving
a configuration, to do that you must create a class that implements the `IExecuteActionOnNext` interface.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.CollectorModule;
import agent.CollectorOuterClass.CollectorMessages;
import agent.CollectorOuterClass.CollectorConfig;
import agent.Common.AuthResponse;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
~~~
<br>**Usage**<br>
Create a class that holds the action to execute when a configuration is received, see an example below.
~~~
public class OnNextConfiguration implements IExecuteActionOnNext {
    
    public static CollectorConfig config;

    public OnNextConfiguration() {}

    @Override
    public void executeOnNext(Object o) {
        if (o instanceof CollectorMessages) {
            if (((CollectorMessages) o).hasConfig() ) {
                // If the message received actually has a configuration set, then do an action
                // for example store the config
                System.out.println("Configuration received");
                config = ((CollectorMessages) o).getConfig();
            }
        }
    }
}
~~~
~~~
try {
    GrpcConnection con = new GrpcConnection();
    con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

    // Instantiating the collector service with a connection to the agent manager
    CollectorService s = new CollectorService(con);
    
    // Authentication information
    String collectorKey = "the collector key";
    String collectorId = "the collector's database id"
    
    // Creating the stream
    StreamObserver<CollectorMessages> collectorStreamObserver;

            // Creating the collector stream and wait for configurations
            final CountDownLatch finishLatch = new CountDownLatch(1);
            while (true) {
                try {
                    // Connecting to the stream
                    collectorStreamObserver = s.getCollectorStreamObserver(new OnNextConfiguration(),collector);

                    // Wait for server response
                    finishLatch.await(10, TimeUnit.SECONDS);
         
                    // Send confirmation back with the same requestId of the configuration received
                    // In the example is assumed that the configuration is not null, you have to add additional
                    // validations to use this example
                    
                    ConfigKnowledge received = ConfigKnowledge.newBuilder()
                                .setAccepted("true").setRequestId(OnNextConfiguration.config.getRequestId()).build();
                    collectorStreamObserver.onNext(CollectorMessages.newBuilder().setResult(received).build());

                } catch (CollectorServiceGrpcException e) {
                    // Log the error if you want and wait for a moment, waiting is an important part
                    // because getCollectorStreamObserver() method will retry internally until receive the config.
                    waitLatch.await(60, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    // Other actions if the thread was interrupted
                }
            }

} catch (GrpcConnectionException e) {
   // Your exception handling here when the channel can't be created
} catch (CollectorServiceGrpcException e) {
   // Your exception handling here
}
~~~

#### List collectors
[Back to Contents](#contents)<br>
This method is used to list the collectors, the request can be filtered and sorted as needed.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.CollectorModule;
import agent.CollectorOuterClass.ListCollectorResponse
import agent.Common.ListRequest;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
~~~
<br>**Usage**<br>
~~~
try {
GrpcConnection con = new GrpcConnection();
con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

// Instantiating the collector service with a connection to the agent manager
CollectorService serv = new CollectorService(con);

// Authentication information
String internalKey = "the UTMStack's internal key";

ListRequest req = ListRequest.newBuilder()
                     .setPageNumber(0)
                     .setPageSize(1000)
                     .setSearchQuery("module.Is=" + CollectorModuleEnum.AS_400)
                     .setSortBy("")
                     .build()

// List collector's information
ListCollectorResponse response = serv.listCollector(req, internalKey);

} catch (GrpcConnectionException e) {
// Your exception handling here when the channel can't be created
} catch (CollectorServiceGrpcException e) {
// Your exception handling here when the collector's can't be listed
}
~~~
**Note:** When you use non-streaming methods like before, ensure that you close the channel with:
~~~
// Close the connection channel
con.getConnectionChannel().shutdown();
~~~

#### List collector's hostnames
[Back to Contents](#contents)<br>
This method is similar than the list collector but only return the hostnames of collectors, the request can be filtered and sorted as needed.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.CollectorModule;
import agent.CollectorOuterClass.CollectorHostnames
import agent.Common.ListRequest;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
~~~
<br>**Usage**<br>
~~~
try {
GrpcConnection con = new GrpcConnection();
con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

// Instantiating the collector service with a connection to the agent manager
CollectorService serv = new CollectorService(con);

// Authentication information
String internalKey = "the UTMStack's internal key";

ListRequest req = ListRequest.newBuilder()
                     .setPageNumber(0)
                     .setPageSize(1000)
                     .setSearchQuery("module.Is=" + CollectorModuleEnum.AS_400)
                     .setSortBy("")
                     .build()

// List collector's hostnames according to the filters
// CollectorHostnames is an array of Strings containing the hostnames
CollectorHostnames response = serv.ListCollectorHostnames(req, internalKey);

} catch (GrpcConnectionException e) {
// Your exception handling here when the channel can't be created
} catch (CollectorServiceGrpcException e) {
// Your exception handling here when the collector's hostnames can't be listed
}
~~~
**Note:** When you use non-streaming methods like before, ensure that you close the channel with:
~~~
// Close the connection channel
con.getConnectionChannel().shutdown();
~~~


### Important classes
This section shows some creational examples of classes not explained in the examples.
#### AuthResponse
Normally you get this class after registering a collector, so, you can persist it for future uses. 
<br>**Imports**

~~~
import agent.Common.AuthResponse;
~~~

<br>**Usage**
~~~
String collectorKey = "the-collector-key";
int collectorId = 1; // The database id of the collector
AuthResponse collector = AuthResponse.newBuilder().setKey("collector key")
                    .setId(collectorId).build();
~~~    
