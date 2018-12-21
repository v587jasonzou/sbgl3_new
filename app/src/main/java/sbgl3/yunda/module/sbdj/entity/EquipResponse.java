package sbgl3.yunda.module.sbdj.entity;

import java.io.Serializable;

import sbgl3.yunda.entry.EquipInfoBean;

public class EquipResponse implements Serializable {
    private Boolean success;
    private  EquipInfoBean entity;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public EquipInfoBean getEntity() {
        return entity;
    }

    public void setEntity(EquipInfoBean entity) {
        this.entity = entity;
    }
}
