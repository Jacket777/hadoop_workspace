package com.zookeeper.demo01;

import java.io.IOException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
/*
 * zk连接：异步
 */

public class CreateSession implements Watcher{
	private static ZooKeeper zookeeper;

	public static void main(String[] args) throws InterruptedException{
		
		try {
			//zk构造方法说明：连接字符串，超时时间，监听事件
			zookeeper = new ZooKeeper("192.168.150.130",5000,new CreateSession());
			System.out.println(zookeeper.getState());//ZK的状态
			Thread.sleep(Integer.MAX_VALUE);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("接收到的事件："+event);
	}

}
