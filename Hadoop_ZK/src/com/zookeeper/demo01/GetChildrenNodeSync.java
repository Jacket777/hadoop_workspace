package com.zookeeper.demo01;
import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.Watcher.Event.EventType;

/*
 * 获取子节点：同步
 */

public class GetChildrenNodeSync implements Watcher{
	private static ZooKeeper zookeeper;
	
	public static void main(String[] args) throws InterruptedException{
		try {
			zookeeper = new ZooKeeper("192.168.150.130",5000,new GetChildrenNodeSync());
			Thread.sleep(Integer.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("回调事件类型: "+event.getType());
		if(event.getState() == KeeperState.SyncConnected) {
			if(event.getType()==EventType.None && null ==event.getPath()) {
			     executeZK();	
			}	
		}else {
			if(event.getType()==EventType.NodeChildrenChanged) {
				try {
					List<String>list = zookeeper.getChildren("/", true);
					System.out.println(list);
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}	
			}
		}	
	}
	
	
	public void executeZK() {
		try {
			List<String>list = zookeeper.getChildren("/", true);
			System.out.println(list);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
}