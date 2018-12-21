package sbgl3.yunda.module.fwhjhx.mvp.entity;

import java.io.Serializable;
import java.util.List;

public class RepairHistoryResponse implements Serializable {

    /**
     * historyRepairRecords : ["合格"]
     * success : true
     */

    private boolean success;
    private List<String> historyRepairRecords;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getHistoryRepairRecords() {
        return historyRepairRecords;
    }

    public void setHistoryRepairRecords(List<String> historyRepairRecords) {
        this.historyRepairRecords = historyRepairRecords;
    }
}
