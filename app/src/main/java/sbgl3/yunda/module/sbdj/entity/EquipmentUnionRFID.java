package sbgl3.yunda.module.sbdj.entity;

import java.io.Serializable;

public class EquipmentUnionRFID implements Serializable {
    
    /**  类型：long  */
    private static final long serialVersionUID = 6416854121596998766L;

    /* idx主键 */
    private String idx;

    /* 设备类别编码 */
    private String classCode;
    
    /* 设备类别名称 */
    private String className;
    
    /* 设备名称 */
    private String equipmentName;
    
    /* 设备编码 */
    private String equipmentCode;
    
    /* 型号 */
    private String model;
    
    /* 规格 */
    private String specification;
    
    /* 设置地点 */
    private String usePlace;
    
    /* 设备编码（标识已关联RFID） */
    private String rfidCode;

    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getClassCode() {
        return classCode;
    }
    
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getEquipmentName() {
        return equipmentName;
    }
    
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    
    public String getEquipmentCode() {
        return equipmentCode;
    }
    
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getSpecification() {
        return specification;
    }
    
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
    public String getUsePlace() {
        return usePlace;
    }
    
    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace;
    }
    
    public String getRfidCode() {
        return rfidCode;
    }
    
    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }
}
