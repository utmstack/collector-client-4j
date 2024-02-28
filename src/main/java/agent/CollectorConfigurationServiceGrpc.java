package agent;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.55.1)",
    comments = "Source: collector.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CollectorConfigurationServiceGrpc {

  private CollectorConfigurationServiceGrpc() {}

  public static final String SERVICE_NAME = "agent.CollectorConfigurationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorConfig,
      agent.CollectorOuterClass.ConfigKnowledge> getCollectorConfigStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CollectorConfigStream",
      requestType = agent.CollectorOuterClass.CollectorConfig.class,
      responseType = agent.CollectorOuterClass.ConfigKnowledge.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorConfig,
      agent.CollectorOuterClass.ConfigKnowledge> getCollectorConfigStreamMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorConfig, agent.CollectorOuterClass.ConfigKnowledge> getCollectorConfigStreamMethod;
    if ((getCollectorConfigStreamMethod = CollectorConfigurationServiceGrpc.getCollectorConfigStreamMethod) == null) {
      synchronized (CollectorConfigurationServiceGrpc.class) {
        if ((getCollectorConfigStreamMethod = CollectorConfigurationServiceGrpc.getCollectorConfigStreamMethod) == null) {
          CollectorConfigurationServiceGrpc.getCollectorConfigStreamMethod = getCollectorConfigStreamMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.CollectorConfig, agent.CollectorOuterClass.ConfigKnowledge>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CollectorConfigStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorConfig.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.ConfigKnowledge.getDefaultInstance()))
              .setSchemaDescriptor(new CollectorConfigurationServiceMethodDescriptorSupplier("CollectorConfigStream"))
              .build();
        }
      }
    }
    return getCollectorConfigStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CollectorConfigurationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorConfigurationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorConfigurationServiceStub>() {
        @java.lang.Override
        public CollectorConfigurationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorConfigurationServiceStub(channel, callOptions);
        }
      };
    return CollectorConfigurationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CollectorConfigurationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorConfigurationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorConfigurationServiceBlockingStub>() {
        @java.lang.Override
        public CollectorConfigurationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorConfigurationServiceBlockingStub(channel, callOptions);
        }
      };
    return CollectorConfigurationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CollectorConfigurationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollectorConfigurationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollectorConfigurationServiceFutureStub>() {
        @java.lang.Override
        public CollectorConfigurationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollectorConfigurationServiceFutureStub(channel, callOptions);
        }
      };
    return CollectorConfigurationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorConfig> collectorConfigStream(
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ConfigKnowledge> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCollectorConfigStreamMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CollectorConfigurationService.
   */
  public static abstract class CollectorConfigurationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CollectorConfigurationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CollectorConfigurationService.
   */
  public static final class CollectorConfigurationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CollectorConfigurationServiceStub> {
    private CollectorConfigurationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorConfigurationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorConfigurationServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<agent.CollectorOuterClass.CollectorConfig> collectorConfigStream(
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ConfigKnowledge> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCollectorConfigStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CollectorConfigurationService.
   */
  public static final class CollectorConfigurationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CollectorConfigurationServiceBlockingStub> {
    private CollectorConfigurationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorConfigurationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorConfigurationServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CollectorConfigurationService.
   */
  public static final class CollectorConfigurationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CollectorConfigurationServiceFutureStub> {
    private CollectorConfigurationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollectorConfigurationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollectorConfigurationServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_COLLECTOR_CONFIG_STREAM = 0;

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
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COLLECTOR_CONFIG_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.collectorConfigStream(
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ConfigKnowledge>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCollectorConfigStreamMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              agent.CollectorOuterClass.CollectorConfig,
              agent.CollectorOuterClass.ConfigKnowledge>(
                service, METHODID_COLLECTOR_CONFIG_STREAM)))
        .build();
  }

  private static abstract class CollectorConfigurationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CollectorConfigurationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return agent.CollectorOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CollectorConfigurationService");
    }
  }

  private static final class CollectorConfigurationServiceFileDescriptorSupplier
      extends CollectorConfigurationServiceBaseDescriptorSupplier {
    CollectorConfigurationServiceFileDescriptorSupplier() {}
  }

  private static final class CollectorConfigurationServiceMethodDescriptorSupplier
      extends CollectorConfigurationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CollectorConfigurationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CollectorConfigurationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CollectorConfigurationServiceFileDescriptorSupplier())
              .addMethod(getCollectorConfigStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
