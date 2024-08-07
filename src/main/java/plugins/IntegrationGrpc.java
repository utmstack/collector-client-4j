package plugins;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.65.1)",
    comments = "Source: plugins.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class IntegrationGrpc {

  private IntegrationGrpc() {}

  public static final java.lang.String SERVICE_NAME = "plugins.Integration";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<plugins.Plugins.Log,
      plugins.Plugins.Ack> getProcessLogMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ProcessLog",
      requestType = plugins.Plugins.Log.class,
      responseType = plugins.Plugins.Ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<plugins.Plugins.Log,
      plugins.Plugins.Ack> getProcessLogMethod() {
    io.grpc.MethodDescriptor<plugins.Plugins.Log, plugins.Plugins.Ack> getProcessLogMethod;
    if ((getProcessLogMethod = IntegrationGrpc.getProcessLogMethod) == null) {
      synchronized (IntegrationGrpc.class) {
        if ((getProcessLogMethod = IntegrationGrpc.getProcessLogMethod) == null) {
          IntegrationGrpc.getProcessLogMethod = getProcessLogMethod =
              io.grpc.MethodDescriptor.<plugins.Plugins.Log, plugins.Plugins.Ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ProcessLog"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  plugins.Plugins.Log.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  plugins.Plugins.Ack.getDefaultInstance()))
              .setSchemaDescriptor(new IntegrationMethodDescriptorSupplier("ProcessLog"))
              .build();
        }
      }
    }
    return getProcessLogMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IntegrationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IntegrationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IntegrationStub>() {
        @java.lang.Override
        public IntegrationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IntegrationStub(channel, callOptions);
        }
      };
    return IntegrationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IntegrationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IntegrationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IntegrationBlockingStub>() {
        @java.lang.Override
        public IntegrationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IntegrationBlockingStub(channel, callOptions);
        }
      };
    return IntegrationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IntegrationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IntegrationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IntegrationFutureStub>() {
        @java.lang.Override
        public IntegrationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IntegrationFutureStub(channel, callOptions);
        }
      };
    return IntegrationFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<plugins.Plugins.Log> processLog(
        io.grpc.stub.StreamObserver<plugins.Plugins.Ack> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getProcessLogMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Integration.
   */
  public static abstract class IntegrationImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return IntegrationGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Integration.
   */
  public static final class IntegrationStub
      extends io.grpc.stub.AbstractAsyncStub<IntegrationStub> {
    private IntegrationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IntegrationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IntegrationStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<plugins.Plugins.Log> processLog(
        io.grpc.stub.StreamObserver<plugins.Plugins.Ack> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getProcessLogMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Integration.
   */
  public static final class IntegrationBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<IntegrationBlockingStub> {
    private IntegrationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IntegrationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IntegrationBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Integration.
   */
  public static final class IntegrationFutureStub
      extends io.grpc.stub.AbstractFutureStub<IntegrationFutureStub> {
    private IntegrationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IntegrationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IntegrationFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_PROCESS_LOG = 0;

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
        case METHODID_PROCESS_LOG:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.processLog(
              (io.grpc.stub.StreamObserver<plugins.Plugins.Ack>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getProcessLogMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              plugins.Plugins.Log,
              plugins.Plugins.Ack>(
                service, METHODID_PROCESS_LOG)))
        .build();
  }

  private static abstract class IntegrationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IntegrationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return plugins.Plugins.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Integration");
    }
  }

  private static final class IntegrationFileDescriptorSupplier
      extends IntegrationBaseDescriptorSupplier {
    IntegrationFileDescriptorSupplier() {}
  }

  private static final class IntegrationMethodDescriptorSupplier
      extends IntegrationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    IntegrationMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (IntegrationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IntegrationFileDescriptorSupplier())
              .addMethod(getProcessLogMethod())
              .build();
        }
      }
    }
    return result;
  }
}
