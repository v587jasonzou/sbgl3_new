package sbgl3.yunda.module.gztp.entry;

import java.io.Serializable;

public class ResponsEquip implements Serializable {
    private Boolean success;
    private EquipmentPrimaryInfo equipment;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public EquipmentPrimaryInfo getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentPrimaryInfo equipment) {
        this.equipment = equipment;
    }
}
