package sbgl3.yunda.module.fwhjhx.mvp.entity;

import java.io.Serializable;

/**
 * Created by weiwei on 2018/7/3.
 */

public class StuffParams implements Serializable {
    private String idx;//idx主键，
    private String stuffName;//材料名称规格，
    private Double stuffNumber;//数量，
    private Double stuffUnitPrice;//单价，
    private Double stuffTotalMoney;//金额，
    private String waitStartTime;//待料开始时间，
    private String waitEndTime;//待料结束时间

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getStuffName() {
        return stuffName;
    }

    public void setStuffName(String stuffName) {
        this.stuffName = stuffName;
    }

    public Double getStuffNumber() {
        return stuffNumber;
    }

    public void setStuffNumber(Double stuffNumber) {
        this.stuffNumber = stuffNumber;
    }

    public Double getStuffUnitPrice() {
        return stuffUnitPrice;
    }

    public void setStuffUnitPrice(Double stuffUnitPrice) {
        this.stuffUnitPrice = stuffUnitPrice;
    }

    public Double getStuffTotalMoney() {
        return stuffTotalMoney;
    }

    public void setStuffTotalMoney(Double stuffTotalMoney) {
        this.stuffTotalMoney = stuffTotalMoney;
    }

    public String getWaitStartTime() {
        return waitStartTime;
    }

    public void setWaitStartTime(String waitStartTime) {
        this.waitStartTime = waitStartTime;
    }

    public String getWaitEndTime() {
        return waitEndTime;
    }

    public void setWaitEndTime(String waitEndTime) {
        this.waitEndTime = waitEndTime;
    }
}
