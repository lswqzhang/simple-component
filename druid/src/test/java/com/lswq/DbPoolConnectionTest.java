package com.lswq;


import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbPoolConnectionTest {

    @Test
    public void dbPoolConnectionTest() throws SQLException {
        DbPoolConnection poolConnection = DbPoolConnection.getInstance();
        DruidPooledConnection druidPooledConnection = poolConnection.getConnection();
        Connection connection = druidPooledConnection.getConnection();
        String sql = "select 1";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        List list = convertList(resultSet);
        System.err.println(list);
    }

    private List convertList(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

}



