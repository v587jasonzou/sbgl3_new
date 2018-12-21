package sbgl3.yunda.module.appraise.entry;

import java.io.Serializable;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 鉴定模板实体类
 * <li>创建人：刘欢
 * <li>创建日期：2018/8/1
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class AppraiseTemplateBean implements Serializable {


    /**
     * className : 机车引车出入库装置
     * templateType : 0
     * templateName : 机车引车出入库装置
     * idx : 8a82839152b021450152b02158120033
     * creator : 0
     * createTime : 1454653921000
     * recordStatus : 0
     * updator : 0
     * classCode : 39906
     * templateNo : 5027
     * remark : null
     * updateTime : 1454653921000
     */

    private String className;
    private int templateType;
    private String templateName;
    private String idx;
    private int creator;
    private long createTime;
    private int recordStatus;
    private int updator;
    private String classCode;
    private String templateNo;
    private Object remark;
    private long updateTime;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}