package sbgl3.yunda.module.userconfirm.entry;

import java.util.Date;

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
public class FaultOrderBean implements java.io.Serializable {
    /**
     * businessEntity : com.yunda.sbgl.check.entity.PointCheckContent
     * businessIdx : 8aed6c2d620425190163288b594d2d6d
     * causeAnalysis : 好好看看
     * createTime : 1527140570000
     * creator : 763
     * dispatchDateDD : 1528093184000
     * dispatchDateGZ : 1528093200000
     * equipmentCode : 018050003
     * equipmentIdx : 4ba29bceb95e4df7af0348832f28c141
     * equipmentName : 车轮车床
     * equipmentType : 固资设备
     * faultLevel : 一般
     * faultOccurTime : 1527140400000
     * faultOrderNo : 201805240002
     * faultPhenomenon : 不良
     * faultPlace : 无漏油、漏水、漏电现象。
     * faultRecoverTime : 1530849046000
     * idx : 402886e9638fb468016390abd23f0008
     * recordStatus : 0
     * repairContent : 废后将军
     * repairEmp : 王勇6
     * repairEmpId : 720
     * repairTeam : 管理组
     * repairTeamId : 1851
     * state : 已处理
     * submitEmp : 何云刚
     * submitEmpId : 747
     * updateTime : 1530849046000
     * updator : 737
     * usePlace : 轮对间
     */

    private String businessEntity;
    private String businessIdx;
    private String causeAnalysis;
    private long createTime;
    private int creator;
    private long dispatchDateDD;
    private long dispatchDateGZ;
    private String equipmentCode;
    private String equipmentIdx;
    private String equipmentName;
    private String equipmentType;
    private String faultLevel;
    private long faultOccurTime;
    private String faultOrderNo;
    private String faultPhenomenon;
    private String faultPlace;
    private long faultRecoverTime;
    private String idx;
    private int recordStatus;
    private String repairContent;
    private String repairEmp;
    private String repairEmpId;
    private String repairTeam;
    private int repairTeamId;
    private String state;
    private String submitEmp;
    private int submitEmpId;
    private long updateTime;
    private int updator;
    private String usePlace;
    private String model;
    private String specification;

    private boolean isConfirm;

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getBusinessIdx() {
        return businessIdx;
    }

    public void setBusinessIdx(String businessIdx) {
        this.businessIdx = businessIdx;
    }

    public String getCauseAnalysis() {
        return causeAnalysis;
    }

    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public long getDispatchDateDD() {
        return dispatchDateDD;
    }

    public void setDispatchDateDD(long dispatchDateDD) {
        this.dispatchDateDD = dispatchDateDD;
    }

    public long getDispatchDateGZ() {
        return dispatchDateGZ;
    }

    public void setDispatchDateGZ(long dispatchDateGZ) {
        this.dispatchDateGZ = dispatchDateGZ;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
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

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel;
    }

    public long getFaultOccurTime() {
        return faultOccurTime;
    }

    public void setFaultOccurTime(long faultOccurTime) {
        this.faultOccurTime = faultOccurTime;
    }

    public String getFaultOrderNo() {
        return faultOrderNo;
    }

    public void setFaultOrderNo(String faultOrderNo) {
        this.faultOrderNo = faultOrderNo;
    }

    public String getFaultPhenomenon() {
        return faultPhenomenon;
    }

    public void setFaultPhenomenon(String faultPhenomenon) {
        this.faultPhenomenon = faultPhenomenon;
    }

    public String getFaultPlace() {
        return faultPlace;
    }

    public void setFaultPlace(String faultPlace) {
        this.faultPlace = faultPlace;
    }

    public long getFaultRecoverTime() {
        return faultRecoverTime;
    }

    public void setFaultRecoverTime(long faultRecoverTime) {
        this.faultRecoverTime = faultRecoverTime;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
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

    public String getRepairTeam() {
        return repairTeam;
    }

    public void setRepairTeam(String repairTeam) {
        this.repairTeam = repairTeam;
    }

    public int getRepairTeamId() {
        return repairTeamId;
    }

    public void setRepairTeamId(int repairTeamId) {
        this.repairTeamId = repairTeamId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubmitEmp() {
        return submitEmp;
    }

    public void setSubmitEmp(String submitEmp) {
        this.submitEmp = submitEmp;
    }

    public int getSubmitEmpId() {
        return submitEmpId;
    }

    public void setSubmitEmpId(int submitEmpId) {
        this.submitEmpId = submitEmpId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public String getUsePlace() {
        return usePlace;
    }

    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
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
}
