package com.ssdb.controller;


import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.apache.commons.io.*;


import java.io.*;

/***
 * @Author zhengr
 * @date 2018-06-22
 */

public class FdfsDemo {
    /**
     * @Description todo
     * @Author zhengr
     * @date 2018/6/25 10:28
     * @param []
     * @return org.csource.fastdfs.StorageClient 获得StorageClient对象
     */
    public static StorageClient getStorageClient() {
        // 1.先创建一个配置文件——fast_dfs.conf，配置文件的内容就是指定TrackerServer的地址
        // 2.使用全局方法加载配置文件
        try {
            ClientGlobal.init("C:\\Users\\Administrator\\Desktop\\ssdb\\src\\main\\resources\\fdfs_dfs.conf");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            System.out.println("配置文件找不到");
            e.printStackTrace();
        }
        // 3.创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 4.通过TrackerClient对象获得TrackerServer对象
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            System.out.println("获取连接失败");
            e.printStackTrace();
        }
        // 5.创建StorageServer的引用，null就可以了
        StorageServer storageServer = null;
        // 6.创建一个StorageClient对象，其需要两个参数，一个是TrackerServer，一个是StorageServer
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        return  storageClient;
    }
    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @param outFile 文件下载保存位置
     * @return
     */
    public static boolean downloadFile(String fileId, String fileName,File outFile) {
        FileOutputStream fos = null;
        int i=0;
        try {
            byte[] content = getStorageClient().download_file(fileId,fileName);
           InputStream inputStream= new ByteArrayInputStream(content);
            fos = new FileOutputStream(outFile);
                i=     IOUtils.copy(inputStream,fos);
            return i>0?true:false;
        } catch (Exception e) {
            System.out.println("【"+fileName+"】"+"不存在");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return i>0?true:false;
    }

    /**
     * @Author zhengr
     * @date
     * @param fileName 文件的名字
     * @param    extName 文件的后缀名
     * @return 
     * @Description 上传文件
     */
    public static String[] uploadFile(String fileName, String extName, NameValuePair[] metaList){
        String[] strings=null;
        try {
            strings = getStorageClient().upload_file(fileName, extName, metaList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return strings;
    }
    /***
     * @Description 删除文件
     * @Author zhengr
     * @date 2018/6/25 10:14
     * @param [groupId 组id, fileName 文件名字]
     * @return int
     */
    public static int  delFile(String groupId,String fileName){
        int i = 0;
        try {
            i = getStorageClient().delete_file(groupId, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        System.out.println( i==0 ? "删除成功" : "删除失败:"+"【"+fileName+"】不存在");
        return i;
    }
}

