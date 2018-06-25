import com.ssdb.controller.FdfsDemo;
import org.csource.fastdfs.StorageClient;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 *@Author zhengr
 *@date 2018/6/25 10:17
 *@Description todo
 *@Version 1.0
 */
public class TestFdfs {
    @Test
    public void uploadTest(){
        // 7.使用StorageClient对象上传文件(图片)
        // 参数1：文件名，参数名：扩展名，不能包含"."，参数3：文件的元数据，保存文件的原始名、大小、尺寸等，如果没有可为null
        String[] strings =FdfsDemo.uploadFile("C:\\Users\\Administrator\\Desktop\\meinv.jpg", "jpg", null);
        for (String str: strings ) {
            System.out.println(str);
        }
    }

    @Test
    public void downLoadTest(){
       boolean flag=FdfsDemo.downloadFile("group1","M00/00/00/wKgABFswUeSAXQG7AAEKu9v-PZM634.jpg",new File("meinv.jpg"));
        System.out.println(flag);
    }

    @Test
    public void deleteTest(){
        FdfsDemo.delFile("group1","M00/00/00/wKgABFswUeSAXQG7AAEKu9v-PZM634.jpg");
    }
}
