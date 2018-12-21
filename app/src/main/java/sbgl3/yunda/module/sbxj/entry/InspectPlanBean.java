package sbgl3.yunda.module.sbxj.entry;


/**
 * <li>标题: 机车检修整备管理信息系统
 * <li>说明: InspectPlan查询实体类，数据表：巡检计划
 * <li>创建人：何涛
 * <li>创建日期：2016年6月16日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */
public class InspectPlanBean implements java.io.Serializable {
    /** 使用默认序列版本ID */
    private static final long serialVersionUID = 1L;

    public static final String STATE_YCL = "已处理";
    public static final String STATE_WCL = "未处理";
    
    /** 主键 */
    private String idx;
    /** 巡检线路idx主键 */
    private String routeIdx;
    /** 巡检线路名称 */
    private String routeName;

    private String orgName;

    private String orgSeq;
    /** 巡检人 */
    private String partrolWorker;
    /** 巡检人ID */
    private Long partrolWorkerId;
    /** 委托巡检人 */
    private String entrustPartrolWorker;
    /** 委托巡检人ID */
    private Long entrustPartrolWorkerId;
    /** 巡检周期（周检，半月检，月检，季检，临时） */
    private String periodType;
    /** 计划开始日期 */
    private Long planStartDate;
    /** 计划结束日期 */
    private Long planEndDate;
    /** 处理状态：(未处理、已处理) */
    private String state;
    /** 未巡检完成的设备数 */
    private Integer wxjCount;
    /** 已巡检完成的设备数 */
    private Integer yxjCount;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgSeq() {
        return orgSeq;
    }

    public void setOrgSeq(String orgSeq) {
        this.orgSeq = orgSeq;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getRouteIdx() {
        return routeIdx;
    }

    public void setRouteIdx(String routeIdx) {
        this.routeIdx = routeIdx;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
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

    public String getEntrustPartrolWorker() {
        return entrustPartrolWorker;
    }

    public void setEntrustPartrolWorker(String entrustPartrolWorker) {
        this.entrustPartrolWorker = entrustPartrolWorker;
    }

    public Long getEntrustPartrolWorkerId() {
        return entrustPartrolWorkerId;
    }

    public void setEntrustPartrolWorkerId(Long entrustPartrolWorkerId) {
        this.entrustPartrolWorkerId = entrustPartrolWorkerId;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Long getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Long planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Long getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Long planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getWxjCount() {
        return wxjCount;
    }

    public void setWxjCount(Integer wxjCount) {
        this.wxjCount = wxjCount;
    }

    public Integer getYxjCount() {
        return yxjCount;
    }

    public void setYxjCount(Integer yxjCount) {
        this.yxjCount = yxjCount;
    }
}
