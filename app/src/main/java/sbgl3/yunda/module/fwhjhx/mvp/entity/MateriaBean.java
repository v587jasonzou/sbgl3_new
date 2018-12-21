package sbgl3.yunda.module.fwhjhx.mvp.entity;

import java.io.Serializable;

/**
 * Created by 周雪巍 on 2018/6/20.
 * 物料信息实体
 */

public class MateriaBean implements Serializable{

    /**
     * stuffNumber : 6.0
     * waitStartTime : 1528196700000
     * waitEndTime : 1529559840000
     * idx : 8a82845f63ba2c2d0163ba2fef0d0001
     * updator : 763
     * updateTime : 1529646011000
     * repairWorkOrderIdx : 8a82845f63b931fc0163b94d09400005
     * stuffUnitPrice : 1.88
     * recordStatus : 0
     * stuffName : 毛刷
     * stuffNamePY : MS
     * stuffUnit : null
     * creator : 854
     * createTime : 1527837094000
     * stuffTotalMoney : 11.28
     */

    private Double stuffNumber;
    private Long waitStartTime;
    private Long waitEndTime;
    private String idx;
    private Long updator;
    private Long updateTime;
    private String repairWorkOrderIdx;
    private Double stuffUnitPrice;
    private int recordStatus;
    private String stuffName;
    private String stuffNamePY;
    private String stuffUnit;
    private Long creator;
    private Long createTime;
    private Double stuffTotalMoney;
    private boolean isEdit = false;
    private String status;
    private String stuffNumberStr;

    public String getStuffNumberStr() {
        return stuffNumberStr;
    }

    public void setStuffNumberStr(String stuffNumberStr) {
        this.stuffNumberStr = stuffNumberStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public Double getStuffNumber() {
        return stuffNumber;
    }

    public void setStuffNumber(Double stuffNumber) {
        this.stuffNumber = stuffNumber;
    }

    public Long getWaitStartTime() {
        return waitStartTime;
    }

    public void setWaitStartTime(Long waitStartTime) {
        this.waitStartTime = waitStartTime;
    }

    public Long getWaitEndTime() {
        return waitEndTime;
    }

    public void setWaitEndTime(Long waitEndTime) {
        this.waitEndTime = waitEndTime;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getRepairWorkOrderIdx() {
        return repairWorkOrderIdx;
    }

    public void setRepairWorkOrderIdx(String repairWorkOrderIdx) {
        this.repairWorkOrderIdx = repairWorkOrderIdx;
    }

    public Double getStuffUnitPrice() {
        return stuffUnitPrice;
    }

    public void setStuffUnitPrice(Double stuffUnitPrice) {
        this.stuffUnitPrice = stuffUnitPrice;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getStuffName() {
        return stuffName;
    }

    public void setStuffName(String stuffName) {
        this.stuffName = stuffName;
    }

    public String getStuffNamePY() {
        return stuffNamePY;
    }

    public void setStuffNamePY(String stuffNamePY) {
        this.stuffNamePY = stuffNamePY;
    }

    public String getStuffUnit() {
        return stuffUnit;
    }

    public void setStuffUnit(String stuffUnit) {
        this.stuffUnit = stuffUnit;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Double getStuffTotalMoney() {
        return stuffTotalMoney;
    }

    public void setStuffTotalMoney(Double stuffTotalMoney) {
        this.stuffTotalMoney = stuffTotalMoney;
    }
}
