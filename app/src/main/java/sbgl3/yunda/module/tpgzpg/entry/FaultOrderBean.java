package sbgl3.yunda.module.tpgzpg.entry;

import java.io.Serializable;

/**
 * <li>说明: FaultOrder实体类
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class FaultOrderBean implements Serializable {

    /**
     * state : 已派工
     * model : DZL6-1.25-AII
     * repairCost : null
     * faultLevel : 特大
     * operatorEmpid : 747
     * updator : 763
     * equipmentIdx : ea7e4c19d4d84b51809eed40bf92f729
     * businessIdx : null
     * equipmentCode : 214030007
     * equipmentName : 卧式快装蒸汽锅炉
     * equipmentType : 固资设备
     * updateTime : 1538986638000
     * recordStatus : 0
     * usePlace : null
     * specification : 6t/h  1.25MPa
     * faultPlace : 建立命名空间LOMO
     * submitEmp : 何云刚
     * backReason : null
     * repairTeam : 电力组
     * repairEmp : null
     * useWorkerId : null
     * useWorker : null
     * faultOrderNo : 201810080007
     * repairTeamId : 1850
     * submitEmpId : 747
     * submitOrgName : 设备车间/管理组
     * repairEmpId : null
     * repairContent : null
     * causeAnalysis : null
     * idx : 8a8284b1662324de016652bfdcf104de
     * faultOccurTime : 1538986512000
     * faultRecoverTime : null
     * faultPhenomenon : 库库马力军大将军Bruno
     * dispatchDateDD : 1538986638000
     * assistRepairTeam :
     * dispatchDateGZ : null
     * assistRepairEmps : null
     * businessEntity : null
     * operatorEmpname : 何云刚
     * assistRepairTeamId :
     * creator : 763
     * createTime : 1538986598000
     */

    private String state;
    private String model;
    private Object repairCost;
    private String faultLevel;
    private int operatorEmpid;
    private int updator;
    private String equipmentIdx;
    private Object businessIdx;
    private String equipmentCode;
    private String equipmentName;
    private String equipmentType;
    private long updateTime;
    private int recordStatus;
    private Object usePlace;
    private String specification;
    private String faultPlace;
    private String submitEmp;
    private Object backReason;
    private String repairTeam;
    private Object repairEmp;
    private Object useWorkerId;
    private Object useWorker;
    private String faultOrderNo;
    private int repairTeamId;
    private int submitEmpId;
    private String submitOrgName;
    private Object repairEmpId;
    private Object repairContent;
    private Object causeAnalysis;
    private String idx;
    private long faultOccurTime;
    private Object faultRecoverTime;
    private String faultPhenomenon;
    private long dispatchDateDD;
    private String assistRepairTeam;
    private Object dispatchDateGZ;
    private Object assistRepairEmps;
    private Object businessEntity;
    private String operatorEmpname;
    private String assistRepairTeamId;
    private int creator;
    private long createTime;
    /** 选中 */
    public static final int CHECKED = 0;
    /** 未选中 */
    public static final int UNCHECKED = 1;

    /** 是否勾选：0 勾选，1 未勾选 */
    private Integer checked  = UNCHECKED;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Object getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(Object repairCost) {
        this.repairCost = repairCost;
    }

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel;
    }

    public int getOperatorEmpid() {
        return operatorEmpid;
    }

    public void setOperatorEmpid(int operatorEmpid) {
        this.operatorEmpid = operatorEmpid;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public String getEquipmentIdx() {
        return equipmentIdx;
    }

    public void setEquipmentIdx(String equipmentIdx) {
        this.equipmentIdx = equipmentIdx;
    }

    public Object getBusinessIdx() {
        return businessIdx;
    }

    public void setBusinessIdx(Object businessIdx) {
        this.businessIdx = businessIdx;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Object getUsePlace() {
        return usePlace;
    }

    public void setUsePlace(Object usePlace) {
        this.usePlace = usePlace;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getFaultPlace() {
        return faultPlace;
    }

    public void setFaultPlace(String faultPlace) {
        this.faultPlace = faultPlace;
    }

    public String getSubmitEmp() {
        return submitEmp;
    }

    public void setSubmitEmp(String submitEmp) {
        this.submitEmp = submitEmp;
    }

    public Object getBackReason() {
        return backReason;
    }

    public void setBackReason(Object backReason) {
        this.backReason = backReason;
    }

    public String getRepairTeam() {
        return repairTeam;
    }

    public void setRepairTeam(String repairTeam) {
        this.repairTeam = repairTeam;
    }

    public Object getRepairEmp() {
        return repairEmp;
    }

    public void setRepairEmp(Object repairEmp) {
        this.repairEmp = repairEmp;
    }

    public Object getUseWorkerId() {
        return useWorkerId;
    }

    public void setUseWorkerId(Object useWorkerId) {
        this.useWorkerId = useWorkerId;
    }

    public Object getUseWorker() {
        return useWorker;
    }

    public void setUseWorker(Object useWorker) {
        this.useWorker = useWorker;
    }

    public String getFaultOrderNo() {
        return faultOrderNo;
    }

    public void setFaultOrderNo(String faultOrderNo) {
        this.faultOrderNo = faultOrderNo;
    }

    public int getRepairTeamId() {
        return repairTeamId;
    }

    public void setRepairTeamId(int repairTeamId) {
        this.repairTeamId = repairTeamId;
    }

    public int getSubmitEmpId() {
        return submitEmpId;
    }

    public void setSubmitEmpId(int submitEmpId) {
        this.submitEmpId = submitEmpId;
    }

    public String getSubmitOrgName() {
        return submitOrgName;
    }

    public void setSubmitOrgName(String submitOrgName) {
        this.submitOrgName = submitOrgName;
    }

    public Object getRepairEmpId() {
        return repairEmpId;
    }

    public void setRepairEmpId(Object repairEmpId) {
        this.repairEmpId = repairEmpId;
    }

    public Object getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(Object repairContent) {
        this.repairContent = repairContent;
    }

    public Object getCauseAnalysis() {
        return causeAnalysis;
    }

    public void setCauseAnalysis(Object causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public long getFaultOccurTime() {
        return faultOccurTime;
    }

    public void setFaultOccurTime(long faultOccurTime) {
        this.faultOccurTime = faultOccurTime;
    }

    public Object getFaultRecoverTime() {
        return faultRecoverTime;
    }

    public void setFaultRecoverTime(Object faultRecoverTime) {
        this.faultRecoverTime = faultRecoverTime;
    }

    public String getFaultPhenomenon() {
        return faultPhenomenon;
    }

    public void setFaultPhenomenon(String faultPhenomenon) {
        this.faultPhenomenon = faultPhenomenon;
    }

    public long getDispatchDateDD() {
        return dispatchDateDD;
    }

    public void setDispatchDateDD(long dispatchDateDD) {
        this.dispatchDateDD = dispatchDateDD;
    }

    public String getAssistRepairTeam() {
        return assistRepairTeam;
    }

    public void setAssistRepairTeam(String assistRepairTeam) {
        this.assistRepairTeam = assistRepairTeam;
    }

    public Object getDispatchDateGZ() {
        return dispatchDateGZ;
    }

    public void setDispatchDateGZ(Object dispatchDateGZ) {
        this.dispatchDateGZ = dispatchDateGZ;
    }

    public Object getAssistRepairEmps() {
        return assistRepairEmps;
    }

    public void setAssistRepairEmps(Object assistRepairEmps) {
        this.assistRepairEmps = assistRepairEmps;
    }

    public Object getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(Object businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getOperatorEmpname() {
        return operatorEmpname;
    }

    public void setOperatorEmpname(String operatorEmpname) {
        this.operatorEmpname = operatorEmpname;
    }

    public String getAssistRepairTeamId() {
        return assistRepairTeamId;
    }

    public void setAssistRepairTeamId(String assistRepairTeamId) {
        this.assistRepairTeamId = assistRepairTeamId;
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

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
