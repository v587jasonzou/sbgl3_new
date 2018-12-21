package sbgl3.yunda.module.spot_check.entity;

import java.io.Serializable;

/**
 * <li>标题: 设备管理信息系统
 * <li>说明: PointCheckContent，数据表：设备点检内容
 * <li>创建人：何涛
 * <li>创建日期：2016年8月12日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class PointCheckContent implements Serializable {

    /** 使用默认序列版本ID */
    private static final long serialVersionUID = 1L;
    
    /** 点检技术状态标志 - 良好 */
    public static final String STATE_FLAG_LH = "良好";
    /** 点检技术状态标志 - 不良 */
    public static final String STATE_FLAG_BL = "不良";
    /** 点检技术状态标志 - 待修 */
    public static final String STATE_FLAG_DX = "待修";
    /** 点检技术状态标志 - 修理*/
    public static final String STATE_FLAG_XL = "修理";
    
    /** 主键 */
    private String idx;
    /** 设备点检idx主键 */
    private String pointCheckIdx;
    /** 点检内容 */
    private String checkContent;
    /** 技术状态标志：良好、不良、待修、修理 */
    private String technologyStateFlag;
    
    /* 数据状态 */
    private Integer recordStatus;
    /* 创建人 */
    private Long creator;
    /* 创建时间 */
    private Long createTime;
    /* 修改人 */
    private Long updator;
    /* 修改时间 */
    private Long updateTime;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTechnologyStateFlag() {
        return technologyStateFlag;
    }

    public void setTechnologyStateFlag(String technologyStateFlag) {
        this.technologyStateFlag = technologyStateFlag;
    }

    public String getPointCheckIdx() {
        return pointCheckIdx;
    }

    public void setPointCheckIdx(String bitCheckIdx) {
        this.pointCheckIdx = bitCheckIdx;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    
}
