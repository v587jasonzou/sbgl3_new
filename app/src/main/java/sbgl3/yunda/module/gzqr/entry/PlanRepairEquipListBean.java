package sbgl3.yunda.module.gzqr.entry;

/**
 * <li>标题: 设备管理信息系统
 * <li>说明: 计划修使用人确认列表实体类
 * <li>创建人：刘欢
 * <li>创建日期：2016年7月7日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class PlanRepairEquipListBean implements java.io.Serializable {

    /**
     * state : 待验收
     * userName : null
     * endTime : 2018-09-08
     * model : null
     * yclCount : 8
     * wclCount : 0
     * imageCount : 0
     * gzSignMac : 毕宏超
     * gzSignElc : 马新潮
     * repairTeamMac : 机修组
     * repairTeamElc : 电力组
     * acceptorId : 1506
     * planMonthIdx : 8aed6c2d60da11da01614613fe1d0fde
     * orgWclCount : 0
     * createTime : 1536829271000
     * userId : null
     * acceptanceReviews :
     * isNeedAcceptance : true
     * acceptanceTime : null
     * idx : 8a8284b165a384fc0165d229adba0475
     * equipmentIdx : 2a1d9ddc6f94445f9fb1829848edca76
     * orgId : .0.18.1804.1845.
     * planYear : null
     * equipmentCode : 241010009
     * orgName : 设备车间
     * equipmentName : 客机风源设备
     * planMonth : null
     * realEndTime : 1537165047000
     * realBeginTime : 1537163937000
     * usePerson : 何云刚
     * usePlace : 检修车间
     * specification : null
     * beginTime : 2018-09-07
     * acceptorName : 马新潮
     * repairClassName : 小
     * mechanicalCoefficient : 12.0
     * electricCoefficient : 15.0
     */

    private String state;
    private Object userName;
    private String endTime;
    private Object model;
    private int yclCount;
    private int wclCount;
    private int imageCount;
    private String gzSignMac;
    private String gzSignElc;
    private String repairTeamMac;
    private String repairTeamElc;
    private int acceptorId;
    private String planMonthIdx;
    private Integer orgWclCount;
    private long createTime;
    private Object userId;
    private String acceptanceReviews;
    private boolean isNeedAcceptance;
    private Object acceptanceTime;
    private String idx;
    private String equipmentIdx;
    private String orgId;
    private Object planYear;
    private String equipmentCode;
    private String orgName;
    private String equipmentName;
    private Object planMonth;
    private long realEndTime;
    private long realBeginTime;
    private String usePerson;
    private String usePlace;
    private Object specification;
    private String beginTime;
    private String acceptorName;
    private String repairClassName;
    private double mechanicalCoefficient;
    private double electricCoefficient;

    private boolean isConfirm;

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public int getYclCount() {
        return yclCount;
    }

    public void setYclCount(int yclCount) {
        this.yclCount = yclCount;
    }

    public int getWclCount() {
        return wclCount;
    }

    public void setWclCount(int wclCount) {
        this.wclCount = wclCount;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getGzSignMac() {
        return gzSignMac;
    }

    public void setGzSignMac(String gzSignMac) {
        this.gzSignMac = gzSignMac;
    }

    public String getGzSignElc() {
        return gzSignElc;
    }

    public void setGzSignElc(String gzSignElc) {
        this.gzSignElc = gzSignElc;
    }

    public String getRepairTeamMac() {
        return repairTeamMac;
    }

    public void setRepairTeamMac(String repairTeamMac) {
        this.repairTeamMac = repairTeamMac;
    }

    public String getRepairTeamElc() {
        return repairTeamElc;
    }

    public void setRepairTeamElc(String repairTeamElc) {
        this.repairTeamElc = repairTeamElc;
    }

    public int getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(int acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getPlanMonthIdx() {
        return planMonthIdx;
    }

    public void setPlanMonthIdx(String planMonthIdx) {
        this.planMonthIdx = planMonthIdx;
    }

    public Integer getOrgWclCount() {
        return orgWclCount;
    }

    public void setOrgWclCount(Integer orgWclCount) {
        this.orgWclCount = orgWclCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getAcceptanceReviews() {
        return acceptanceReviews;
    }

    public void setAcceptanceReviews(String acceptanceReviews) {
        this.acceptanceReviews = acceptanceReviews;
    }

    public boolean isIsNeedAcceptance() {
        return isNeedAcceptance;
    }

    public void setIsNeedAcceptance(boolean isNeedAcceptance) {
        this.isNeedAcceptance = isNeedAcceptance;
    }

    public Object getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Object acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Object getPlanYear() {
        return planYear;
    }

    public void setPlanYear(Object planYear) {
        this.planYear = planYear;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Object getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(Object planMonth) {
        this.planMonth = planMonth;
    }

    public long getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(long realEndTime) {
        this.realEndTime = realEndTime;
    }

    public long getRealBeginTime() {
        return realBeginTime;
    }

    public void setRealBeginTime(long realBeginTime) {
        this.realBeginTime = realBeginTime;
    }

    public String getUsePerson() {
        return usePerson;
    }

    public void setUsePerson(String usePerson) {
        this.usePerson = usePerson;
    }

    public String getUsePlace() {
        return usePlace;
    }

    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
    }

    public Object getSpecification() {
        return specification;
    }

    public void setSpecification(Object specification) {
        this.specification = specification;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public double getMechanicalCoefficient() {
        return mechanicalCoefficient;
    }

    public void setMechanicalCoefficient(double mechanicalCoefficient) {
        this.mechanicalCoefficient = mechanicalCoefficient;
    }

    public double getElectricCoefficient() {
        return electricCoefficient;
    }

    public void setElectricCoefficient(double electricCoefficient) {
        this.electricCoefficient = electricCoefficient;
    }

}
