package com.zookeeper.demo01;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.Watcher.Event.EventType;

/*
 * 更新节点内容：同步
 */


public class UpdateNodeSync implements Watcher{
	private static ZooKeeper zookeeper;
	
	public static void main(String[] args) throws InterruptedException{
		try {
			zookeeper = new ZooKeeper("192.168.150.130",5000,new UpdateNodeSync());
			Thread.sleep(Integer.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected) {
			if(event.getType()==EventType.None&&null==event.getPath()) {
				executeZK();	
			}
		}	
	}
	
	public void executeZK() {
		System.out.println("执行ZK操作");
		Stat stat=null;
		try {
			stat = zookeeper.setData("/node3", "123456".getBytes(), -1);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(stat);
	}

}
