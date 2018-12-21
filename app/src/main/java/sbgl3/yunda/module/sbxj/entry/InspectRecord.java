package sbgl3.yunda.module.sbxj.entry;


import java.io.Serializable;

/**
 * <li>标题: 机车检修整备管理信息系统
 * <li>说明: InspectRecord实体类，数据表：设备巡检记录
 * <li>创建人：何涛
 * <li>创建日期：2016年6月14
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class InspectRecord implements Serializable {
    /* 使用默认序列版本ID */
    private static final long serialVersionUID = 1L;
    
    /** 巡检结果 - 合格 */
    public static final String CHECK_RESULT_HG = "合格";
    /** 巡检结果 - 不合格 */
    public static final String CHECK_RESULT_BHG = "不合格";
    /** 选中 */
    public static final int CHECKED = 0;
    /** 未选中 */
    public static final int UNCHECKED = 1;
    
    /** idx主键 */
    private String idx;
    /** 巡检设备idx主键 */
    private String planEquipmentIdx;
    /** 设备类别编码 */
    private String classCode;
    /** 设备类别名称 */
    private String className;
    /** 检查项目 */
    private String checkItem;
    /** 检查项目首拼（用于根据首字母进行快速检索） */
    private String checkItemPY;
    /** 检修类型（1：机械、2：电气、3：其它） */
	private Integer repairType;
    /** 检查标准 */
    private String checkStandard;
    /** 顺序号 */
    private Integer seqNo;
    /** 备注 */
    private String remarks;
    /** 检查结果（合格，不合格） */
    private String checkResult;
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
    /** 是否勾选：0 勾选，1 未勾选 */
    private Integer checked  = CHECKED;
    
    public String getIdx() {
        return idx;
    }
    
    public void setIdx(String idx) {
        this.idx = idx;
    }
    
    public String getPlanEquipmentIdx() {
        return planEquipmentIdx;
    }
    
    public void setPlanEquipmentIdx(String planEquipmentIdx) {
        this.planEquipmentIdx = planEquipmentIdx;
    }
    
    public String getClassCode() {
        return classCode;
    }
    
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getCheckItem() {
        return checkItem;
    }
    
    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }
    
    public String getCheckItemPY() {
        return checkItemPY;
    }
    
    public void setCheckItemPY(String checkItemPY) {
        this.checkItemPY = checkItemPY;
    }
    
    public String getCheckStandard() {
        return checkStandard;
    }
    
    public void setCheckStandard(String checkStandard) {
        this.checkStandard = checkStandard;
    }
    
    public Integer getSeqNo() {
        return seqNo;
    }
    
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getCheckResult() {
        return checkResult;
    }
    
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
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

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

	public Integer getRepairType() {
		return repairType;
	}

	public void setRepairType(Integer repairType) {
		this.repairType = repairType;
	}

}
