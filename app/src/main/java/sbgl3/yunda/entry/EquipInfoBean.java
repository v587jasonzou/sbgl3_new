package sbgl3.yunda.entry;

import java.io.Serializable;
/**
 * <li>标题: 设备信息类
 * <li>说明: 设备信息相关数据类
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class EquipInfoBean implements Serializable {


    /**
     * buyDate : 189619200000
     * classCode : 02101
     * className : 立式钻床
     * createTime : 1485225832000
     * creator : 747
     * czState : 0
     * dynamic : 3
     * electricCoefficient : 6
     * electricRepairPerson : 黄颖安
     * electricRepairPersonId : 717
     * electricRepairTeam : 电力组
     * electricRepairTeamId : 1850
     * eletricTotalPower : 7.6
     * equipmentCode : 021010001
     * equipmentName : 立式钻床
     * fcState : 0
     * fixedAssetNo : 070102-a002
     * fixedAssetValue : 35889.88
     * fixedEquipment : true
     * idx : 8802de6132844584a0a9550d1e388663
     * isDedicated : 0
     * isExactness : 0
     * isFrock : 0
     * isPrimaryDevice : 1
     * isSpecialType : 0
     * leaveFactoryNo : 75066
     * makeDate : 181324800000
     * makeFactory : 大河机床厂
     * manageClass : B
     * manageLevel : 段级
     * mechanicalCoefficient : 13
     * mechanicalRepairPerson : 赵晓隆
     * mechanicalRepairPersonId : 728
     * mechanicalRepairTeam : 机修组
     * mechanicalRepairTeamId : 1853
     * model : Z575A
     * orgId : .0.18.1804.
     * orgName : 安康机务段
     * recordStatus : 0
     * remark :
     * runingShifts : 0
     * shapeSize : 1550X800X3085mm
     * specification : Φ75
     * tag : 1
     * tecLevel : 1
     * updateTime : 1530153041000
     * updator : 854
     * useDate : 299606400000
     * usePerson : 邓志斌
     * usePersonId : 918
     * usePlace : 机床间
     * useWorkshop : 检修车间
     * useWorkshopId : 1871
     * weight : 2.8
     * xzState : 1
     */

    private Long buyDate;
    private String classCode;
    private String className;
    private Long createTime;
    private Integer creator;
    private Integer czState;
    private Integer dynamic;
    private Integer electricCoefficient;
    private String electricRepairPerson;
    private String electricRepairPersonId;
    private String electricRepairTeam;
    private String electricRepairTeamId;
    private String eletricTotalPower;
    private String equipmentCode;
    private String equipmentName;
    private Integer fcState;
    private String fixedAssetNo;
    private Float fixedAssetValue;
    private Boolean fixedEquipment;
    private String idx;
    private Integer isDedicated;
    private Integer isExactness;
    private Integer isFrock;
    private Integer isPrimaryDevice;
    private Integer isSpecialType;
    private String leaveFactoryNo;
    private Long makeDate;
    private String makeFactory;
    private String manageClass;
    private String manageLevel;
    private Integer mechanicalCoefficient;
    private String mechanicalRepairPerson;
    private String mechanicalRepairPersonId;
    private String mechanicalRepairTeam;
    private String mechanicalRepairTeamId;
    private String model;
    private String orgId;
    private String orgName;
    private Integer recordStatus;
    private String remark;
    private Integer runingShifts;
    private String shapeSize;
    private String specification;
    private String tag;
    private String tecLevel;
    private Long updateTime;
    private Integer updator;
    private Long useDate;
    private String usePerson;
    private String usePersonId;
    private String usePlace;
    private String useWorkshop;
    private String useWorkshopId;
    private Float weight;
    private Integer xzState;

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getCzState() {
        return czState;
    }

    public void setCzState(Integer czState) {
        this.czState = czState;
    }

    public Integer getDynamic() {
        return dynamic;
    }

    public void setDynamic(Integer dynamic) {
        this.dynamic = dynamic;
    }

    public Integer getElectricCoefficient() {
        return electricCoefficient;
    }

    public void setElectricCoefficient(Integer electricCoefficient) {
        this.electricCoefficient = electricCoefficient;
    }

    public String getElectricRepairPerson() {
        return electricRepairPerson;
    }

    public void setElectricRepairPerson(String electricRepairPerson) {
        this.electricRepairPerson = electricRepairPerson;
    }

    public String getElectricRepairPersonId() {
        return electricRepairPersonId;
    }

    public void setElectricRepairPersonId(String electricRepairPersonId) {
        this.electricRepairPersonId = electricRepairPersonId;
    }

    public String getElectricRepairTeam() {
        return electricRepairTeam;
    }

    public void setElectricRepairTeam(String electricRepairTeam) {
        this.electricRepairTeam = electricRepairTeam;
    }

    public String getElectricRepairTeamId() {
        return electricRepairTeamId;
    }

    public void setElectricRepairTeamId(String electricRepairTeamId) {
        this.electricRepairTeamId = electricRepairTeamId;
    }

    public String getEletricTotalPower() {
        return eletricTotalPower;
    }

    public void setEletricTotalPower(String eletricTotalPower) {
        this.eletricTotalPower = eletricTotalPower;
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

    public Integer getFcState() {
        return fcState;
    }

    public void setFcState(Integer fcState) {
        this.fcState = fcState;
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

    public Boolean isFixedEquipment() {
        return fixedEquipment;
    }

    public void setFixedEquipment(Boolean fixedEquipment) {
        this.fixedEquipment = fixedEquipment;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Integer getIsDedicated() {
        return isDedicated;
    }

    public void setIsDedicated(Integer isDedicated) {
        this.isDedicated = isDedicated;
    }

    public Integer getIsExactness() {
        return isExactness;
    }

    public void setIsExactness(Integer isExactness) {
        this.isExactness = isExactness;
    }

    public Integer getIsFrock() {
        return isFrock;
    }

    public void setIsFrock(Integer isFrock) {
        this.isFrock = isFrock;
    }

    public Integer getIsPrimaryDevice() {
        return isPrimaryDevice;
    }

    public void setIsPrimaryDevice(Integer isPrimaryDevice) {
        this.isPrimaryDevice = isPrimaryDevice;
    }

    public Integer getIsSpecialType() {
        return isSpecialType;
    }

    public void setIsSpecialType(Integer isSpecialType) {
        this.isSpecialType = isSpecialType;
    }

    public String getLeaveFactoryNo() {
        return leaveFactoryNo;
    }

    public void setLeaveFactoryNo(String leaveFactoryNo) {
        this.leaveFactoryNo = leaveFactoryNo;
    }

    public Long getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(Long makeDate) {
        this.makeDate = makeDate;
    }

    public String getMakeFactory() {
        return makeFactory;
    }

    public void setMakeFactory(String makeFactory) {
        this.makeFactory = makeFactory;
    }

    public String getManageClass() {
        return manageClass;
    }

    public void setManageClass(String manageClass) {
        this.manageClass = manageClass;
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel;
    }

    public Integer getMechanicalCoefficient() {
        return mechanicalCoefficient;
    }

    public void setMechanicalCoefficient(Integer mechanicalCoefficient) {
        this.mechanicalCoefficient = mechanicalCoefficient;
    }

    public String getMechanicalRepairPerson() {
        return mechanicalRepairPerson;
    }

    public void setMechanicalRepairPerson(String mechanicalRepairPerson) {
        this.mechanicalRepairPerson = mechanicalRepairPerson;
    }

    public String getMechanicalRepairPersonId() {
        return mechanicalRepairPersonId;
    }

    public void setMechanicalRepairPersonId(String mechanicalRepairPersonId) {
        this.mechanicalRepairPersonId = mechanicalRepairPersonId;
    }

    public String getMechanicalRepairTeam() {
        return mechanicalRepairTeam;
    }

    public void setMechanicalRepairTeam(String mechanicalRepairTeam) {
        this.mechanicalRepairTeam = mechanicalRepairTeam;
    }

    public String getMechanicalRepairTeamId() {
        return mechanicalRepairTeamId;
    }

    public void setMechanicalRepairTeamId(String mechanicalRepairTeamId) {
        this.mechanicalRepairTeamId = mechanicalRepairTeamId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRuningShifts() {
        return runingShifts;
    }

    public void setRuningShifts(Integer runingShifts) {
        this.runingShifts = runingShifts;
    }

    public String getShapeSize() {
        return shapeSize;
    }

    public void setShapeSize(String shapeSize) {
        this.shapeSize = shapeSize;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTecLevel() {
        return tecLevel;
    }

    public void setTecLevel(String tecLevel) {
        this.tecLevel = tecLevel;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Long getUseDate() {
        return useDate;
    }

    public void setUseDate(Long useDate) {
        this.useDate = useDate;
    }

    public String getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }

    public String getUsePersonId() {
        return usePersonId;
    }

    public void setUsePersonId(String usePersonId) {
        this.usePersonId = usePersonId;
    }

    public String getUsePlace() {
        return usePlace;
    }

    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
    }

    public String getUseWorkshop() {
        return useWorkshop;
    }

    public void setUseWorkshop(String useWorkshop) {
        this.useWorkshop = useWorkshop;
    }

    public String getUseWorkshopId() {
        return useWorkshopId;
    }

    public void setUseWorkshopId(String useWorkshopId) {
        this.useWorkshopId = useWorkshopId;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getXzState() {
        return xzState;
    }

    public void setXzState(Integer xzState) {
        this.xzState = xzState;
    }
}
