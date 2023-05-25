package com.keyword.topic;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.admin.BrokerMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;

import org.apache.kafka.common.security.JaasUtils;

import scala.collection.Map;
import scala.collection.Seq;
import scala.tools.nsc.interpreter.IMain.StrippingTruncatingWriter;


public class TopicManager {
	
	
	/**
	 * 1.创建主题
	 * @param topic  主题名称
	 * @param partition 分区数
	 * @param replica 副本数
	 * @param properties 属性对象
	 */
	public static boolean createTopic(String topic,int partition,int replica,
			Properties properties) {
		boolean isSuccess = true;
		ZkUtils zkUtils = null;
		try {
			//1.实例化ZkUtils
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT, Config.SESSION_TIMEOUT,  Config.CONNECT_TIMEOUT,
					JaasUtils.isZkSecurityEnabled());
			if(!AdminUtils.topicExists(zkUtils, topic)) {
				//2.主题不存在，则创建
				AdminUtils.createTopic(zkUtils, topic,partition,replica,properties,
						AdminUtils.createTopic$default$6());
				System.out.println("创建成功");
				isSuccess = true;
			}else {
				System.out.println("======topic已经存在===========");
				isSuccess = false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
			isSuccess = false;
		}finally {
			zkUtils.close();
		}
		return isSuccess;
	}
	
	
	/**
	 * 2.删除主题
	 * @param topic--topic名称
	 * @return
	 */
	public static boolean deleteTopic(String topic) {
		boolean isDel = true;
		ZkUtils zkUtils = null;
		try {
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT, Config.SESSION_TIMEOUT,  Config.CONNECT_TIMEOUT,
					JaasUtils.isZkSecurityEnabled());
			if(AdminUtils.topicExists(zkUtils, topic)) {
				//主题存在，则删除
				AdminUtils.deleteTopic(zkUtils, topic);
				System.out.println("删除成功");
			}else {
				System.out.println("==topic不存在==");
				isDel = false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
			isDel = false;
		}finally {
			zkUtils.close();
		}
		return isDel;
	}
	
	
	/**
	 * 3.增加分区数
	 * @param topic--topic名称
	 * @param partition--分区数
	 * @return
	 */
	public static boolean addPartition(String topic, int partition) {
		boolean isAdd = true;
		ZkUtils zkUtils = null;
		try {
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT, Config.SESSION_TIMEOUT,  Config.CONNECT_TIMEOUT,
					JaasUtils.isZkSecurityEnabled());
			if(AdminUtils.topicExists(zkUtils, topic)) {
				//主题存在，则增加分区
				AdminUtils.addPartitions(zkUtils, topic, partition,"", true,AdminUtils.addPartitions$default$6());
				System.out.println("添加分区成功");
			}else {
				System.out.println("==topic不存在==");
				isAdd = false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
			isAdd = false;
		}finally {
			zkUtils.close();
		}
		return isAdd;
	}
	
	
	/**
	 * 4.修改分区与副本数
	 * @param topic--topic名称
	 * @param partition--新分区数：必须大于原分区数
	 * @param replica--新副本数
	 */
	public static boolean modifyReplica(String topic, int partition, int replica) {
		ZkUtils zkUtils = null;
		boolean isAdd = true;
		try {
			// 1.实例化ZkUtils
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT, Config.SESSION_TIMEOUT,
					Config.CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
			//2.检查topic是否存在
			if(AdminUtils.topicExists(zkUtils, topic)) {
				//存在则执行下面步骤
				// 3.获取Broker元数据信息
				Seq<BrokerMetadata> brokerMeta = AdminUtils.getBrokerMetadatas(
						zkUtils, AdminUtils.getBrokerMetadatas$default$2(),
						AdminUtils.getBrokerMetadatas$default$3());
				// 4.生成分区副本分配方案
				Map<Object, Seq<Object>> replicaAssign = AdminUtils
						.assignReplicasToBrokers(brokerMeta, partition, replica,
								AdminUtils.assignReplicasToBrokers$default$4(),
								AdminUtils.assignReplicasToBrokers$default$5());
				// 5.修改分区副本分配方案
				AdminUtils.createOrUpdateTopicPartitionAssignmentPathInZK(zkUtils,
						topic, replicaAssign, null, true);	
			}else {
				System.out.println("==topic不存在==");
				isAdd = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isAdd = false;
		} finally {
			zkUtils.close();
		}
		return isAdd;
	}


	/**
	 * 5.修改主题级别配置
	 * @param topic
	 * @param properties
	 */
	public static void modifyTopicConfig(String topic, Properties properties) {
		ZkUtils zkUtils = null;
		try {
			// 实例化ZkUtils
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT,Config.SESSION_TIMEOUT,
					Config.CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
			//2.检查topic是否存在
			if(AdminUtils.topicExists(zkUtils, topic)) {
				// 2.1.首先获取当前的已有配置
				Properties curProp = AdminUtils.fetchEntityConfig(zkUtils,
						ConfigType.Topic(), topic);
				// 2.2.添加新修改的配置
				curProp.putAll(properties);
				AdminUtils.changeTopicConfig(zkUtils, topic, curProp);
			}else {
				System.out.println("==topic不存在===");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			zkUtils.close();
		}
	}


	/**
	 * 5.获取主题级别的配置
	 */
	public static Properties queryTopicInfo(String topic) {
		ZkUtils zkUtils = null;
		try {
			// 实例化ZkUtils
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT, Config.SESSION_TIMEOUT,
					Config.CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
			
			//2.检查topic是否存在
			if(AdminUtils.topicExists(zkUtils, topic)) {
				// 查询主题信息
				return AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(),
						topic);
			}else {
				System.out.println("==topic不存在===");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			zkUtils.close();
		}
		return null;
	}
	
	
	/**
	 * 6.检查topic是否存在
	 * @param topic
	 * @return 返回topic是否存在
	 */
	public static boolean checkTopic(String topic) {
		boolean isExist = true;
		ZkUtils zkUtils = null;
		try {
			//1.实例化ZkUtils
			zkUtils = ZkUtils.apply(Config.ZK_CONNECT, Config.SESSION_TIMEOUT,  Config.CONNECT_TIMEOUT,
					JaasUtils.isZkSecurityEnabled());
			isExist =AdminUtils.topicExists(zkUtils, topic);
		
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("出现异常");
			isExist = false;
		}finally {
			zkUtils.close();
		}
		return isExist;	
	}
	
	
	public static void main(String[] args) {
		Properties  props = new Properties();
		//1.创建主题---本机测试
		TopicManager.createTopic("stock-quotation", 1, 3, props);  
		
		 //查询主题---p
//		 Properties properties = queryTopicInfo("keywordtest01");
//		 Iterator<Entry<Object, Object>> iterator = properties.entrySet()
//		 .iterator();
//		 while (iterator.hasNext()) {
//		 Entry<Object, Object> entry = iterator.next();
//		 System.out.println(entry.getKey() + "\t" + entry.getValue());
//		 }

		// 修改主题级别配置--p
//		 Properties topicPro = new Properties();
//		 topicPro.put("flush.messages", "1");
//		 modifyTopicConfig("keywordtest01", topicPro);

		// 增加分区
		 //addPartition("keywordtest01",3);//--p
		//增加后 {"version":1,"partitions":{"2":[1,2],"1":[0,1],"0":[2,0]}}
		// {"0":[3]}
		// String replicaAssignment = "3:1,2:1";
		 //addPartition("partition-api-foo", 2, replicaAssignment);
		//增加副本，分区或是分区重分配
		//modifyReplica("keywordtest01",4,1);  //---P
		//addReplica();
		// 删除主题
		//deleteTopic("keywordtest01");//--P
	}
}
