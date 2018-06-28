package com.ssdb.controller;/**
 * Created by zhengr on 2018/6/25.
 *
 * @Description todo
 */


import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Author zhengr
 * @date 2018/6/25 14:30
 * @Description jdbc工具类
 * @Version 1.0
 */
public class JDBCUtil {
    private static Connection conn = null;
    private static PreparedStatement psmt = null;
    private static ResultSet rs = null;

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
                //加载驱动
                Class.forName(driverName);
                //获取连接
                conn = DriverManager.getConnection(url, user, password);
                conn.setAutoCommit(false);
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
    public static int insert(String sql, String... params) {
        int k = 0;
        conn = getConn();
        try {
            psmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]);
            }
            k = psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                psmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return k;
        }
    }

    /**
     * @param [cls 要封装的类, sql 执行的sql, params 查询的条件]
     * @return java.util.List<java.lang.Object>
     * @Description 查询操作
     * @Author zhengr
     * @date 2018/6/28 12:00
     */
    public static List <Object> query(Class <?> cls, String sql, String... params) {
        conn = getConn();
        boolean flag = false;
        List <Object> list = new ArrayList();
        try {
            psmt = conn.prepareStatement(sql);
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    psmt.setObject(i + 1, params[i]);
                }
            }
            rs = psmt.executeQuery();
            Field fields[] = cls.getDeclaredFields();
            Object o = null;

            while (rs.next()) {
                o = cls.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        field.set(o, rs.getObject(field.getName()));
                    } catch (Exception e) {
                        field.set(o, null);
                    }
                }
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                psmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    /***
     * @Description 删除操作
     * @Author zhengr
     * @date 2018/6/28 12:59
     * @param [sql, params]  
     * @return int
     */
    public static int delete(String sql, String... params) {
        conn = getConn();
        int k = 0;
        try {
            psmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]);
            }
            k = psmt.executeUpdate();
            System.out.println("删除成功");
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("数据删除失败");
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                psmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return k;
    }

    /**
     * @Description 修改操作
     * @Author zhengr
     * @date 2018/6/28 13:01
     * @param [sql, params]
     * @return void
     */
    public void update(String sql, String... params) {
        conn = getConn();
        try {
            psmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]);
            }
            psmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                psmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
