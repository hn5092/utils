package com.xym.hadoop.rpc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.SocketFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Closeable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.retry.RetryPolicy;
import org.apache.hadoop.ipc.Client.ConnectionId;
import org.apache.hadoop.ipc.ProtocolMetaInfoPB;
import org.apache.hadoop.ipc.ProtocolProxy;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;
import org.apache.hadoop.ipc.RpcEngine;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.SecretManager;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.junit.Before;
import org.junit.Test;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.EnumDescriptorProto;

public class TestRPC {
  public static Configuration conf;
  public static final String ADDRESS= "localhost";
  @Before
  public void setupConf() {
    // new一个默认的shconfiguration
    conf = new Configuration();
    // 设置在配置文件中设置键值对 用来干啥的 里面方法判断参数2是否是参数3的子类.是的话 保存 参数一和参数2的名称
    conf.setClass("rpc.engine." + StoppedProtocol.class.getName(), StoppedRpcEngine.class,
        RpcEngine.class);
    // 在组信息中设置设置配置文件
    UserGroupInformation.setConfiguration(conf);
  }

  /**
   * 继承通讯协议 可以获取当前的协议版本 和协议的签名 此接口有很多实现 里面记载了每个版本的新特性 在这里我们可以选择切换版本
   * 其中一个实现是client的实现.这个实现里面有各种不同的协议版本用来对应的 client对jobtarck 通讯 这个jobclient可以使用这些通讯来提交任务并且了解当前任务的运行状态
   * 另外一个实现是TaskUmbilicalProtocol这个类 这个类是每个节点的父进程和子进程进行通讯的 当他run 一个map 或者 reduce时候
   * 子进程就通过这个协议来跟父进程进行通讯
   * 
   * @author imad
   *
   */
  public interface TestProtocol extends VersionedProtocol {
    public static final long versionID = 1L;

    void ping() throws IOException;

    void slowPing(boolean shouldSlow) throws IOException;

    void sleep(long delay) throws IOException, InterruptedException;

    String echo(String value) throws IOException;

    String[] echo(String[] value) throws IOException;

    Writable echo(Writable value) throws IOException;

    int add(int v1, int v2) throws IOException;

    int add(int[] values) throws IOException;

    int error() throws IOException;

    void testServerGet() throws IOException;

    int[] exchange(int[] values) throws IOException;

    DescriptorProtos.EnumDescriptorProto exchangeProto(DescriptorProtos.EnumDescriptorProto arg);
  }
  public static class TestImpl implements TestProtocol {
    int fastPingCounter = 0;

    /**
     * 获取协议的版本信息
     */
    public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
      return TestProtocol.versionID;
    }

    /**
     * 按照协议的版本号生成一个协议新标
     */
    public ProtocolSignature getProtocolSignature(String protocol, long clientVersion,
        int clientMethodsHash) throws IOException {
      return new ProtocolSignature(TestProtocol.versionID, null);
    }


    public void ping() throws IOException {

    }

    public synchronized void slowPing(boolean shouldSlow) throws IOException {
      if (shouldSlow) {
        while (fastPingCounter < 2) {
          try {
            wait();
          } catch (InterruptedException e) {
          }
        }
        fastPingCounter -= 2;
      }else{
        fastPingCounter++;
        notify();
      }
    }

    public void sleep(long delay) throws IOException, InterruptedException {
      Thread.sleep(delay);
    }

    public String echo(String value) throws IOException {
      return value;
    }

    public String[] echo(String[] value) throws IOException {
      return value;
    }

    public Writable echo(Writable value) throws IOException {
      // TODO Auto-generated method stub
      return value;
    }

    public int add(int v1, int v2) throws IOException {
      // TODO Auto-generated method stub
      return v1+v2;
    }

    public int add(int[] values) throws IOException {
      int sum = 0;
      for(int i = 0; i < values.length ; i++){
        sum += values[i];
      }
      return sum;
    }

    public int error() throws IOException {
      throw new IOException("bobo");
    }

    public void testServerGet() throws IOException {
      if(!(org.apache.hadoop.ipc.Server.get() instanceof RPC.Server)){
        throw new IOException("Server.get() failed");

      }

    }

    public int[] exchange(int[] values) throws IOException {
      for (int i = 0; i < values.length; i++) {
        values[i] = i;
      }   
      return values;
    }

    public EnumDescriptorProto exchangeProto(EnumDescriptorProto arg) {
      // TODO Auto-generated method stub
      return arg;
    }

  }



  public static interface StoppedProtocol {
    long versionID = 0;

    public void stop();
  }
  /**
   * 这个类用来停止rpcengine get方法 通过动态代理得到一个代理对象 2个get方法的区别是其中一个有 fallbackToSimpleAuth boolean类型参数
   * 
   * @author imad
   *
   */
  private static class StoppedRpcEngine implements RpcEngine {

    public <T> ProtocolProxy<T> getProxy(Class<T> protocol, long clientVersion,
        InetSocketAddress addr, UserGroupInformation ticket, Configuration conf,
        SocketFactory factory, int rpcTimeout, RetryPolicy connectionRetryPolicy)
        throws IOException {

      return getProxy(protocol, clientVersion, addr, ticket, conf, factory, rpcTimeout,
          connectionRetryPolicy, null);
    }

    @SuppressWarnings("unused")
    public <T> ProtocolProxy<T> getProxy(Class<T> protocol, long clientVersion,
        InetSocketAddress addr, UserGroupInformation ticket, Configuration conf,
        SocketFactory factory, int rpcTimeout, RetryPolicy connectionRetryPolicy,
        AtomicBoolean fallbackToSimpleAuth) throws IOException {
      @SuppressWarnings("unchecked")
      // 通过动态代理获得一个代理对象
      T proxy =
          (T) Proxy.newProxyInstance(protocol.getClassLoader(), new Class[] {protocol},
              new StoppedInvocationHandler());
      return new ProtocolProxy<T>(protocol, proxy, false);
    }

    public Server getServer(Class<?> protocol, Object instance, String bindAddress, int port,
        int numHandlers, int numReaders, int queueSizePerHandler, boolean verbose,
        Configuration conf, SecretManager<? extends TokenIdentifier> secretManager,
        String portRangeConfig) throws IOException {

      return null;
    }

    public ProtocolProxy<ProtocolMetaInfoPB> getProtocolMetaInfoProxy(ConnectionId connId,
        Configuration conf, SocketFactory factory) throws IOException {
      return null;
    }

  }
  /**
   * 这个类用来创建动态代理 这个动态代理面没有实现任何方法
   * 
   * @author imad
   *
   */
  private static class StoppedInvocationHandler implements InvocationHandler, Closeable {
    private int closeCalled = 0;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      return null;
    }

    public void close() throws IOException {
      closeCalled++;
    }

    public int getCloseCalled() {
      return closeCalled;
    }
  }

  @Test
  public void testConfCpc() throws IOException {
    RPC.Builder builder = new RPC.Builder(conf);
    Server build = builder.setProtocol(TestProtocol.class).setInstance(new TestImpl()).setBindAddress(ADDRESS).setPort(8888)
    .setNumHandlers(1).setnumReaders(3).setQueueSizePerHandler(200).setVerbose(false).build();
    assertEquals(3,build.getNumReaders());
    assertEquals(200, build.getMaxQueueSize());
    build.start();
  }
  @Test
  public void testProxyAddress()throws IOException{
    //通过writerableengine getserver出来的
    Server server = new RPC.Builder(conf).setProtocol(TestProtocol.class)
        .setInstance(new TestImpl()).setBindAddress(ADDRESS).setPort(0).build();
    TestProtocol proxy = null;
    server.start();
    InetSocketAddress addr = NetUtils.getConnectAddress(server);
    //通过读取配置文件中set的class来创建其对象 然后通过该对象的getproxy来得到代理对象
    
    proxy = RPC.getProxy(TestProtocol.class, TestProtocol.versionID, addr, conf);
    addr = RPC.getServerAddress(proxy);
    server.stop();
    if(proxy != null){
      RPC.stopProxy(proxy);
    }
  }


}
