package com.zookeeper.demo01;

import java.io.IOException;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;


/*
 * zkɾ���ڵ�ʵ����ͬ��
 */

public class DeleteNodeSync implements Watcher {
	private static ZooKeeper zookeeper;
	

	public static void main(String[] args) throws InterruptedException{
		try {
			zookeeper = new ZooKeeper("192.168.150.130",50000,new  DeleteNodeSync());
			Thread.sleep(Integer.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("���յ����¼�: "+event);
		if(event.getState()== KeeperState.SyncConnected) {
			if(event.getType() == EventType.None&&null== event.getPath()) {
				executeZK();
			}	
		}	
	}
	
	public void executeZK() {
		System.out.println("ִ��zk����");
		try {
			zookeeper.delete("/node3", -1);
		}catch(KeeperException e) {
			e.printStackTrace();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}	
	}
}