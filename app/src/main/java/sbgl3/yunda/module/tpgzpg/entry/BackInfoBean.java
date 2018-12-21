package sbgl3.yunda.module.tpgzpg.entry;

import java.io.Serializable;

/**
 * <li>说明: 退回详情实体类
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月09日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0.1
 */

public class BackInfoBean implements Serializable {

    /**
     * updator : 762
     * updateTime : 1534129793000
     * idx : 8a8284b16521b47e01653142e7200037
     * backPersonDuty : 工长
     * faultOrderIdx : 8a8284b164f95ff9016517459d050b05
     * backPerson : 张志勇
     * backPersonId : 746
     * assistRepairTeam : null
     * assistRepairEmps : null
     * assistRepairTeamId : null
     * backReason : 撒大声地
     * repairTeam : 管理组
     * repairEmp : null
     * repairTeamId : 1851
     * repairEmpId : null
     * recordStatus : 0
     * creator : 762
     * createTime : 1534129793000
     */

    private Integer updator;
    private Long updateTime;
    private String idx;
    private String backPersonDuty;
    private String faultOrderIdx;
    private String backPerson;
    private String backPersonId;
    private String assistRepairTeam;
    private Object assistRepairEmps;
    private String assistRepairTeamId;
    private String backReason;
    private String repairTeam;
    private String repairEmp;
    private Integer repairTeamId;
    private String repairEmpId;
    private Integer recordStatus;
    private Integer creator;
    private Long createTime;

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getBackPersonDuty() {
        return backPersonDuty;
    }

    public void setBackPersonDuty(String backPersonDuty) {
        this.backPersonDuty = backPersonDuty;
    }

    public String getFaultOrderIdx() {
        return faultOrderIdx;
    }

    public void setFaultOrderIdx(String faultOrderIdx) {
        this.faultOrderIdx = faultOrderIdx;
    }

    public String getBackPerson() {
        return backPerson;
    }

    public void setBackPerson(String backPerson) {
        this.backPerson = backPerson;
    }

    public String getBackPersonId() {
        return backPersonId;
    }

    public void setBackPersonId(String backPersonId) {
        this.backPersonId = backPersonId;
    }

    public String getAssistRepairTeam() {
        return assistRepairTeam;
    }

    public void setAssistRepairTeam(String assistRepairTeam) {
        this.assistRepairTeam = assistRepairTeam;
    }

    public Object getAssistRepairEmps() {
        return assistRepairEmps;
    }

    public void setAssistRepairEmps(Object assistRepairEmps) {
        this.assistRepairEmps = assistRepairEmps;
    }

    public String getAssistRepairTeamId() {
        return assistRepairTeamId;
    }

    public void setAssistRepairTeamId(String assistRepairTeamId) {
        this.assistRepairTeamId = assistRepairTeamId;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getRepairTeam() {
        return repairTeam;
    }

    public void setRepairTeam(String repairTeam) {
        this.repairTeam = repairTeam;
    }

    public String getRepairEmp() {
        return repairEmp;
    }

    public void setRepairEmp(String repairEmp) {
        this.repairEmp = repairEmp;
    }

    public Integer getRepairTeamId() {
        return repairTeamId;
    }

    public void setRepairTeamId(Integer repairTeamId) {
        this.repairTeamId = repairTeamId;
    }

    public String getRepairEmpId() {
        return repairEmpId;
    }

    public void setRepairEmpId(String repairEmpId) {
        this.repairEmpId = repairEmpId;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
