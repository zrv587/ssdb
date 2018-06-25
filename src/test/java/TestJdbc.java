/**
 * Created by zhengr on 2018/6/25.
 *
 * @Description todo
 */

import com.ssdb.controller.FdfsDemo;
import com.ssdb.controller.JDBCUtil;
import org.junit.Test;

/**
 *@Author zhengr
 *@date 2018/6/25 15:34
 *@Description todo
 *@Version 1.0
 */
public class TestJdbc {
    @Test
    public void testAdd(){
        String sql = "insert into picture (groupId,filename,meta)values(?,?,?)";
        String[] strings = FdfsDemo.uploadFile("C:\\Users\\Administrator\\Desktop\\ssdb\\pom.xml", "xml", null);
        String[] obj={};
        for (int i =0;i<strings.length;i++) {
            obj = new String[]{"groupId:group1", "fileName:"+strings[1],"meta:xml"};
        }
        int i= new JDBCUtil().insert(sql,obj);
        if(i>0){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }
    }

    @Test
    public void downLoadTest(){
        String sql ="select groupId,filename,meta from picture where id=?";
        String []obj={};
        obj= new String[]{"id:8"};
       boolean flag= new JDBCUtil().query(sql,"pom1",obj);
        System.out.println(flag);
    }
}
