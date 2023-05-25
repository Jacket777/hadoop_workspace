package com.keyword.topic;

public interface Config {
	/** 连接Zk */
	static final String ZK_CONNECT="192.168.150.130:2181,192.168.150.131:2181,192.168.150.132:2181";
	/** session过期时间 */
	static final int SESSION_TIMEOUT = 30000;
	/** 连接超时时间 */
	static final int CONNECT_TIMEOUT = 30000;

}
