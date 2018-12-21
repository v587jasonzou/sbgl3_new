package sbgl3.yunda.module.userconfirm.entry;


import java.util.List;

/**
 * <li>标题: 设备管理信息系统
 * <li>说明: RepairScopeCase，数据表：检修范围实例
 * <li>创建人：何涛
 * <li>创建日期：2016年7月7日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class RepairScopeCase implements java.io.Serializable {
	
	/** 默认序列号 */
	private static final long serialVersionUID = 1L;

	/** 选中 */
    public static final int CHECKED = 0;
    /** 未选中 */
    public static final int UNCHECKED = 1;
	
	/** 处理状态 - 未处理 */
	public static final String STATE_WCL = "未处理";
	/** 处理状态 - 已处理 */
	public static final String STATE_YCL = "已处理";

    /** 主键 */
    private String idx;
    /** 范围定义主键 */
    private String scopeDefineIdx;
    /** 检修任务单主键 */
    private String taskListIdx;
    /** 序号 */
    private Integer sortNo;
    /** 检修类型，1：机型、2：电气、3：其它 */
    private Integer repairType;
    /** 检修范围名称 */
    private String repairItemName;
    /** 备注 */
    private String remark;
	/** 处理状态：(未处理、已处理)，默认为未处理 */
	private String state;
    /** 数据状态 */
    private Integer recordStatus;
    /** 创建人 */
    private Long creator;
    /** 创建时间 */
    private Long createTime;
    /** 修改人 */
    private Long updator;
    /** 修改时间 */
    private Long updateTime;
    
    /* 未处理作业工单数 */
    private Integer wclCount;
    /* 已处理作业工单数 */
    private Integer yclCount;
    
    /** 是否勾选：0 勾选，1 未勾选 */
    private Integer checked  = UNCHECKED;

    private String color;

    private List<RepairWorkOrder> workOrders;

    public List<RepairWorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<RepairWorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIdx() {
        return idx;
    }
    
    public void setIdx(String idx) {
        this.idx = idx;
    }
    
    public String getScopeDefineIdx() {
        return scopeDefineIdx;
    }
    
    public void setScopeDefineIdx(String scopeDefineIdx) {
        this.scopeDefineIdx = scopeDefineIdx;
    }
    
    public String getTaskListIdx() {
        return taskListIdx;
    }
    
    public void setTaskListIdx(String taskListIdx) {
        this.taskListIdx = taskListIdx;
    }
    
    public Integer getSortNo() {
        return sortNo;
    }
    
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
    
    public Integer getRepairType() {
        return repairType;
    }
    
    public void setRepairType(Integer repairType) {
        this.repairType = repairType;
    }
    
    public String getRepairItemName() {
        return repairItemName;
    }
    
    public void setRepairItemName(String repairItemName) {
        this.repairItemName = repairItemName;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

    public Integer getWclCount() {
        return wclCount;
    }
    
    public void setWclCount(Integer wclCount) {
        this.wclCount = wclCount;
    }
    
    public Integer getYclCount() {
        return yclCount;
    }
    
    public void setYclCount(Integer yclCount) {
        this.yclCount = yclCount;
    }
    
    public Integer getChecked() {
        return checked;
    }
    
    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
