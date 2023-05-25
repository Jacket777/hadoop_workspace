package com.hdfs.unit01.rpc;
import java.io.IOException;
import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;
/*
 * 项目实战--定义RPC server端
 */
public class SayRpcServer implements Isay{
	public static void main(String[] args)
	  throws HadoopIllegalArgumentException,IOException{
		Builder builder = new RPC.Builder(new Configuration());//创建RPC server builder对象
		builder.setInstance(new SayRpcServer());//设置服务对象
		builder.setBindAddress("127.0.0.1");//设置服务端地址
		builder.setPort(8000);//设置服务端端口
		builder.setProtocol(Isay.class);//定义代理协议类型
		Server sayRpcServer = builder.build();//创建server
		sayRpcServer.start();//启动server
		System.out.println("Server started");
	}

	@Override
	public String say(String userName) {
		System.out.println("Server received data ->["+userName+"]");
		return "Hello! ["+userName+"]";
	}
}