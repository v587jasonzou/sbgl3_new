package sbgl3.yunda.module.gztp.entry;

/**
 * <li>标题: 设备管理系统
 * <li>说明：EquipmentPrimaryInfo实体类, 数据表：设备主要信息
 * <li>创建人：王利成
 * <li>创建日期：2015-05-21
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0
 */
public class EquipmentPrimaryInfo implements java.io.Serializable {
    
    /* 使用默认序列版本ID */
    private static final long serialVersionUID = 1L;
    
    public static final int FIXEDASSETVALUE_DEFAULT = 5000;
    
    /** 设备动态-调入1 */
    public static final int DYNAMIC_IN = 1;
    
    /** 设备动态-调出2 */
    public static final int DYNAMIC_OUT = 2;
    
    /** 设备动态-新购3 */
    public static final int DYNAMIC_NEW_BUY = 3;
    
    /** 设备动态-报废4 */
    public static final int DYNAMIC_SCRAPPED = 4;
    
    /** 设备动态-出租5 */
    public static final int DYNAMIC_RENT = 5;
    
    /** 设备动态-租用6 */
    public static final int DYNAMIC_HIRE = 6;
    
    /* 添加动态时建议更新Dynamic.js里的dynamic方法 */
    
    /* idx主键 */
    private String idx;
    
    /* 单位名称 */
    private String orgName;
    
    /* 单位ID */
    private String orgId;
    
    /* 设备类别编码 */
    private String classCode;
    
    /* 设备类别名称 */
    private String className;
    
    /* 设备名称 */
    private String equipmentName;
    
    /* 设备编码 */
    private String equipmentCode;
    
    /* 购入日期 */
    private Long buyDate;
    
    /* 固资编号 */
    private String fixedAssetNo;
    
    /* 型号 */
    private String model;
    
    /* 规格 */
    private String specification;
    
    /* 机械系数 */
    private Integer mechanicalCoefficient;
    
    /* 电气系数 */
    private Integer electricCoefficient;
    
    /* 制造工厂 */
    private String makeFactory;
    
    /* 制造年月 */
    private Long makeDate;
    
    /* 固资原值 */
    private Float fixedAssetValue;
    
    /* 使用年月 */
    private Long useDate;
    
    /* 设置地点(来源码表) */
    private String usePlace;
    
    /* 管理级别 */
    private String manageLevel;
    
    /* 管理类别 */
    private String manageClass;
    
    /* 重量 */
    private Float weight;
    
    /* 最大修年度 */
    private Integer maxRepairYear;
    
    /* 出厂编号 */
    private String leaveFactoryNo;
    
    /* 电气总功率 */
    private String eletricTotalPower;
    
    /* 技术等级 */
    private String tecLevel;
    
    /* 技术状态 */
    private String tecstatus;
    
    /* 外形尺寸 */
    private String shapeSize;
    
    /* 是否主设备 */
    private Integer isPrimaryDevice;
    
    /* 是否专用设备 */
    private Integer isDedicated;
    
    /* 是否特种设备 */
    private Integer isSpecialType;
    
    /* 是否大精设备 */
    private Integer isExactness;
    
    /* 是否工装设备 */
    private Integer isFrock;
    
    /* 设备动态(来源码表) */
    private Integer dynamic;
    
    /* 封存状态(来源码表) */
    private Integer fcState;
    
    /* 闲置状态(来源码表) */
    private Integer xzState;
    
    /* 出租状态(来源码表) */
    private Integer czState;
    
    /* 设备运行班制 */
    private Integer runingShifts;
    
    /* 备注 */
    private String remark;
    
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
    
    /*------设备两定三包临时字段----------*/
    /* 使用车间 */
    private String useWorkshopId;
    
    private String useWorkshop;
    
    /* 使用人 */
    private String usePersonId;
    
    private String usePerson;
    
    /* 机械维修人 */
    private String mechanicalRepairPersonId;
    
    private String mechanicalRepairPerson;
    
    /* 电气维修人 */
    private String electricRepairPersonId;
    
    private String electricRepairPerson;
    
    /* 电气维修班组 */
    private String electricRepairTeamId;
    
    private String electricRepairTeam;
    
    /* 机械维修班组 */
    private String mechanicalRepairTeamId;
    
    private String mechanicalRepairTeam;
    
    /* 制造年度 */
    private String makeYear; 
    
    /* 使用年度 */
    private String useYear;
    
    /* 是否新购调用 */
    private boolean newBuyInvoke = false;
    
    /** 设备净值查询页面 **/
    /* 固资净值 */
    private Float netValue;
    /*是设备类型：1：固资设备，2：非固资设备*/
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Float getNetValue() {
        return netValue;
    }
    
    public void setNetValue(Float netValue) {
        this.netValue = netValue;
    }
    
    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getOrgId() {
        return orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * @return String 获取设备类别编码
     */
    public String getClassCode() {
        return classCode;
    }
    
    /**
     * @param equipmentClassCode 设置设备类别编码
     */
    public void setClassCode(String equipmentClassCode) {
        this.classCode = equipmentClassCode;
    }
    
    /**
     * @return String 获取设备名称
     */
    public String getEquipmentName() {
        return equipmentName;
    }
    
    /**
     * @param equipmentName 设置设备名称
     */
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    
    /**
     * @return String 获取设备编码
     */
    public String getEquipmentCode() {
        return equipmentCode;
    }
    
    /**
     * @param equipmentCode 设置设备编码
     */
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    
    /**
     * @return String 获取固资编号
     */
    public String getFixedAssetNo() {
        return fixedAssetNo;
    }
    
    /**
     * @param fixedAssetNo 设置固资编号
     */
    public void setFixedAssetNo(String fixedAssetNo) {
        this.fixedAssetNo = fixedAssetNo;
    }
    
    /**
     * @return String 获取型号
     */
    public String getModel() {
        return model;
    }
    
    /**
     * @param modal 设置型号
     */
    public void setModel(String modal) {
        this.model = modal;
    }
    
    /**
     * @return String 获取规格
     */
    public String getSpecification() {
        return specification;
    }
    
    /**
     * @param specification 设置规格
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
    /**
     * @return Float 获取机械系数
     */
    public Integer getMechanicalCoefficient() {
        return mechanicalCoefficient;
    }
    
    /**
     * @param mechanicalCoefficient 设置机械系数
     */
    public void setMechanicalCoefficient(Integer mechanicalCoefficient) {
        this.mechanicalCoefficient = mechanicalCoefficient;
    }
    
    /**
     * @return Float 获取电气系数
     */
    public Integer getElectricCoefficient() {
        return electricCoefficient;
    }
    
    /**
     * @param electricCoefficient 设置电气系数
     */
    public void setElectricCoefficient(Integer electricCoefficient) {
        this.electricCoefficient = electricCoefficient;
    }
    
    /**
     * @return String 获取制造工厂
     */
    public String getMakeFactory() {
        return makeFactory;
    }
    
    /**
     * @param makeFactory 设置制造工厂
     */
    public void setMakeFactory(String makeFactory) {
        this.makeFactory = makeFactory;
    }
    
    /**
     * @return java.util.Date 获取制造年月
     */
    public Long getMakeDate() {
        return makeDate;
    }
    
    /**
     * @param makedate 设置制造年月
     */
    public void setMakeDate(Long makedate) {
        this.makeDate = makedate;
    }
    
    /**
     * @return Integer 获取固资原值
     */
    public Float getFixedAssetValue() {
        return fixedAssetValue;
    }
    
    /**
     * @param fixedAssetValue 设置固资原值
     */
    public void setFixedAssetValue(Float fixedAssetValue) {
        this.fixedAssetValue = fixedAssetValue;
    }
    
    /**
     * @return java.util.Date 获取使用年月
     */
    public Long getUseDate() {
        return useDate;
    }
    
    /**
     * @param useDate 设置使用年月
     */
    public void setUseDate(Long useDate) {
        this.useDate = useDate;
    }
    
    /**
     * @return String 获取设置地点
     */
    public String getUsePlace() {
        return usePlace;
    }
    
    /**
     * @param usePlace 设置设置地点
     */
    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
    }
    
    /**
     * @return String 获取管理级别
     */
    public String getManageLevel() {
        return manageLevel;
    }
    
    /**
     * @param manageLevel 设置管理级别
     */
    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel;
    }
    
    /**
     * @return String 获取管理类别
     */
    public String getManageClass() {
        return manageClass;
    }
    
    /**
     * @param manageClass 设置管理类别
     */
    public void setManageClass(String manageClass) {
        this.manageClass = manageClass;
    }
    
    /**
     * @return Integer 获取重量
     */
    public Float getWeight() {
        return weight;
    }
    
    /**
     * @param weight 设置重量
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }
    
    /**
     * @return Integer 获取最大修年度
     */
    public Integer getMaxRepairYear() {
        return maxRepairYear;
    }
    
    /**
     * @param maxRepairYear 设置最大修年度
     */
    public void setMaxRepairYear(Integer maxRepairYear) {
        this.maxRepairYear = maxRepairYear;
    }
    
    /**
     * @return String 获取出厂编号
     */
    public String getLeaveFactoryNo() {
        return leaveFactoryNo;
    }
    
    /**
     * @param leaveFactoryNo 设置出厂编号
     */
    public void setLeaveFactoryNo(String leaveFactoryNo) {
        this.leaveFactoryNo = leaveFactoryNo;
    }
    
    /**
     * @return String 获取电气总功率
     */
    public String getEletricTotalPower() {
        return eletricTotalPower;
    }
    
    /**
     * @param eletricTotalPower 设置电气总功率
     */
    public void setEletricTotalPower(String eletricTotalPower) {
        this.eletricTotalPower = eletricTotalPower;
    }
    
    /**
     * @return String 获取技术等级
     */
    public String getTecLevel() {
        return tecLevel;
    }
    
    /**
     * @param tecLevel 设置技术等级
     */
    public void setTecLevel(String tecLevel) {
        this.tecLevel = tecLevel;
    }
    
    /**
     * @return String 获取外形尺寸
     */
    public String getShapeSize() {
        return shapeSize;
    }
    
    /**
     * @param shapeSize 设置外形尺寸
     */
    public void setShapeSize(String shapeSize) {
        this.shapeSize = shapeSize;
    }
    
    /**
     * @return Integer 获取是否主设备
     */
    public Integer getIsPrimaryDevice() {
        return isPrimaryDevice;
    }
    
    /**
     * @param isprimarydevice 设置是否主设备
     */
    public void setIsPrimaryDevice(Integer isprimarydevice) {
        this.isPrimaryDevice = isprimarydevice;
    }
    
    /**
     * @return Integer 获取是否专用设备
     */
    public Integer getIsDedicated() {
        return isDedicated;
    }
    
    /**
     * @param isdedicated 设置是否专用设备
     */
    public void setIsDedicated(Integer isdedicated) {
        this.isDedicated = isdedicated;
    }
    
    /**
     * @return Integer 获取是否特种设备
     */
    public Integer getIsspecialtype() {
        return isSpecialType;
    }
    
    /**
     * @param isspecialtype 设置是否特种设备
     */
    public void setIsspecialtype(Integer isspecialtype) {
        this.isSpecialType = isspecialtype;
    }
    
    /**
     * @return Integer 获取是否大精设备
     */
    public Integer getIsExactness() {
        return isExactness;
    }
    
    /**
     * @param isexactness 设置是否大精设备
     */
    public void setIsExactness(Integer isexactness) {
        this.isExactness = isexactness;
    }
    
    /**
     * @return Integer 获取是否工装设备
     */
    public Integer getIsFrock() {
        return isFrock;
    }
    
    /**
     * @param isFrock 设置是否工装设备
     */
    public void setIsFrock(Integer isFrock) {
        this.isFrock = isFrock;
    }
    
    /**
     * @return Integer 获取设备动态
     */
    public Integer getDynamic() {
        return dynamic;
    }
    
    /**
     * @param dynamic 设置设备动态
     */
    public void setDynamic(Integer dynamic) {
        this.dynamic = dynamic;
    }
    
    /**
     * @return String 获取备注
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param remark 设置备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return Integer 获取数据状态
     */
    public Integer getRecordStatus() {
        return recordStatus;
    }
    
    /**
     * @param recordStatus 设置数据状态
     */
    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }
    
    /**
     * @return Long 获取创建人
     */
    public Long getCreator() {
        return creator;
    }
    
    /**
     * @param creator 设置创建人
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }
    
    /**
     * @return java.util.Date 获取创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }
    
    /**
     * @param createTime 设置创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    /**
     * @return Long 获取修改人
     */
    public Long getUpdator() {
        return updator;
    }
    
    /**
     * @param updator 设置修改人
     */
    public void setUpdator(Long updator) {
        this.updator = updator;
    }
    
    /**
     * @return java.util.Date 获取修改时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }
    
    /**
     * @param updateTime 设置修改时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * @return String idx主键
     */
    public String getIdx() {
        return idx;
    }
    
    /**
     * @param idx 设置idx主键
     */
    public void setIdx(String idx) {
        this.idx = idx;
    }
    
    public Integer getRuningShifts() {
        return runingShifts;
    }
    
    public void setRuningShifts(Integer runingShifts) {
        this.runingShifts = runingShifts;
    }
    
    public String getUseWorkshopId() {
        return useWorkshopId;
    }
    
    public void setUseWorkshopId(String useWorkshopId) {
        this.useWorkshopId = useWorkshopId;
    }
    
    public String getUseWorkshop() {
        return useWorkshop;
    }
    
    public void setUseWorkshop(String useWorkshop) {
        this.useWorkshop = useWorkshop;
    }
    
    public String getUsePersonId() {
        return usePersonId;
    }
    
    public void setUsePersonId(String usePersonId) {
        this.usePersonId = usePersonId;
    }
    
    public String getUsePerson() {
        return usePerson;
    }
    
    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }
    
    public String getMechanicalRepairPerson() {
        return mechanicalRepairPerson;
    }
    
    public void setMechanicalRepairPerson(String mechanicalRepairPeson) {
        this.mechanicalRepairPerson = mechanicalRepairPeson;
    }
    
    public String getMechanicalRepairPersonId() {
        return mechanicalRepairPersonId;
    }
    
    public void setMechanicalRepairPersonId(String mechanicalRepairPersonId) {
        this.mechanicalRepairPersonId = mechanicalRepairPersonId;
    }
    
    public String getElectricRepairPersonId() {
        return electricRepairPersonId;
    }
    
    public void setElectricRepairPersonId(String electricRepairPersonId) {
        this.electricRepairPersonId = electricRepairPersonId;
    }
    
    public String getElectricRepairPerson() {
        return electricRepairPerson;
    }
    
    public void setElectricRepairPerson(String electricRepairPeson) {
        this.electricRepairPerson = electricRepairPeson;
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
    
    public String getMechanicalRepairTeamId() {
        return mechanicalRepairTeamId;
    }
    
    public void setMechanicalRepairTeamId(String mechanicalRepairTeamId) {
        this.mechanicalRepairTeamId = mechanicalRepairTeamId;
    }
    
    public String getMechanicalRepairTeam() {
        return mechanicalRepairTeam;
    }
    
    public void setMechanicalRepairTeam(String mechanicalRepairTeam) {
        this.mechanicalRepairTeam = mechanicalRepairTeam;
    }
    
    public Integer getIsSpecialType() {
        return isSpecialType;
    }
    
    public void setIsSpecialType(Integer isSpecialType) {
        this.isSpecialType = isSpecialType;
    }
    
    public String getMakeYear() {
        return makeYear;
    }
    
    public void setMakeYear(String makeYear) {
        this.makeYear = makeYear;
    }
    
    public String getUseYear() {
        return useYear;
    }
    
    public void setUseYear(String useYear) {
        this.useYear = useYear;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getTecstatus() {
        return tecstatus;
    }
    
    public void setTecstatus(String tecstatus) {
        this.tecstatus = tecstatus;
    }
    
    public Integer getFcState() {
        return fcState;
    }
    
    public void setFcState(Integer fcState) {
        this.fcState = fcState;
    }
    
    public Integer getXzState() {
        return xzState;
    }
    
    public void setXzState(Integer xzState) {
        this.xzState = xzState;
    }
    
    public Integer getCzState() {
        return czState;
    }
    
    public void setCzState(Integer czState) {
        this.czState = czState;
    }
    
    public Long getBuyDate() {
        return buyDate;
    }
    
    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }
    
    public boolean isNewBuyInvoke() {
        return newBuyInvoke;
    }
    
    public void setNewBuyInvoke(boolean newBuyInvoke) {
        this.newBuyInvoke = newBuyInvoke;
    }
}
