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
        + [Send logs to log-auth-proxy](#send-logs-to-log-auth-proxy)
        + [Upsert collector configuration](#upsert-collector-configuration)
    - [Important classes](#important-classes)
        + [AuthResponse](#authresponse)
    - [Proto files](#proto-files)
        + [Collector](#collector)
        + [Logs](#logs)
        + [Ping](#ping)
        + [Common](#common)

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
the service class and then, use the method. Check the [proto files](#proto-files) to see more about the structure and services
explained in this section.
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
int collectorId = 1; // the collector's database id

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
import agent.Common.DeleteRequest;
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
int collectorId = 1; // the collector's database id
String deletedBy = "a user name, or IP, or hostname"; // Something that indicates who performed the action

DeleteRequest req = DeleteRequest.newBuilder()
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
a configuration, to do that you must create a class that implements the `IExecuteActionOnNext` and `IExecuteActionOnError` interfaces.
This method try to recover itself after server reconnections, so, you don't have to worry about connect
to server do all over again, but we strongly **_recommend to execute the code below in a separated thread_**
to avoid unwanted interruptions.
<br>**Imports**
~~~
import com.utmstack.grpc.service.CollectorService;
import com.utmstack.grpc.exception.CollectorServiceGrpcException;
import agent.CollectorOuterClass.CollectorMessages;
import agent.CollectorOuterClass.CollectorConfig;
import agent.CollectorOuterClass.ConfigKnowledge;
import agent.Common.AuthResponse;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
import com.utmstack.grpc.service.iface.IExecuteActionOnNext;
import com.utmstack.grpc.service.iface.IExecuteActionOnError;

import com.utmstack.grpc.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.grpc.stub.StreamObserver;
~~~
<br>**Usage**<br>
Create a class that holds the action to execute when a configuration is received and other when stream has errors, see an example below.
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
public class OnErrorCollectorMessagesStream implements IExecuteActionOnError {
    private static final Logger logger = LogManager.getLogger(OnErrorCollectorMessagesStream.class);

    @Override
    public void executeOnError(String message) {
        // Log the error message, check if it is server UNAVAILABLE, if true -> try to request configuration on the next execution
        if (message.contains("UNAVAILABLE")) {
            logger.error("Connecting to the collector configuration stream: " + StringUtil.formatError(message));
        } else if (message.contains("ALREADY_EXISTS")) {
            logger.info("Collector configuration stream is connected, reconnection is not needed.");
        } else {
            logger.error("Connecting to the collector configuration stream, server responded with unusual error: " + StringUtil.formatError(message));
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
    int collectorId = 1; // the collector's database id
    AuthResponse collector = AuthResponse.newBuilder().setKey(collectorKey)
                    .setId(collectorId).build();
    
    // Creating the stream
    StreamObserver<CollectorMessages> collectorStreamObserver;

            // Creating the collector stream and wait for configurations
            final CountDownLatch finishLatch = new CountDownLatch(1);
            while (true) {
                try {
                    // Connecting to the stream
                    collectorStreamObserver = s.getCollectorStreamObserver(new OnNextConfiguration(), new OnErrorCollectorMessagesStream(),collector);

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
import agent.CollectorOuterClass.ListCollectorResponse;
import agent.Common.ListRequest;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.GrpcConnectionException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
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
                     .setSearchQuery("module.Is=" + CollectorModule.AS_400.name())
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


#### Send logs to log-auth-proxy
[Back to Contents](#contents)<br>
This method is used to send logs from a collector to [log-auth-proxy](#description). This method is a bidirectional stream used to 
 send logs to server and  receive the acknowledgment when received by server.
This method is more complex than the others because needs that you implement you own action for the acknowledgment, to do 
that you must create a class that implements the `OnNextLogsAck` interface.
We strongly **_recommend to execute the code below in a separated thread_**
to avoid unwanted interruptions.

<br>**Imports**
~~~
import agent.Common;
import com.extractor.as400.grpc.actions.OnNextLogsAck;
import com.extractor.as400.util.ConfigVerification;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.LogMessagingException;
import com.utmstack.grpc.jclient.config.Constants;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
import static com.utmstack.grpc.util.StringUtil.collectorKeyFormat;
import com.utmstack.grpc.service.LogMessagingService;
import io.grpc.stub.StreamObserver;
import plugins.Plugins;
import java.util.UUID;
~~~
<br>**Usage**<br>
~~~
try {
GrpcConnection con = new GrpcConnection();
con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

// Calling the service
LogMessagingService serv = new LogMessagingService(con);


// Authentication information
    String collectorKey = "the collector key";
    int collectorId = 1; // the collector's database id
    AuthResponse collector = AuthResponse.newBuilder().setKey(collectorKey)
                    .setId(collectorId).build();

// Creating the stream used to send logs to server
            StreamObserver<Plugins.Log> logStreamObserver = serv.getLogsStreamObserver(new OnNextLogsAck(),collector);

            // Create log message end through gRPC
            String message = "Original raw message";
            // Setting log's datatype, must match with the datatype value in UTMStack's DB
            String DATA_TYPE = "ibm-as400";
            
                        Plugins.Log log = Plugins.Log.newBuilder()
                                .setId(UUID.randomUUID().toString())
                                .setDataSource("hostname, IP or some datasource identifier")
                                .setDataType(DATA_TYPE)
                                .setRaw(message)
                                .build();
                        logStreamObserver.onNext(log);
            
} catch (Exception e) {
   throw new LogMessagingException(ctx + "Unable to send log -> " + e.getMessage());
}
~~~


#### Upsert collector configuration
[Back to Contents](#contents)<br>
This method is used to send a collector's configuration to the server and get a confirmation back. 
If the configuration for the specified collector, exists, is override, if not is inserted. First, you will see 
in the [proto files](#proto-files) that a CollectorConfig has a list of CollectorConfigGroup and this one, 
has a list of CollectorGroupConfigurations inside. The configurations vary from one collector to another, so, 
you must know what configurations you need for each collector.
<br>**Imports**
~~~
import agent.CollectorOuterClass.ConfigKnowledge;
import agent.CollectorOuterClass.CollectorConfig;
import agent.CollectorOuterClass.CollectorConfigGroup;
import agent.CollectorOuterClass.CollectorGroupConfigurations;
import com.utmstack.grpc.service.PanelCollectorService;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.exception.CollectorConfigurationGrpcException;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;
~~~
<br>**Usage**<br>
~~~
try {
GrpcConnection con = new GrpcConnection();
con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

// Calling the service
PanelCollectorService serv = new PanelCollectorService(con);


// Authentication information
String internalKey = "the UTMStack's internal key";

// Creating the configuration, in this example we show the AS400's configuration
List<CollectorGroupConfigurations> configRows = new ArrayList<>();
            CollectorGroupConfigurations hostname = CollectorGroupConfigurations.newBuilder()
                    .setConfName("Host name")
                    .setConfDescription("Host name of the AS400")
                    .setConfKey("collector.as400.hostname")
                    .setConfValue("The host name of the as400 you're trying to connect to")
                    .setConfDataType("text")
                    .setConfRequired(true)
                    .build();

            CollectorGroupConfigurations username = CollectorGroupConfigurations.newBuilder()
                    .setConfName("User name")
                    .setConfDescription("User name to authenticate into the AS400")
                    .setConfKey("collector.as400.user")
                    .setConfValue("The user name used to connect to the as400")
                    .setConfDataType("text")
                    .setConfRequired(true)
                    .build();

            CollectorGroupConfigurations password = CollectorGroupConfigurations.newBuilder()
                    .setConfName("Password")
                    .setConfDescription("Password of the user used to authenticate into the AS400")
                    .setConfKey("collector.as400.password")
                    .setConfValue("The password of the user used to connect to the as400")
                    .setConfDataType("password")
                    .setConfRequired(true)
                    .build();

            // Adding individual configurations to create the group
            configRows.add(hostname);
            configRows.add(username);
            configRows.add(password);

            // Creating a group with those configurations
            CollectorConfigGroup myFirstAS400Config = CollectorConfigGroup.newBuilder()
                    .setGroupName("AS400Server1")
                    .setGroupDescription("This is my first AS400 server configuration")
                    .addAllConfigurations(configRows)
                    .build();

            // You can add as many groups as you need for the same collector
            List<CollectorConfigGroup> configGroups = new ArrayList<>();
            configGroups.add(myFirstAS400Config);
            
            // Put all together to create the final configuration
            CollectorConfig collectorConfig = CollectorConfig.newBuilder()
                    .setCollectorKey("your collector's key")
                    .addAllGroups(configGroups)
                    .setRequestId("some unique id to identify the request (number, UUID, hash ...)")
                    .build();


            // Call the gRPC upsert config method
            ConfigKnowledge confirmation = serv.insertCollectorConfig(collectorConfig, internalKey);

            // Is not important for now but you can check the response from server
            if(confirmation.getAccepted().equals("true")) {
                // Great !!!
            }

} catch (GrpcConnectionException e) {
// Your exception handling here when the channel can't be created
} catch (LogMessagingException e) {
// Your exception handling here when the configuration wasn't upserted
}
~~~
**Note:** When you use non-streaming methods like before, ensure that you close the channel with:
~~~
// Close the connection channel
con.getConnectionChannel().shutdown();
~~~

#### Ping from collector
[Back to Contents](#contents)<br>
This method is used to send ping requests from a collector to the [agent manager](#description), so, the server knows 
that the collector is alive.
The ping method recovers itself after server reconnections, so, you don't have to worry about connect 
to server and perform ping request again, we just only **_recommend to execute ping in a separated thread_** 
to avoid unwanted interruptions.
<br>**Imports**
~~~
import agent.Common.AuthResponse;
import agent.Common.ConnectorType;
import agent.Ping.PingRequest;
import agent.Ping.PingResponse;
import agent.PingServiceGrpc;
import com.utmstack.grpc.exception.PingException;
import com.utmstack.grpc.connection.GrpcConnection;
import com.utmstack.grpc.service.PingService;
import com.utmstack.grpc.jclient.config.interceptors.impl.GrpcEmptyAuthInterceptor;

import java.util.concurrent.TimeUnit;
~~~
<br>**Usage**<br>
~~~
try {
            GrpcConnection con = new GrpcConnection();
            con.createChannel(AGENT_MANAGER_HOST, AGENT_MANAGER_PORT, new GrpcEmptyAuthInterceptor());

            // Used to set the TimeUnit to execute the ping request
            TimeUnit pingTimeUnit = TimeUnit.SECONDS;
            // Used to set the amount of 'pingTimeUnit' value to execute the ping request
            int pingTimeAmount = 10;
            
            // Creating ping requests of the current collector
            PingRequest pingRequest = PingRequest.newBuilder()
                    .setType(ConnectorType.COLLECTOR)
                    .build();
            
            // Authentication info to let the server know what collector is performing th ping.
            String collectorKey = "the collector key";
            int collectorId = 1; // the collector's database id
            AuthResponse collector = AuthResponse.newBuilder().setKey(collectorKey)
                    .setId(collectorId).build();

            // Creating the ping service and perform continuous ping
            PingService servPing = new PingService(con);
            servPing.ping(pingRequest, collector, pingTimeUnit, pingTimeAmount);

        } catch (GrpcConnectionException | PingException e) {
            // Put your error handlig here
        }
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

### Proto files
This section shows all .proto files with the structure and services used across the client. If you aren't
familiar with gRPC and protocol buffer, you can start [here](https://grpc.io/docs/languages/java/basics/).

#### Collector
This .proto file has all the collector's services and structure definitions.

~~~
syntax = "proto3";

option go_package = "github.com/utmstack/UTMStack/agent-manager/agent";
import "common.proto";

package agent;

service CollectorService {
  rpc RegisterCollector(RegisterRequest) returns (AuthResponse) {}
  rpc DeleteCollector(DeleteRequest) returns (AuthResponse) {}
  rpc ListCollector (ListRequest) returns (ListCollectorResponse) {}
  rpc CollectorStream(stream CollectorMessages) returns (stream CollectorMessages) {}
  rpc GetCollectorConfig (ConfigRequest) returns (CollectorConfig) {}
}

service PanelCollectorService {
  rpc RegisterCollectorConfig(CollectorConfig) returns (ConfigKnowledge) {}
}

enum CollectorModule{
  AS_400 = 0;
}

message RegisterRequest {
  string ip = 1;
  string hostname = 2;
  string version = 3;
  CollectorModule collector = 4;
}

message ListCollectorResponse {
  repeated Collector rows = 1;
  int32 total = 2;
}

message Collector {
  int32 id = 1;
  Status status = 2;
  string collector_key = 3;
  string ip = 4;
  string hostname = 5;
  string version = 6;
  CollectorModule module = 7;
  string last_seen = 8;
}

message CollectorMessages {
  oneof stream_message {
    CollectorConfig config = 1;
    ConfigKnowledge result = 2;
  }
}

message CollectorConfig {
  string collector_id = 1;
  repeated CollectorConfigGroup groups = 2;
  string request_id = 3;
}

message CollectorConfigGroup {
  int32 id = 1;
  string group_name = 2;
  string group_description = 3;
  repeated CollectorGroupConfigurations configurations = 4;
  int32 collector_id = 5;
}

message CollectorGroupConfigurations {
  int32 id = 1;
  int32 group_id = 2;
  string conf_key = 3;
  string conf_value = 4;
  string conf_name = 5;
  string conf_description = 6;
  string conf_data_type = 7;
  bool conf_required = 8;
}

message ConfigKnowledge{
  string accepted = 1;
  string request_id = 2;
}

message ConfigRequest {
  CollectorModule module = 1;
}

~~~

#### Logs
This .proto file has all the services and structure definitions used to send logs to the [log-auth-proxy](#description).

~~~
syntax = "proto3";

package plugins;

option go_package = "github.com/threatwinds/go-sdk/plugins";

import public "google/protobuf/empty.proto";
import public "google/protobuf/struct.proto";

message Ack{
    string lastId = 1;
}

message Log {
    string id = 1;
    string dataType = 2;
    string dataSource = 3;
    string timestamp = 4 [json_name="@timestamp"];
    string tenantId = 5;
    string raw = 6;
}

service Integration{
    rpc ProcessLog(stream Log) returns (stream Ack);
}
~~~

#### Ping
This .proto file has all the services and structure definitions to make ping requests to the 
server, to let the server know that the collector is alive.

~~~
syntax = "proto3";

option go_package = "github.com/utmstack/UTMStack/agent-manager/agent";

package agent;

import "common.proto";

service PingService {
  rpc Ping(stream PingRequest) returns (PingResponse) {}
}

message PingRequest{
  ConnectorType type = 1;
}

message PingResponse {
  string received= 1;
}
~~~

#### Common
This .proto file has common structure definitions used in other .proto files.

~~~
syntax = "proto3";

option go_package = "github.com/utmstack/UTMStack/agent-manager/agent";

package agent;

message ListRequest {
  int32 page_number = 1;
  int32 page_size = 2;
  string search_query = 3;
  string sort_by = 4;
}

enum Status {
  ONLINE = 0;
  OFFLINE = 1;
  UNKNOWN = 2;
}

enum ConnectorType{
  AGENT = 0;
  COLLECTOR = 1;
}

message AuthResponse {
  uint32 id = 1;
  string key = 2;
}

message DeleteRequest {
  string deleted_by = 1;
}

~~~
