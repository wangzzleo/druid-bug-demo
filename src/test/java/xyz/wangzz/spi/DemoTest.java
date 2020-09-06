package xyz.wangzz.spi;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.sun.tools.javac.util.ServiceLoader;
import org.junit.Test;

import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoTest {

    @Test
    public void testDemo() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("12345678");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306");
        druidDataSource.setKeepAlive(true);
        druidDataSource.setInitialSize(2);
        druidDataSource.setMinIdle(2);
        druidDataSource.setTimeBetweenEvictionRunsMillis(10000);
        druidDataSource.setMinEvictableIdleTimeMillis(31000);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setKeepAliveBetweenTimeMillis(30000);
        try {
            druidDataSource.init();
            new Thread(() -> {
                try {
                    Thread.sleep(60*1000);
                    DruidPooledConnection connection = druidDataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select 1");
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }).start();
            Thread.currentThread().join();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
