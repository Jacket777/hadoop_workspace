package com.hdfs.unit01.rpc;
import java.io.IOException;
import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;
/*
 * ��Ŀʵս--����RPC server��
 */
public class SayRpcServer implements Isay{
	public static void main(String[] args)
	  throws HadoopIllegalArgumentException,IOException{
		Builder builder = new RPC.Builder(new Configuration());//����RPC server builder����
		builder.setInstance(new SayRpcServer());//���÷������
		builder.setBindAddress("127.0.0.1");//���÷���˵�ַ
		builder.setPort(8000);//���÷���˶˿�
		builder.setProtocol(Isay.class);//�������Э������
		Server sayRpcServer = builder.build();//����server
		sayRpcServer.start();//����server
		System.out.println("Server started");
	}

	@Override
	public String say(String userName) {
		System.out.println("Server received data ->["+userName+"]");
		return "Hello! ["+userName+"]";
	}
}