package sbgl3.yunda.module.fwhjhx.mvp.entity;

import android.support.annotation.NonNull;

/**
 * <li>标题: 设备管理信息系统
 * <li>说明: RepairTaskList，数据表：检修任务单
 * <li>创建人：何涛
 * <li>创建日期：2016年7月7日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class RepairTaskList implements java.io.Serializable, Comparable<RepairTaskList> {

    public static final String ACCEPTANCE_REVIEWS_HG = "合格";

    public static final String ACCEPTANCE_REVIEWS_BHG = "不合格";

    /**
     * 处理状态 - 未处理
     */
    public static final String STATE_WCL = "未处理";

    /**
     * 处理状态 - 已处理
     */
    public static final String STATE_YCL = "已处理";

    /**
     * 修别 - 小
     */
    public static final String REPAIR_CLASS_NAME_SMALL = "小";

    /**
     * 修别 - 中
     */
    public static final String REPAIR_CLASS_NAME_MEDIUM = "中";

    /**
     * 修别 - 项
     */
    public static final String REPAIR_CLASS_NAME_SUBJECT = "项";

    /**
     * 选中
     */
    public static final int CHECKED = 0;
    /**
     * 未选中
     */
    public static final int UNCHECKED = 1;
    /**
     * 是否勾选：0 勾选，1 未勾选
     */
    private Integer checked = UNCHECKED;

    /**
     * 默认序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String idx;

    /**
     * 月计划主键
     */
    private String planMonthIdx;

    /**
     * 设备主键
     */
    private String equipmentIdx;

    /**
     * 修别
     */
    private String repairClassName;

    /**
     * 工长签名
     */
    private String gzSign;

    /**
     * 实际开工时间
     */
    private Long realBeginTime;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 验收人ID
     */
    private Long acceptorId;

    /**
     * 验收人名称
     */
    private String acceptorName;

    /**
     * 验收评语
     */
    private String acceptanceReviews;

    /**
     * 验收时间
     */
    private Long acceptanceTime;

    /**
     * 使用人ID
     */
    private Long userId;

    /**
     * 使用人名称
     */
    private String userName;

    /**
     * 处理状态：(未处理、已处理)
     */
    private String state;

    /**
     * 数据状态
     */
    private Integer recordStatus;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改人
     */
    private Long updator;

    /**
     * 修改时间
     */
    private Long updateTime;

    /* 设备编码 */
    private String equipmentCode;

    /* 设备名称 */
    private String equipmentName;

    /* 规格 */
    private String specification;

    /* 型号 */
    private String model;

    /* 设置地点(来源码表) */
    private String usePlace;

    /* 电气系数 */
    private Float electricCoefficient;

    /* 机械系数 */
    private Float mechanicalCoefficient;

    /* 未处理数量 */
    private Integer orgWclCount;

    private String UpdateStutes;

    public String getUpdateStutes() {
        return UpdateStutes;
    }

    public void setUpdateStutes(String updateStutes) {
        UpdateStutes = updateStutes;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getPlanMonthIdx() {
        return planMonthIdx;
    }

    public void setPlanMonthIdx(String planMonthIdx) {
        this.planMonthIdx = planMonthIdx;
    }

    public String getEquipmentIdx() {
        return equipmentIdx;
    }

    public void setEquipmentIdx(String equipmentIdx) {
        this.equipmentIdx = equipmentIdx;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(Long acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getAcceptanceReviews() {
        return acceptanceReviews;
    }

    public void setAcceptanceReviews(String acceptanceReviews) {
        this.acceptanceReviews = acceptanceReviews;
    }

    public Long getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Long acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getGzSign() {
        return gzSign;
    }

    public void setGzSign(String gzSign) {
        this.gzSign = gzSign;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUsePlace() {
        return usePlace;
    }

    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
    }

    public Float getElectricCoefficient() {
        return electricCoefficient;
    }

    public void setElectricCoefficient(Float electricCoefficient) {
        this.electricCoefficient = electricCoefficient;
    }

    public Float getMechanicalCoefficient() {
        return mechanicalCoefficient;
    }

    public void setMechanicalCoefficient(Float mechanicalCoefficient) {
        this.mechanicalCoefficient = mechanicalCoefficient;
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

    public Integer getOrgWclCount() {
        return orgWclCount;
    }

    public void setOrgWclCount(Integer orgWclCount) {
        this.orgWclCount = orgWclCount;
    }


    @Override
    public int compareTo(@NonNull RepairTaskList another) {
        //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
        if (Long.parseLong(this.equipmentCode) >= Long.parseLong(another.getEquipmentCode())) {
            return 1;
        }
        return -1;
    }
}
