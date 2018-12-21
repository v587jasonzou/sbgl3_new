package sbgl3.yunda.module.evaluate.entry;

import java.io.Serializable;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 设备评定--评定模板数据实体类
 * <li>创建人：刘欢
 * <li>创建日期：2018/9/12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class EquipmentEvaluatePlanBean implements Serializable {

    /** 主键 */
    private String idx;
    /** 计划idx主键 */
    private String planIdx;
    /** 实测整修前 */
    private String beforeMeasured;
    /** 实测整修后 */
    private String afterMeasured;
    /** 鉴/评定结果整修前 */
    private String beforeAppraise;
    /** 鉴/评定结果整修后 */
    private String afterAppraise;
    /** 项目编号 */
    private String itemNo;
    /** 项目名称 */
    private String itemName;
    /** 项目类型 */
    private Integer itemType;
    /** 检修标准甲 */
    private String repairStandardA;
    /** 检修标准乙 */
    private String repairStandardB;
    /** 检修标准丙 */
    private String repairStandardC;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getPlanIdx() {
        return planIdx;
    }

    public void setPlanIdx(String planIdx) {
        this.planIdx = planIdx;
    }

    public String getBeforeMeasured() {
        return beforeMeasured;
    }

    public void setBeforeMeasured(String beforeMeasured) {
        this.beforeMeasured = beforeMeasured;
    }

    public String getAfterMeasured() {
        return afterMeasured;
    }

    public void setAfterMeasured(String afterMeasured) {
        this.afterMeasured = afterMeasured;
    }

    public String getBeforeAppraise() {
        return beforeAppraise;
    }

    public void setBeforeAppraise(String beforeAppraise) {
        this.beforeAppraise = beforeAppraise;
    }

    public String getAfterAppraise() {
        return afterAppraise;
    }

    public void setAfterAppraise(String afterAppraise) {
        this.afterAppraise = afterAppraise;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getRepairStandardA() {
        return repairStandardA;
    }

    public void setRepairStandardA(String repairStandardA) {
        this.repairStandardA = repairStandardA;
    }

    public String getRepairStandardB() {
        return repairStandardB;
    }

    public void setRepairStandardB(String repairStandardB) {
        this.repairStandardB = repairStandardB;
    }

    public String getRepairStandardC() {
        return repairStandardC;
    }

    public void setRepairStandardC(String repairStandardC) {
        this.repairStandardC = repairStandardC;
    }

}