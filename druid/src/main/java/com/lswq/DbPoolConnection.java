package com.lswq;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import sun.security.util.Resources;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池学习
 *
 * @author zhangsw
 */
public class DbPoolConnection {
    private static DbPoolConnection databasePool = null;
    private static DruidDataSource dds = null;

    static {
        Properties properties = loadPropertyFile("db_server.properties");
        try {
            dds = (DruidDataSource) DruidDataSourceFactory
                    .createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DbPoolConnection() {
    }

    public static synchronized DbPoolConnection getInstance() {
        if (null == databasePool) {
            databasePool = new DbPoolConnection();
        }
        return databasePool;
    }

    public DruidPooledConnection getConnection() throws SQLException {
        return dds.getConnection();
    }

    public static Properties loadPropertyFile(String fullFile) {
        if (null == fullFile || fullFile.equals("")) {
            throw new IllegalArgumentException("Properties file path can not be null : " + fullFile);
        }
        InputStream inputStream = null;
        Properties p;
        try {
            inputStream = DbPoolConnection.class.getClassLoader().getResourceAsStream(fullFile);
            p = new Properties();
            p.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Properties file not found: "
                    + fullFile);
        } catch (IOException e) {
            throw new IllegalArgumentException("Properties file can not be loading: " + fullFile);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }
}



