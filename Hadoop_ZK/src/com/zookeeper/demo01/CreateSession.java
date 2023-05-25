package com.zookeeper.demo01;

import java.io.IOException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
/*
 * zk���ӣ��첽
 */

public class CreateSession implements Watcher{
	private static ZooKeeper zookeeper;

	public static void main(String[] args) throws InterruptedException{
		
		try {
			//zk���췽��˵���������ַ�������ʱʱ�䣬�����¼�
			zookeeper = new ZooKeeper("192.168.150.130",5000,new CreateSession());
			System.out.println(zookeeper.getState());//ZK��״̬
			Thread.sleep(Integer.MAX_VALUE);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("���յ����¼���"+event);
	}

}
