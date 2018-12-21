package sbgl3.yunda.module.spot_check.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;

/**
 * <li>标题: 设备管理信息系统
 * <li>说明: PointCheck，数据表：设备点检任务单
 * <li>创建人：何涛
 * <li>创建日期：2016年8月12日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class PointCheck implements Serializable {
    
    /** 使用默认序列版本ID */
    private static final long serialVersionUID = 1L;
    
    /** 处理状态 - 未处理 */
    public static final String STATE_WCL = "未处理";
    /** 处理状态 - 已处理 */
    public static final String STATE_YCL = "已处理";
    
    /** 设备状态 - 启动 */
    public static final String EQUIPMENT_STATE_QD = "启动";
    /** 设备状态 - 停止 */
    public static final String EQUIPMENT_STATE_TZ = "停止";
    
    /** 主键 */
    private String idx;
    /** 设备idx主键 */
    private String equipmentIdx;
    /** 点检人 */
    private String checkEmpId;
    /** 点检人名称 */
    private String checkEmp;
    /** 点检日期 */
    private String checkDate;
    /** 点检时间，该字段用于计算“设备运转时间”的临时字段 */
    private Long checkTime;
    /** 设备运转时间，单位：小时，运转时间 = 点检结束时间 - 点检开始时间 */
    private Float runningTime;
    /**计算设备运转时间*/
    private Float calRunningTime;
    /**处理状态*/
    private String state;
    /**设备运行状态状态*/
    private String equipmentState;
    
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

    public String getEquipmentIdx() {
        return equipmentIdx;
    }

    public void setEquipmentIdx(String equipmentIdx) {
        this.equipmentIdx = equipmentIdx;
    }

    public String getCheckEmpId() {
        return checkEmpId;
    }

    public void setCheckEmpId(String checkEmpId) {
        this.checkEmpId = checkEmpId;
    }

    public String getCheckEmp() {
        return checkEmp;
    }

    public void setCheckEmp(String checkEmp) {
        this.checkEmp = checkEmp;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Long checkTime) {
        this.checkTime = checkTime;
    }

    public Float getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Float runningTime) {
        this.runningTime = runningTime;
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
    
    public Float getCalRunningTime() {
        return calRunningTime;
    }
    
    public void setCalRunningTime(Float calRunningTime) {
        this.calRunningTime = calRunningTime;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    /** 设备点检内容 */
    private List<PointCheckContent> checkContentList;

    /** 设备信息 */
    private EquipmentPrimaryInfo equipmentInfo;
    
    public String getEquipmentState() {
        return equipmentState;
    }
    
    public void setEquipmentState(String equipmentState) {
        this.equipmentState = equipmentState;
    }
    
    public List<PointCheckContent> getCheckContentList() {
        return checkContentList;
    }
    
    public void setCheckContentList(List<PointCheckContent> checkContentList) {
        this.checkContentList = checkContentList;
    }

    public EquipmentPrimaryInfo getEquipmentInfo() {
        return equipmentInfo;
    }
    
    public void setEquipmentInfo(EquipmentPrimaryInfo equipmentInfo) {
        this.equipmentInfo = equipmentInfo;
    }

}
