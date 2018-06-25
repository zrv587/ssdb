package com.ssdb.controller;/**
 * Created by zhengr on 2018/6/25.
 *
 * @Description todo
 */


import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author zhengr
 * @date 2018/6/25 14:30
 * @Description jdbc工具类
 * @Version 1.0
 */
public class JDBCUtil {
    private static Connection conn = null;
    private PreparedStatement psmt = null;
    private ResultSet rs = null;

    public static Connection getConn() {
        try {
            try {
                InputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\Administrator\\Desktop\\ssdb\\src\\main\\resources\\jdbc.properties"));
                Properties properties = new Properties();
                properties.load(in);
                String url = properties.getProperty("dbid");
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                String driverName = properties.getProperty("driver");
                System.out.println(properties);
                //加载驱动
                Class.forName(driverName);
                //获取连接
                conn = DriverManager.getConnection(url, user, password);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {

        }
        return conn;
    }

    /**
     * @param
     * @return
     * @Description 增加操作
     * @Author zhengr
     * @date 2018/6/25 14:58
     */
    public int insert(String sql, String... params) {

        Map map = arrToMap(params);

        int i = 0;
        conn = getConn();
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, map.get("groupId").toString());
            psmt.setString(2, map.get("fileName").toString());
            psmt.setString(3, map.get("meta").toString());
            i = psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @param
     * @return
     * @Description 下载图片
     * @Author zhengr
     * @date 2018/6/25 15:36
     */
    public boolean query(String sql, String file,String... params) {
        conn = getConn();
        boolean flag = false;
        try {
            psmt = conn.prepareStatement(sql);
            Map map = arrToMap(params);
            psmt.setInt(1, Integer.parseInt(map.get("id").toString()));
            rs = psmt.executeQuery();
            while (rs.next()) {
                String groupId = rs.getString(1);
                String fileName = rs.getString(2);
                String meta=rs.getString(3);
                flag = FdfsDemo.downloadFile(groupId, fileName, new File(file.concat("."+meta)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /***
     * @Description 将数组转变成map
     * @Author zhengr
     * @date 2018/6/25 15:08
     * @param [params]
     * @return void
     */
    public static Map arrToMap(String[] params) {
        Map map = new HashMap();
        for (String str : params) {
            String[] arr = str.split(":");
            map.put(arr[0], arr[1]);
        }
        return map;
    }

}
