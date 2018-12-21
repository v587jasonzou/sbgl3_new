package sbgl3.yunda.module.sbxj.entry;

import java.io.Serializable;

/**
 * <li>标题: 机车检修整备管理信息系统
 * <li>说明: InspectPlanEquipmentBean实体类，用于联合分页查询
 * <li>创建人：何涛
 * <li>创建日期：2016年6月16日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public final class InspectPlanEquipmentBean implements Serializable {
	/** 使用默认序列版本ID */
	private static final long serialVersionUID = 1L;
	public static final String CHECKRESULT_YXJ = "已巡检";
	public static final String CHECKRESULT_WXJ = "未巡检";
	public static final String CHECKRESULT_WSC = "未上传";

	/** 选中 */
	public static final int CHECKED = 0;
	/** 未选中 */
	public static final int UNCHECKED = 1;
	/** 是否勾选：0 勾选，1 未勾选 */
	private Integer checked = UNCHECKED;

	/** idx主键 */
	private String idx;
	/** 巡检周期计划idx主键 */
	private String planIdx;
	/** 设备主键 */
	private String equipmentIdx;
	/** 序号 */
	private Integer seqNo;
	/** 巡检人 */
	private String partrolWorker;
	/** 巡检人ID */
	private Long partrolWorkerId;
	/** 使用人 */
	private String useWorker;
	/** 使用人ID */
	private Long useWorkerId;
	/** 巡检结果（已巡检、未巡检） */
	private String checkResult;
	/** 巡检情况描述 */
	private String checkResultDesc;
	/** 设备类别编码 */
	private String classCode;
	/** 设备类别名称 */
	private String className;
	/** 设备名称 */
	private String equipmentName;
	/** 设备编码 */
	private String equipmentCode;
	/** 购入日期 */
	private Long buyDate;
	/** 固资编号 */
	private String fixedAssetNo;
	/** 固资原值 */
	private Float fixedAssetValue;
	/** 型号 */
	private String model;
	/** 规格 */
	private String specification;
	/** 机械系数 */
	private Integer mechanicalCoefficient;
	/** 电气系数 */
	private Integer electricCoefficient;
	/** 制造工厂 */
	private String makeFactory;
	/** 制造年月 */
	private Long makeDate;
	/** 设置地点(来源码表) */
	private String usePlace;
	/** 未处理的巡检记录数 */
	private Integer wclCount;
	/** 已处理的巡检记录数 */
	private Integer yclCount;
	/** 实际开工时间 */
	private Long realBeginTime;
	/**机械包修人*/
	private String mechanicalRepairPerson;
	/**电器包修人*/
	private String electricRepairPerson;

	public String getMechanicalRepairPerson() {
		return mechanicalRepairPerson;
	}

	public void setMechanicalRepairPerson(String mechanicalRepairPerson) {
		this.mechanicalRepairPerson = mechanicalRepairPerson;
	}

	public String getElectricRepairPerson() {
		return electricRepairPerson;
	}

	public void setElectricRepairPerson(String electricRepairPerson) {
		this.electricRepairPerson = electricRepairPerson;
	}

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

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getPlanIdx() {
		return planIdx;
	}

	public void setPlanIdx(String planIdx) {
		this.planIdx = planIdx;
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

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public Long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Long buyDate) {
		this.buyDate = buyDate;
	}

	public String getFixedAssetNo() {
		return fixedAssetNo;
	}

	public void setFixedAssetNo(String fixedAssetNo) {
		this.fixedAssetNo = fixedAssetNo;
	}

	public Float getFixedAssetValue() {
		return fixedAssetValue;
	}

	public void setFixedAssetValue(Float fixedAssetValue) {
		this.fixedAssetValue = fixedAssetValue;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getMechanicalCoefficient() {
		return mechanicalCoefficient;
	}

	public void setMechanicalCoefficient(Integer mechanicalCoefficient) {
		this.mechanicalCoefficient = mechanicalCoefficient;
	}

	public Integer getElectricCoefficient() {
		return electricCoefficient;
	}

	public void setElectricCoefficient(Integer electricCoefficient) {
		this.electricCoefficient = electricCoefficient;
	}

	public String getMakeFactory() {
		return makeFactory;
	}

	public void setMakeFactory(String makeFactory) {
		this.makeFactory = makeFactory;
	}

	public Long getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Long makeDate) {
		this.makeDate = makeDate;
	}

	public String getUsePlace() {
		return usePlace;
	}

	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}

	public String getPartrolWorker() {
		return partrolWorker;
	}

	public void setPartrolWorker(String partrolWorker) {
		this.partrolWorker = partrolWorker;
	}

	public Long getPartrolWorkerId() {
		return partrolWorkerId;
	}

	public void setPartrolWorkerId(Long partrolWorkerId) {
		this.partrolWorkerId = partrolWorkerId;
	}

	public String getUseWorker() {
		return useWorker;
	}

	public void setUseWorker(String useWorker) {
		this.useWorker = useWorker;
	}

	public Long getUseWorkerId() {
		return useWorkerId;
	}

	public void setUseWorkerId(Long useWorkerId) {
		this.useWorkerId = useWorkerId;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckResultDesc() {
		return checkResultDesc;
	}

	public void setCheckResultDesc(String checkResultDesc) {
		this.checkResultDesc = checkResultDesc;
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

	public Long getRealBeginTime() {
		return realBeginTime;
	}

	public void setRealBeginTime(Long realBeginTime) {
		this.realBeginTime = realBeginTime;
	}

}