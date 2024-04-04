package agent;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.55.1)",
    comments = "Source: collector.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PanelCollectorServiceGrpc {

  private PanelCollectorServiceGrpc() {}

  public static final String SERVICE_NAME = "agent.PanelCollectorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorConfig,
      agent.CollectorOuterClass.ConfigKnowledge> getCollectorConfigStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CollectorConfigStream",
      requestType = agent.CollectorOuterClass.CollectorConfig.class,
      responseType = agent.CollectorOuterClass.ConfigKnowledge.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorConfig,
      agent.CollectorOuterClass.ConfigKnowledge> getCollectorConfigStreamMethod() {
    io.grpc.MethodDescriptor<agent.CollectorOuterClass.CollectorConfig, agent.CollectorOuterClass.ConfigKnowledge> getCollectorConfigStreamMethod;
    if ((getCollectorConfigStreamMethod = PanelCollectorServiceGrpc.getCollectorConfigStreamMethod) == null) {
      synchronized (PanelCollectorServiceGrpc.class) {
        if ((getCollectorConfigStreamMethod = PanelCollectorServiceGrpc.getCollectorConfigStreamMethod) == null) {
          PanelCollectorServiceGrpc.getCollectorConfigStreamMethod = getCollectorConfigStreamMethod =
              io.grpc.MethodDescriptor.<agent.CollectorOuterClass.CollectorConfig, agent.CollectorOuterClass.ConfigKnowledge>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CollectorConfigStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.CollectorConfig.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  agent.CollectorOuterClass.ConfigKnowledge.getDefaultInstance()))
              .setSchemaDescriptor(new PanelCollectorServiceMethodDescriptorSupplier("CollectorConfigStream"))
              .build();
        }
      }
    }
    return getCollectorConfigStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PanelCollectorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PanelCollectorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PanelCollectorServiceStub>() {
        @java.lang.Override
        public PanelCollectorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PanelCollectorServiceStub(channel, callOptions);
        }
      };
    return PanelCollectorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PanelCollectorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PanelCollectorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PanelCollectorServiceBlockingStub>() {
        @java.lang.Override
        public PanelCollectorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PanelCollectorServiceBlockingStub(channel, callOptions);
        }
      };
    return PanelCollectorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PanelCollectorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PanelCollectorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PanelCollectorServiceFutureStub>() {
        @java.lang.Override
        public PanelCollectorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PanelCollectorServiceFutureStub(channel, callOptions);
        }
      };
    return PanelCollectorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void collectorConfigStream(agent.CollectorOuterClass.CollectorConfig request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ConfigKnowledge> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCollectorConfigStreamMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PanelCollectorService.
   */
  public static abstract class PanelCollectorServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PanelCollectorServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PanelCollectorService.
   */
  public static final class PanelCollectorServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PanelCollectorServiceStub> {
    private PanelCollectorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PanelCollectorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PanelCollectorServiceStub(channel, callOptions);
    }

    /**
     */
    public void collectorConfigStream(agent.CollectorOuterClass.CollectorConfig request,
        io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ConfigKnowledge> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCollectorConfigStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PanelCollectorService.
   */
  public static final class PanelCollectorServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PanelCollectorServiceBlockingStub> {
    private PanelCollectorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PanelCollectorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PanelCollectorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public agent.CollectorOuterClass.ConfigKnowledge collectorConfigStream(agent.CollectorOuterClass.CollectorConfig request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCollectorConfigStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PanelCollectorService.
   */
  public static final class PanelCollectorServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PanelCollectorServiceFutureStub> {
    private PanelCollectorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PanelCollectorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PanelCollectorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<agent.CollectorOuterClass.ConfigKnowledge> collectorConfigStream(
        agent.CollectorOuterClass.CollectorConfig request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCollectorConfigStreamMethod(), getCallOptions()), request);
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
        case METHODID_COLLECTOR_CONFIG_STREAM:
          serviceImpl.collectorConfigStream((agent.CollectorOuterClass.CollectorConfig) request,
              (io.grpc.stub.StreamObserver<agent.CollectorOuterClass.ConfigKnowledge>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCollectorConfigStreamMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              agent.CollectorOuterClass.CollectorConfig,
              agent.CollectorOuterClass.ConfigKnowledge>(
                service, METHODID_COLLECTOR_CONFIG_STREAM)))
        .build();
  }

  private static abstract class PanelCollectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PanelCollectorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return agent.CollectorOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PanelCollectorService");
    }
  }

  private static final class PanelCollectorServiceFileDescriptorSupplier
      extends PanelCollectorServiceBaseDescriptorSupplier {
    PanelCollectorServiceFileDescriptorSupplier() {}
  }

  private static final class PanelCollectorServiceMethodDescriptorSupplier
      extends PanelCollectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PanelCollectorServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PanelCollectorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PanelCollectorServiceFileDescriptorSupplier())
              .addMethod(getCollectorConfigStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
