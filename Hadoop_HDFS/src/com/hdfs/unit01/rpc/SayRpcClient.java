package com.hdfs.unit01.rpc;

import java.util.Scanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.conf.Configuration;
/*
 * 项目实战--定义RPC通信Client端
 */
public class SayRpcClient {
	private static Scanner sc;
	public static void main(String[]args)throws IOException{
		//要连接服务端的通信地址及端口
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",8000);
		//初始化Rpc代理
		Isay proxySay = RPC.getProxy(Isay.class, 1L, inetSocketAddress,
				new Configuration());
		System.out.println("请输入用户姓名[退出请输入quit]:");
		sc = new Scanner(System.in);//从控制台读取数据
		while(sc.hasNext()) {//判断输入
			String userName = sc.next();//读取输入数据
			if("quit".equals(userName)) {//设定退出条件
				System.out.println("RPC client exited");
				return;
			}
			String s = proxySay.say(userName);//远程调用
			System.out.println(s);//输入server返回的数据
			System.out.println("请再输入用户姓名[退出请输入quit]:");
		}	
	}
}