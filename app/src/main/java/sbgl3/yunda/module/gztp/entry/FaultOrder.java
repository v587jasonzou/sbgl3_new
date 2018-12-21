package sbgl3.yunda.module.gztp.entry;

import java.util.Date;

/**
 * <li>标题: 机车检修整备管理信息系统
 * <li>说明: FaultOrder实体类，数据表：故障提票
 * <li>创建人：何涛
 * <li>创建日期：2016年6月17日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class FaultOrder implements java.io.Serializable {
    /** 使用默认序列版本ID */
    private static final long serialVersionUID = 1L;
    
    /** 提票单状态 - 新建 */
    public static final String STATE_XJ = "新建";
    /** 提票单状态 - 已派工（调度派工） */
    public static final String STATE_YPG = "已派工";
    /** 提票单状态 - 处理中（工长派工） */
    public static final String STATE_CLZ = "处理中";
    /** 提票单状态 - 已处理 */
    public static final String STATE_YCL = "已处理";
    /** 提票单状态 - 退回 */
    public static final String STATE_TH = "退回";
    /** 提票单状态 - 调度退回 */
    public static final String STATE_DDTH = "调度退回";
    /** 提票单状态 - 工长退回 */
    public static final String STATE_GZTH = "工长退回";
    /** 提票单状态 - 工人退回 */
    public static final String STATE_GRTU = "工人退回";
    
    /** 故障等级 - 一般 */
    public static final String FAULT_LEVEL_YB = "一般";
    /** 故障等级 - 重大 */
    public static final String FAULT_LEVEL_ZD = "重大";
    /** 故障等级 - 特大 */
    public static final String FAULT_LEVEL_TD = "特大";
    
    /** 选中 */
    public static final int CHECKED = 0;
    /** 未选中 */
    public static final int UNCHECKED = 1;
    /** 是否勾选：0 勾选，1 未勾选 */
    private Integer checked  = UNCHECKED;

    /** 主键 */
    private String idx;
    /** 设备idx主键 */
    private String equipmentIdx;
    /** 设备名称 */
    private String equipmentName;
    /** 设备编码 */
    private String equipmentCode;
    /** 型号 */
    private String model;
    /** 规格 */
    private String specification;
    /** 提报人 */
    private String submitEmp;
    /** 提报人ID */
    private Long submitEmpId;
    /** 提票单号 */
    private String faultOrderNo;
    /** 故障发生时间 */
    private Long faultOccurTime;
    /** 发生地点及部位 */
    private String faultPlace;
    /** 故障现象 */
    private String faultPhenomenon;
    /** 原因分析 */
    private String causeAnalysis;
    /** 施修班组ID */
    private Long repairTeamId;
    /** 施修班组 */
    private String repairTeam;
    /** 修理费用 */
    private Float repairCost;
    /** 修理人 */
    private String repairEmp;
    /** 修理人ID */
    private String repairEmpId;
    /** 修理内容 */
    private String repairContent;
    /** 使用人（保留） */
    private String useWorker;
    /** 使用人ID（保留） */
    private Long useWorkerId;
    /** 故障等级（一般，重大，特大） */
    private String faultLevel;
    /** 提票状态（新建，已派工，处理中，已处理）） */
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


    /** 业务idx主键，用于记录点/巡检过程中自动发起的提票 */
    private String businessIdx;
    /** 业务类实体，用于记录点/巡检过程中自动发起的提票 */
    private String businessEntity;
    private String equipmentType;
    /** 设置地点 */

    private String usePlace;

    public String getUsePlace() {
        return usePlace;
    }

    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
    }

    public String getBusinessIdx() {
        return businessIdx;
    }

    public void setBusinessIdx(String businessIdx) {
        this.businessIdx = businessIdx;
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
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

    public String getSubmitEmp() {
        return submitEmp;
    }

    public void setSubmitEmp(String submitEmp) {
        this.submitEmp = submitEmp;
    }

    public Long getSubmitEmpId() {
        return submitEmpId;
    }

    public void setSubmitEmpId(Long submitEmpId) {
        this.submitEmpId = submitEmpId;
    }

    public String getFaultOrderNo() {
        return faultOrderNo;
    }

    public void setFaultOrderNo(String faultOrderNo) {
        this.faultOrderNo = faultOrderNo;
    }

    public Long getFaultOccurTime() {
        return faultOccurTime;
    }

    public void setFaultOccurTime(Long faultOccurTime) {
        this.faultOccurTime = faultOccurTime;
    }

    public String getFaultPlace() {
        return faultPlace;
    }

    public void setFaultPlace(String faultPlace) {
        this.faultPlace = faultPlace;
    }

    public String getFaultPhenomenon() {
        return faultPhenomenon;
    }

    public void setFaultPhenomenon(String faultPhenomenon) {
        this.faultPhenomenon = faultPhenomenon;
    }

    public String getCauseAnalysis() {
        return causeAnalysis;
    }

    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public Long getRepairTeamId() {
        return repairTeamId;
    }

    public void setRepairTeamId(Long repairTeamId) {
        this.repairTeamId = repairTeamId;
    }

    public String getRepairTeam() {
        return repairTeam;
    }

    public void setRepairTeam(String repairTeam) {
        this.repairTeam = repairTeam;
    }

    public Float getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Float repairCost) {
        this.repairCost = repairCost;
    }

    public String getRepairEmp() {
        return repairEmp;
    }

    public void setRepairEmp(String repairEmp) {
        this.repairEmp = repairEmp;
    }

    public String getRepairEmpId() {
        return repairEmpId;
    }

    public void setRepairEmpId(String repairEmpId) {
        this.repairEmpId = repairEmpId;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
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

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel;
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

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
}
