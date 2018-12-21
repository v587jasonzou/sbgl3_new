package sbgl3.yunda.module.fwhjhx.mvp.entity;

import com.jess.arms.base.entry.BaseResponsBean;

import java.io.Serializable;

public class WorkOrderResponse implements Serializable {

    /**
     * entity : {"idx":"8a8284b165e52d560165e6c2b8ae0036","scopeCaseIdx":"8a8284b165e52d560165e6c2b8aa0035","defineIdx":"8aed6c2d5a8204c5015a823e4d8a0048","sortNo":1,"workContent":"检查套筒移动丝杠状态；工艺标准：丝杠螺纹磨损不大于螺纹厚度的15％。","processStandard":"检查套筒移动丝杠状态；工艺标准：丝杠螺纹磨损不大于螺纹厚度的15％。","workerId":null,"workerName":null,"otherWorkerName":null,"processTime":null,"repairRecord":null,"color":null,"remark":null,"orderStatus":1,"recordStatus":0,"creator":763,"createTime":1537174846000,"updator":763,"updateTime":1537174846000}
     * success : true
     */

    private RepairWorkOrder entity;
    private boolean success;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public RepairWorkOrder getEntity() {
        return entity;
    }

    public void setEntity(RepairWorkOrder entity) {
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
