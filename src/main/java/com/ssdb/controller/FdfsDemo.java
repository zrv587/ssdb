package com.ssdb.controller;


import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.apache.commons.io.*;


import java.io.*;

/***
 * @Author zhengr
 * @date 2018-06-22
 */
public class FdfsDemo {
    public static void main(String[] args) {
        try {
            // 7.使用StorageClient对象上传文件(图片)
            // 参数1：文件名，参数名：扩展名，不能包含"."，参数3：文件的元数据，保存文件的原始名、大小、尺寸等，如果没有可为null
        String[] strings = getStorageClient().upload_file("C:\\Users\\Administrator\\Desktop\\meinv.jpg", "jpg", null);
        for (String str: strings ) {
            System.out.println(str);
        }

       int i= downloadFile("group1","M00/00/00/wKgABFssye6AU92qAAEKu9v-PZM915.jpg",new File("meinv.jpg"));
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
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
    public static int downloadFile(String fileId, String fileName,File outFile) {
        FileOutputStream fos = null;
        try {
            byte[] content = getStorageClient().download_file(fileId,fileName);
           InputStream inputStream= new ByteArrayInputStream(content);
            fos = new FileOutputStream(outFile);
            int i=     IOUtils.copy(inputStream,fos);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

}

