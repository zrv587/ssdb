package com.ssdb.pojo;/**
 * Created by zhengr on 2018/6/28.
 *
 * @Description todo
 */

/**
 *@Author zhengr
 *@date 2018/6/28 10:49
 *@Description todo
 *@Version 1.0
 */
public class Picture {
    private Integer id;

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", groupId='" + groupId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", meta='" + meta + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    private String groupId;
    private String fileName;
    private String meta;
}
