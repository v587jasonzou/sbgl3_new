package sbgl3.yunda.module.ysrqr.entry;

/**
 * <li>标题: 机车检修整备管理信息系统
 * <li>说明: RepairWorkOrder，数据表：设备检修作业工单
 * <li>创建人：张凡
 * <li>创建日期：2016年6月16日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class RepairWorkOrder implements java.io.Serializable {

	/** 默认序列号 */
	private static final long serialVersionUID = 1L;

	/** 选中 */
	public static final int CHECKED = 0;
	/** 未选中 */
	public static final int UNCHECKED = 1;

	public static final String REPAIR_RECORD_HG = "合格";

	public static final String REPAIR_RECORD_BHG = "不合格";

	/** 工单状态 - 未处理【1】 */
	public static final int ORDER_STATUS_WCL = 1;

	/** 工单状态 - 已处理【3】 */
	public static final int ORDER_STATUS_YCL = 3;

	/* 主键 */
	private String idx;

	/* 范围实例主键 */
	private String scopeCaseIdx;

	/* 作业项定义主键 */
	private String defineIdx;

	/* 序号 */
	private Integer sortNo;

	/* 作业内容 */
	private String workContent;

	/* 工艺标准 */
	private String processStandard;

	/* 施修人 */
	private Long workerId;

	private String workerName;

	/* 实修记录 */
	private String repairRecord;

	/* 备注 */
	private String remark;

	/* 工单状态，默认为未处理（代码：1） */
	private Integer orderStatus;

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

	/* 其他作业人员名称 */
	private String otherWorkerName;

	/** 是否勾选：0 勾选，1 未勾选 */
	private Integer checked = UNCHECKED;
	/*五色图颜色*/
	private String color;

	private String repairWorkOrderIdx;

	public String getRepairWorkOrderIdx() {
		return repairWorkOrderIdx;
	}

	public void setRepairWorkOrderIdx(String repairWorkOrderIdx) {
		this.repairWorkOrderIdx = repairWorkOrderIdx;
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

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getProcessStandard() {
		return processStandard;
	}

	public void setProcessStandard(String processStandard) {
		this.processStandard = processStandard;
	}

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getRepairRecord() {
		return repairRecord;
	}

	public void setRepairRecord(String repairRecord) {
		this.repairRecord = repairRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getScopeCaseIdx() {
		return scopeCaseIdx;
	}

	public void setScopeCaseIdx(String scopeCaseIdx) {
		this.scopeCaseIdx = scopeCaseIdx;
	}

	public String getDefineIdx() {
		return defineIdx;
	}

	public void setDefineIdx(String defineIdx) {
		this.defineIdx = defineIdx;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public String getOtherWorkerName() {
		return otherWorkerName;
	}

	public void setOtherWorkerName(String otherWorkerName) {
		this.otherWorkerName = otherWorkerName;
	}

}
