package com.kafka.monitor.utils;

import com.kafka.monitor.model.AdminUser;
import com.kafka.monitor.model.ClusterTopicInfo;
import com.kafka.monitor.model.Person;
import com.kafka.monitor.model.WhiteTopic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 18061263
 * @create 2019-04-01
 * @since v1.0.0
 */
public class DBUtils {
    private Log LOG = LogFactory.getLog(DBUtils.class);
    private final String DRIVER;
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private Connection connection;

    public DBUtils(String driver, String url, String user, String password) {
        DRIVER = driver;
        URL = url;
        USER = user;
        PASSWORD = password;
    }

    public boolean test() {
        boolean isPassed = true;
        try {
            Class.forName(DRIVER);
            connection = getConnection();
        } catch (ClassNotFoundException e) {
            LOG.error(e);
            isPassed = false;
        } catch (SQLException e) {
            LOG.error(e);
            isPassed = false;
        }
        return isPassed;
    }

    /**
     * 查询SSO登录用户
     *
     * @return
     * @throws SQLException
     */
    public AdminUser queryLoginUser() throws SQLException {
        AdminUser adminUser = null;
        String sql = "select * from data_flow_admin";
        Statement stmt = null;
        ResultSet ret = null;
        stmt = connection.createStatement();
        ret = stmt.executeQuery(sql);
        while (ret.next()) {
            String userName = ret.getString("id");
            String password2 = ret.getString("password2");
            adminUser = new AdminUser(userName, password2);
        }
        ret.close();
        stmt.close();
        return adminUser;
    }

    /**
     * 查询告警通知用户列表
     *
     * @return
     * @throws SQLException
     */
    public List<Person> queryNotifyPerson() throws SQLException {
        List<Person> list = new ArrayList<>();
        String sql = "select * from data_flow_notify";
        Statement stmt = null;
        ResultSet ret = null;
        stmt = connection.createStatement();
        ret = stmt.executeQuery(sql);
        while (ret.next()) {
            String id = ret.getString("id");
            String name = ret.getString("name");
            String phone = ret.getString("phone");
            String mail = ret.getString("mail");
            Person person = new Person(id, name, phone, mail);
            list.add(person);
        }
        ret.close();
        stmt.close();
        return list;
    }

    /**
     * 查询监控白名单topic
     *
     * @return
     * @throws SQLException
     */
    public List<WhiteTopic> queryWhiteTopicList() throws SQLException {
        List<WhiteTopic> list = new ArrayList<>();
        String sql = "select * from data_flow_white_list";
        Statement stmt = null;
        ResultSet ret = null;
        stmt = connection.createStatement();
        ret = stmt.executeQuery(sql);
        while (ret.next()) {
            int id = ret.getInt("id");
            String groupId = ret.getString("group_id");
            String dc = ret.getString("dc");
            WhiteTopic whiteTopic = new WhiteTopic(id, groupId, dc);
            list.add(whiteTopic);
        }
        ret.close();
        stmt.close();
        return list;
    }

    public boolean update(List<ClusterTopicInfo> list) throws SQLException {
        String deleteSql = "delete from data_flow_topic_info";
        Statement stmt = null;
       // ResultSet ret = null;
        stmt = connection.createStatement();
        stmt.executeUpdate(deleteSql);

        String sql = "INSERT INTO data_flow_topic_info(jobid,clustername,topic,groupid,tps) values(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ClusterTopicInfo topicInfo = list.get(i);
            pstm.setInt(1, topicInfo.getJobId());
            pstm.setString(2, topicInfo.getClusterName());
            pstm.setString(3, topicInfo.getTopic());
            pstm.setString(4, topicInfo.getGroupId());
            pstm.setFloat(5, topicInfo.getTps());
            pstm.executeUpdate();
        }

        return true;
    }

    /**
     * 获取 Connetion
     *
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    /**
     * 释放连接 Connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            //等待垃圾回收
            connection = null;
        }
    }
}
