package com.hdfs.unit01.rpc;

import java.util.Scanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.conf.Configuration;
/*
 * ��Ŀʵս--����RPCͨ��Client��
 */
public class SayRpcClient {
	private static Scanner sc;
	public static void main(String[]args)throws IOException{
		//Ҫ���ӷ���˵�ͨ�ŵ�ַ���˿�
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",8000);
		//��ʼ��Rpc����
		Isay proxySay = RPC.getProxy(Isay.class, 1L, inetSocketAddress,
				new Configuration());
		System.out.println("�������û�����[�˳�������quit]:");
		sc = new Scanner(System.in);//�ӿ���̨��ȡ����
		while(sc.hasNext()) {//�ж�����
			String userName = sc.next();//��ȡ��������
			if("quit".equals(userName)) {//�趨�˳�����
				System.out.println("RPC client exited");
				return;
			}
			String s = proxySay.say(userName);//Զ�̵���
			System.out.println(s);//����server���ص�����
			System.out.println("���������û�����[�˳�������quit]:");
		}	
	}
}