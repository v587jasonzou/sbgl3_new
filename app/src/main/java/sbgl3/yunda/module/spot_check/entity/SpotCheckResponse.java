package sbgl3.yunda.module.spot_check.entity;

import java.io.Serializable;

public class SpotCheckResponse implements Serializable {
    Boolean success;
    PointCheck entity;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PointCheck getEntity() {
        return entity;
    }

    public void setEntity(PointCheck entity) {
        this.entity = entity;
    }
}
