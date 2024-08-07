package agent;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.65.1)",
    comments = "Source: collector.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CollectorServiceGrpc {

  private CollectorServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "agent.CollectorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.RegisterRequest,
      agent.Common.AuthResponse> getRegisterCollectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterCollector",
      requestType = agent.CollectorOuterClass.RegisterRequest.class,
      responseType = agent.Common.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.RegisterRequest,
      agent.Common.AuthResponse> getRegisterCollectorMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.RegisterRequest, agent.Common.AuthResponse> getRegisterCollectorMethod;
    if ((getRegisterCollectorMethod = CollectorServiceGrpc.getRegisterCollectorMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getRegisterCollectorMethod = CollectorServiceGrpc.getRegisterCollectorMethod) == null) {
          CollectorServiceGrpc.getRegisterCollectorMethod = getRegisterCollectorMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.RegisterRequest, agent.Common.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterCollector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.Common.AuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("RegisterCollector"))
              .build();
        }
      }
    }
    return getRegisterCollectorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorDelete,
      agent.Common.AuthResponse> getDeleteCollectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteCollector",
      requestType = agent.CollectorOuterClass.CollectorDelete.class,
      responseType = agent.Common.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorDelete,
      agent.Common.AuthResponse> getDeleteCollectorMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorDelete, agent.Common.AuthResponse> getDeleteCollectorMethod;
    if ((getDeleteCollectorMethod = CollectorServiceGrpc.getDeleteCollectorMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getDeleteCollectorMethod = CollectorServiceGrpc.getDeleteCollectorMethod) == null) {
          CollectorServiceGrpc.getDeleteCollectorMethod = getDeleteCollectorMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.CollectorDelete, agent.Common.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteCollector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorDelete.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.Common.AuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("DeleteCollector"))
              .build();
        }
      }
    }
    return getDeleteCollectorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<agent.Common.ListRequest,
      agent.CollectorOuterClass.ListCollectorResponse> getListCollectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListCollector",
      requestType = agent.Common.ListRequest.class,
      responseType = agent.CollectorOuterClass.ListCollectorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.Common.ListRequest,
      agent.CollectorOuterClass.ListCollectorResponse> getListCollectorMethod() {
    io.grpc.MethodDescriptor<agent.Common.ListRequest, agent.CollectorOuterClass.ListCollectorResponse> getListCollectorMethod;
    if ((getListCollectorMethod = CollectorServiceGrpc.getListCollectorMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getListCollectorMethod = CollectorServiceGrpc.getListCollectorMethod) == null) {
          CollectorServiceGrpc.getListCollectorMethod = getListCollectorMethod =
              io.grpc.MethodDescriptor.<agent.Common.ListRequest, agent.CollectorOuterClass.ListCollectorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListCollector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.Common.ListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.ListCollectorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("ListCollector"))
              .build();
        }
      }
    }
    return getListCollectorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorMessages,
      agent.CollectorOuterClass.CollectorMessages> getCollectorStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CollectorStream",
      requestType = agent.CollectorOuterClass.CollectorMessages.class,
      responseType = agent.CollectorOuterClass.CollectorMessages.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorMessages,
      agent.CollectorOuterClass.CollectorMessages> getCollectorStreamMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorMessages, agent.CollectorOuterClass.CollectorMessages> getCollectorStreamMethod;
    if ((getCollectorStreamMethod = CollectorServiceGrpc.getCollectorStreamMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getCollectorStreamMethod = CollectorServiceGrpc.getCollectorStreamMethod) == null) {
          CollectorServiceGrpc.getCollectorStreamMethod = getCollectorStreamMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.CollectorMessages, agent.CollectorOuterClass.CollectorMessages>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CollectorStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorMessages.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorMessages.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("CollectorStream"))
              .build();
        }
      }
    }
    return getCollectorStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<agent.Common.ListRequest,
      agent.CollectorOuterClass.CollectorHostnames> getListCollectorHostnamesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListCollectorHostnames",
      requestType = agent.Common.ListRequest.class,
      responseType = agent.CollectorOuterClass.CollectorHostnames.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.Common.ListRequest,
      agent.CollectorOuterClass.CollectorHostnames> getListCollectorHostnamesMethod() {
    io.grpc.MethodDescriptor<agent.Common.ListRequest, agent.CollectorOuterClass.CollectorHostnames> getListCollectorHostnamesMethod;
    if ((getListCollectorHostnamesMethod = CollectorServiceGrpc.getListCollectorHostnamesMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getListCollectorHostnamesMethod = CollectorServiceGrpc.getListCollectorHostnamesMethod) == null) {
          CollectorServiceGrpc.getListCollectorHostnamesMethod = getListCollectorHostnamesMethod =
              io.grpc.MethodDescriptor.<agent.Common.ListRequest, agent.CollectorOuterClass.CollectorHostnames>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListCollectorHostnames"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.Common.ListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorHostnames.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("ListCollectorHostnames"))
              .build();
        }
      }
    }
    return getListCollectorHostnamesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.FilterByHostAndModule,
      agent.CollectorOuterClass.ListCollectorResponse> getGetCollectorsByHostnameAndModuleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCollectorsByHostnameAndModule",
      requestType = agent.CollectorOuterClass.FilterByHostAndModule.class,
      responseType = agent.CollectorOuterClass.ListCollectorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.FilterByHostAndModule,
      agent.CollectorOuterClass.ListCollectorResponse> getGetCollectorsByHostnameAndModuleMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.FilterByHostAndModule, agent.CollectorOuterClass.ListCollectorResponse> getGetCollectorsByHostnameAndModuleMethod;
    if ((getGetCollectorsByHostnameAndModuleMethod = CollectorServiceGrpc.getGetCollectorsByHostnameAndModuleMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getGetCollectorsByHostnameAndModuleMethod = CollectorServiceGrpc.getGetCollectorsByHostnameAndModuleMethod) == null) {
          CollectorServiceGrpc.getGetCollectorsByHostnameAndModuleMethod = getGetCollectorsByHostnameAndModuleMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.FilterByHostAndModule, agent.CollectorOuterClass.ListCollectorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCollectorsByHostnameAndModule"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.FilterByHostAndModule.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.ListCollectorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("GetCollectorsByHostnameAndModule"))
              .build();
        }
      }
    }
    return getGetCollectorsByHostnameAndModuleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.ConfigRequest,
      agent.CollectorOuterClass.CollectorConfig> getGetCollectorConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCollectorConfig",
      requestType = agent.CollectorOuterClass.ConfigRequest.class,
      responseType = agent.CollectorOuterClass.CollectorConfig.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.ConfigRequest,
      agent.CollectorOuterClass.CollectorConfig> getGetCollectorConfigMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.ConfigRequest, agent.CollectorOuterClass.CollectorConfig> getGetCollectorConfigMethod;
    if ((getGetCollectorConfigMethod = CollectorServiceGrpc.getGetCollectorConfigMethod) == null) {
      synchronized (CollectorServiceGrpc.class) {
        if ((getGetCollectorConfigMethod = CollectorServiceGrpc.getGetCollectorConfigMethod) == null) {
          CollectorServiceGrpc.getGetCollectorConfigMethod = getGetCollectorConfigMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.ConfigRequest, agent.CollectorOuterClass.CollectorConfig>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCollectorConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.ConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorConfig.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorServiceMethodDescriptorSupplier("GetCollectorConfig"))
              .build();
        }
      }
    }
    return getGetCollectorConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CollectorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorServiceStub>() {
        @java.lang.Override
        public CollectorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorServiceStub(channel, callOptions);
        }
      };
    return CollectorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CollectorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorServiceBlockingStub>() {
        @java.lang.Override
        public CollectorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorServiceBlockingStub(channel, callOptions);
        }
      };
    return CollectorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CollectorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorServiceFutureStub>() {
        @java.lang.Override
        public CollectorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorServiceFutureStub(channel, callOptions);
        }
      };
    return CollectorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registerCollector(agent.CollectorOuterClass.RegisterRequest request,
        io.grpc.stub.StreamObserver<agent.Common.AuthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterCollectorMethod(), responseObserver);
    }

    /**
     */
    default void deleteCollector(agent.CollectorOuterClass.CollectorDelete request,
        io.grpc.stub.StreamObserver<agent.Common.AuthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteCollectorMethod(), responseObserver);
    }

    /**
     */
    default void listCollector(agent.Common.ListRequest request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ListCollectorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListCollectorMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorMessages> collectorStream(
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorMessages> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCollectorStreamMethod(), responseObserver);
    }

    /**
     */
    default void listCollectorHostnames(agent.Common.ListRequest request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorHostnames> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListCollectorHostnamesMethod(), responseObserver);
    }

    /**
     */
    default void getCollectorsByHostnameAndModule(agent.CollectorOuterClass.FilterByHostAndModule request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ListCollectorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCollectorsByHostnameAndModuleMethod(), responseObserver);
    }

    /**
     */
    default void getCollectorConfig(agent.CollectorOuterClass.ConfigRequest request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorConfig> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCollectorConfigMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CollectorService.
   */
  public static abstract class CollectorServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CollectorServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CollectorService.
   */
  public static final class CollectorServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CollectorServiceStub> {
    private CollectorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerCollector(agent.CollectorOuterClass.RegisterRequest request,
        io.grpc.stub.StreamObserver<agent.Common.AuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterCollectorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteCollector(agent.CollectorOuterClass.CollectorDelete request,
        io.grpc.stub.StreamObserver<agent.Common.AuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteCollectorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listCollector(agent.Common.ListRequest request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ListCollectorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListCollectorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorMessages> collectorStream(
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorMessages> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCollectorStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void listCollectorHostnames(agent.Common.ListRequest request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorHostnames> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListCollectorHostnamesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCollectorsByHostnameAndModule(agent.CollectorOuterClass.FilterByHostAndModule request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ListCollectorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCollectorsByHostnameAndModuleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCollectorConfig(agent.CollectorOuterClass.ConfigRequest request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorConfig> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCollectorConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CollectorService.
   */
  public static final class CollectorServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CollectorServiceBlockingStub> {
    private CollectorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public agent.Common.AuthResponse registerCollector(agent.CollectorOuterClass.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterCollectorMethod(), getCallOptions(), request);
    }

    /**
     */
    public agent.Common.AuthResponse deleteCollector(agent.CollectorOuterClass.CollectorDelete request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteCollectorMethod(), getCallOptions(), request);
    }

    /**
     */
    public agent.CollectorOuterClass.ListCollectorResponse listCollector(agent.Common.ListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListCollectorMethod(), getCallOptions(), request);
    }

    /**
     */
    public agent.CollectorOuterClass.CollectorHostnames listCollectorHostnames(agent.Common.ListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListCollectorHostnamesMethod(), getCallOptions(), request);
    }

    /**
     */
    public agent.CollectorOuterClass.ListCollectorResponse getCollectorsByHostnameAndModule(agent.CollectorOuterClass.FilterByHostAndModule request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCollectorsByHostnameAndModuleMethod(), getCallOptions(), request);
    }

    /**
     */
    public agent.CollectorOuterClass.CollectorConfig getCollectorConfig(agent.CollectorOuterClass.ConfigRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCollectorConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CollectorService.
   */
  public static final class CollectorServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CollectorServiceFutureStub> {
    private CollectorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.Common.AuthResponse> registerCollector(
        agent.CollectorOuterClass.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterCollectorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.Common.AuthResponse> deleteCollector(
        agent.CollectorOuterClass.CollectorDelete request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteCollectorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.CollectorOuterClass.ListCollectorResponse> listCollector(
        agent.Common.ListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListCollectorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.CollectorOuterClass.CollectorHostnames> listCollectorHostnames(
        agent.Common.ListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListCollectorHostnamesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.CollectorOuterClass.ListCollectorResponse> getCollectorsByHostnameAndModule(
        agent.CollectorOuterClass.FilterByHostAndModule request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCollectorsByHostnameAndModuleMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.CollectorOuterClass.CollectorConfig> getCollectorConfig(
        agent.CollectorOuterClass.ConfigRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCollectorConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_COLLECTOR = 0;
  private static final int METHODID_DELETE_COLLECTOR = 1;
  private static final int METHODID_LIST_COLLECTOR = 2;
  private static final int METHODID_LIST_COLLECTOR_HOSTNAMES = 3;
  private static final int METHODID_GET_COLLECTORS_BY_HOSTNAME_AND_MODULE = 4;
  private static final int METHODID_GET_COLLECTOR_CONFIG = 5;
  private static final int METHODID_COLLECTOR_STREAM = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_COLLECTOR:
          serviceImpl.registerCollector((agent.CollectorOuterClass.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<agent.Common.AuthResponse>) responseObserver);
          break;
        case METHODID_DELETE_COLLECTOR:
          serviceImpl.deleteCollector((agent.CollectorOuterClass.CollectorDelete) request,
              (io.grpc.stub.StreamObserver<agent.Common.AuthResponse>) responseObserver);
          break;
        case METHODID_LIST_COLLECTOR:
          serviceImpl.listCollector((agent.Common.ListRequest) request,
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ListCollectorResponse>) responseObserver);
          break;
        case METHODID_LIST_COLLECTOR_HOSTNAMES:
          serviceImpl.listCollectorHostnames((agent.Common.ListRequest) request,
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorHostnames>) responseObserver);
          break;
        case METHODID_GET_COLLECTORS_BY_HOSTNAME_AND_MODULE:
          serviceImpl.getCollectorsByHostnameAndModule((agent.CollectorOuterClass.FilterByHostAndModule) request,
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ListCollectorResponse>) responseObserver);
          break;
        case METHODID_GET_COLLECTOR_CONFIG:
          serviceImpl.getCollectorConfig((agent.CollectorOuterClass.ConfigRequest) request,
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorConfig>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COLLECTOR_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.collectorStream(
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorMessages>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterCollectorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.CollectorOuterClass.RegisterRequest,
              agent.Common.AuthResponse>(
                service, METHODID_REGISTER_COLLECTOR)))
        .addMethod(
          getDeleteCollectorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.CollectorOuterClass.CollectorDelete,
              agent.Common.AuthResponse>(
                service, METHODID_DELETE_COLLECTOR)))
        .addMethod(
          getListCollectorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.Common.ListRequest,
              agent.CollectorOuterClass.ListCollectorResponse>(
                service, METHODID_LIST_COLLECTOR)))
        .addMethod(
          getCollectorStreamMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              agent.CollectorOuterClass.CollectorMessages,
              agent.CollectorOuterClass.CollectorMessages>(
                service, METHODID_COLLECTOR_STREAM)))
        .addMethod(
          getListCollectorHostnamesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.Common.ListRequest,
              agent.CollectorOuterClass.CollectorHostnames>(
                service, METHODID_LIST_COLLECTOR_HOSTNAMES)))
        .addMethod(
          getGetCollectorsByHostnameAndModuleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.CollectorOuterClass.FilterByHostAndModule,
              agent.CollectorOuterClass.ListCollectorResponse>(
                service, METHODID_GET_COLLECTORS_BY_HOSTNAME_AND_MODULE)))
        .addMethod(
          getGetCollectorConfigMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.CollectorOuterClass.ConfigRequest,
              agent.CollectorOuterClass.CollectorConfig>(
                service, METHODID_GET_COLLECTOR_CONFIG)))
        .build();
  }

  private static abstract class CollectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CollectorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return agent.CollectorOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CollectorService");
    }
  }

  private static final class CollectorServiceFileDescriptorSupplier
      extends CollectorServiceBaseDescriptorSupplier {
    CollectorServiceFileDescriptorSupplier() {}
  }

  private static final class CollectorServiceMethodDescriptorSupplier
      extends CollectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CollectorServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CollectorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CollectorServiceFileDescriptorSupplier())
              .addMethod(getRegisterCollectorMethod())
              .addMethod(getDeleteCollectorMethod())
              .addMethod(getListCollectorMethod())
              .addMethod(getCollectorStreamMethod())
              .addMethod(getListCollectorHostnamesMethod())
              .addMethod(getGetCollectorsByHostnameAndModuleMethod())
              .addMethod(getGetCollectorConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
