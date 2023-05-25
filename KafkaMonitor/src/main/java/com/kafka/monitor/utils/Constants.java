package com.kafka.monitor.utils;

/**
 * @author 18061263
 * @create 2019-05-17
 * @since v1.0.0
 */
public class Constants {
    /***************************sit环境*****************************/
//    public static final String CAS_URI = "https://sso.cnsuning.com/ids/login";
//    public static final String REF_SERVICE_URL = "http://itsm.cnsuning.com/traffic-web-in/auth?targetUrl=http%3A%2F%2Fitsm.cnsuning.com%2Ftraffic-web-in%2F";
//
//    public final static String TOPIC_LIST_URL = "http://kafkasit.cnsuning.com/consumer/list.htm";
//    public final static String TOPIC_INDEX_TEMPLATE = "http://kafkasit.cnsuning.com%s";
//    public final static String OFFSET_URL = "http://kafkasit.cnsuning.com/consumer/%s/offset.htm";
//
//    public final static String SPEED_TEMPLATE = "http://kafkasit.cnsuning.com/consumer/relOffsetPartitionData.htm";
//
//    public final static String ALARM_URL = "http://alert.cloudytrace.com/alert/newEvent.htm";
//    public final static String ALARM_SYSTEM_KEY = "d6cad680-2c2f-4e35-98c7-7afcb020e0d4";
//
//    public static final String DRIVER = "com.mysql.jdbc.Driver";
//    public static final String DB_URL = "jdbc:mysql://10.37.130.1:3306/disit2?useUnicode=true&amp;characterEncoding=UTF-8";
//    public static final String DB_USER_NAME = "selffabu";
//    public static final String DB_PASSWORD = "IbfloGYHC2";
//
//    public static final String ES_SERVER_NAMES = "olap01-sit.cnsuning.com,olap02-sit.cnsuning.com,olap03-sit.cnsuning.com,olap04-sit.cnsuning.com,olap06-sit.cnsuning.com,olap05-sit.cnsuning.com";
//    public static final int ES_PORT = 9300;
//    public static final String ES_CLUSTER_NAME = "common";
//    public static final String ES_INDEX = "dataflow_monitor_metric";
//    public static final String ES_INDEX_TYPE = "dataflow_monitor_metric_type";

    /***************************pre环境*****************************/
//    public static final String CAS_URI = "https://sso.cnsuning.com/ids/login";
//    public static final String REF_SERVICE_URL = "http://itsm.cnsuning.com/traffic-web-in/auth?targetUrl=http%3A%2F%2Fitsm.cnsuning.com%2Ftraffic-web-in%2F";
//
//    public final static String TOPIC_LIST_URL = "http://kafkapre.cnsuning.com/consumer/list.htm";
//    public final static String TOPIC_INDEX_TEMPLATE = "http://kafkapre.cnsuning.com%s";
//    public final static String OFFSET_URL = "http://kafkapre.cnsuning.com/consumer/%s/offset.htm";
//
//    public final static String SPEED_TEMPLATE = "http://kafkapre.cnsuning.com/consumer/relOffsetPartitionData.htm";
//
//    public final static String ALARM_URL = "http://alert.cloudytrace.com/alert/newEvent.htm";
//    public final static String ALARM_SYSTEM_KEY = "d6cad680-2c2f-4e35-98c7-7afcb020e0d4";
//
//    public static final String DRIVER = "com.mysql.jdbc.Driver";
//    public static final String DB_URL = "jdbc:mysql://10.242.100.33:3306/dipre1?useUnicode=true&amp;characterEncoding=UTF-8";
//    public static final String DB_USER_NAME = "DIusr";
//    public static final String DB_PASSWORD = "QSkxk1ngefP5";
//
//    public static final String ES_SERVER_NAMES = "ocep02-pre-xg.cnsuning.com,ocep03-pre-xg.cnsuning.com,ocep04-pre-xg.cnsuning.com,ocep05-pre-xg.cnsuning.com,ocep06-pre-xg.cnsuning.com,ocep07-pre-xg.cnsuning.com,ocep08-pre-xg.cnsuning.com";
//    public static final int ES_PORT = 9101;
//    public static final String ES_CLUSTER_NAME = "common";
//    public static final String ES_INDEX = "dataflow_monitor_metric";
//    public static final String ES_INDEX_TYPE = "dataflow_monitor_metric_type";

    /***************************prd环境*****************************/
    public static final String CAS_URI = "https://sso.cnsuning.com/ids/login";
    public static final String REF_SERVICE_URL = "http://itsm.cnsuning.com/traffic-web-in/auth?targetUrl=http%3A%2F%2Fitsm.cnsuning.com%2Ftraffic-web-in%2F";

    public final static String TOPIC_LIST_URL = "http://kafka.cnsuning.com/consumer/list.htm";
    public final static String TOPIC_INDEX_TEMPLATE = "http://kafka.cnsuning.com%s";
    public final static String OFFSET_URL = "http://kafka.cnsuning.com/consumer/%s/offset.htm";

    public final static String SPEED_TEMPLATE = "http://kafka.cnsuning.com/consumer/relOffsetPartitionData.htm";

    public final static String ALARM_URL = "http://alert.cloudytrace.com/alert/newEvent.htm";
    public final static String ALARM_SYSTEM_KEY = "d6cad680-2c2f-4e35-98c7-7afcb020e0d4";

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.235.95.95:3306/diprd1?useUnicode=true&amp;characterEncoding=UTF-8";
    public static final String DB_USER_NAME = "DIusr";
    public static final String DB_PASSWORD = "FU2SmezSIXiP";

    public static final String ES_SERVER_NAMES = "10.96.49.150,10.96.49.149,10.96.49.148";
    public static final int ES_PORT = 9601;
    public static final String ES_CLUSTER_NAME = "es-flowmgr";
    public static final String ES_INDEX = "dataflow_monitor_metric";
    public static final String ES_INDEX_TYPE = "dataflow_monitor_metric_type";

    /***************************sit - prd环境*****************************/
    /*
    public static final String CAS_URI = "https://sso.cnsuning.com/ids/login";
    public static final String REF_SERVICE_URL = "http://itsm.cnsuning.com/traffic-web-in/auth?targetUrl=http%3A%2F%2Fitsm.cnsuning.com%2Ftraffic-web-in%2F";

    public final static String TOPIC_LIST_URL = "http://kafka.cnsuning.com/consumer/list.htm";
    public final static String TOPIC_INDEX_TEMPLATE = "http://kafka.cnsuning.com%s";
    public final static String OFFSET_URL = "http://kafka.cnsuning.com/consumer/%s/offset.htm";

    public final static String SPEED_TEMPLATE = "http://kafka.cnsuning.com/consumer/relOffsetPartitionData.htm";

    public final static String ALARM_URL = "http://alert.cloudytrace.com/alert/newEvent.htm";
    public final static String ALARM_SYSTEM_KEY = "d6cad680-2c2f-4e35-98c7-7afcb020e0d4";

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://10.37.130.1:3306/disit2?useUnicode=true&amp;characterEncoding=UTF-8";
    public static final String DB_USER_NAME = "DIusr";
    public static final String DB_PASSWORD = "FU2SmezSIXiP";

    public static final String ES_SERVER_NAMES = "olap01-sit.cnsuning.com,olap02-sit.cnsuning.com,olap03-sit.cnsuning.com,olap04-sit.cnsuning.com,olap06-sit.cnsuning.com,olap05-sit.cnsuning.com";
    public static final int ES_PORT = 9300;
    public static final String ES_CLUSTER_NAME = "common";
    public static final String ES_INDEX = "dataflow_monitor_metric";
    public static final String ES_INDEX_TYPE = "dataflow_monitor_metric_type";
    */


//    /***************************pre - prd环境*****************************/
//    public static final String CAS_URI = "https://sso.cnsuning.com/ids/login";
//    public static final String REF_SERVICE_URL = "http://itsm.cnsuning.com/traffic-web-in/auth?targetUrl=http%3A%2F%2Fitsm.cnsuning.com%2Ftraffic-web-in%2F";
//
//    public final static String TOPIC_LIST_URL = "http://kafka.cnsuning.com/consumer/list.htm";
//    public final static String TOPIC_INDEX_TEMPLATE = "http://kafka.cnsuning.com%s";
//    public final static String OFFSET_URL = "http://kafka.cnsuning.com/consumer/%s/offset.htm";
//
//    public final static String SPEED_TEMPLATE = "http://kafka.cnsuning.com/consumer/relOffsetPartitionData.htm";
//
//    public final static String ALARM_URL = "http://alert.cloudytrace.com/alert/newEvent.htm";
//    public final static String ALARM_SYSTEM_KEY = "d6cad680-2c2f-4e35-98c7-7afcb020e0d4";
//
//    public static final String DRIVER = "com.mysql.jdbc.Driver";
//    public static final String DB_URL = "jdbc:mysql://10.242.100.33:3306/dipre1?useUnicode=true&amp;characterEncoding=UTF-8";
//    public static final String DB_USER_NAME = "DIusr";
//    public static final String DB_PASSWORD = "QSkxk1ngefP5";
//
//    public static final String ES_SERVER_NAMES = "ocep02-pre-xg.cnsuning.com,ocep03-pre-xg.cnsuning.com,ocep04-pre-xg.cnsuning.com,ocep05-pre-xg.cnsuning.com,ocep06-pre-xg.cnsuning.com,ocep07-pre-xg.cnsuning.com,ocep08-pre-xg.cnsuning.com";
//    public static final int ES_PORT = 9101;
//    public static final String ES_CLUSTER_NAME = "common";
//    public static final String ES_INDEX = "dataflow_monitor_metric";
//    public static final String ES_INDEX_TYPE = "dataflow_monitor_metric_type";

}
