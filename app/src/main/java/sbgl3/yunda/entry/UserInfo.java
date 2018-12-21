package sbgl3.yunda.entry;

import java.io.Serializable;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 登录用户信息
 * <li>创建人：何东
 * <li>创建日期：2016年6月17日
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 1.0
 */
public final class UserInfo implements Serializable {
    /** 选中 */
    public static final int CHECKED = 0;
    /** 未选中 */
    public static final int UNCHECKED = 1;
    
    private Long operatorid;
    private Long empid;
    private Long orgid;
    private String userid;
    private String operatorname;
    private String empname;
    private String orgname;
    private String orgseq;
    private Integer orglevel;
    private String orgdegree;
    private Long parentorgid;
    
    /** 是否勾选：0 勾选，1 未勾选 */
    private Integer checked  = UNCHECKED;
    
    public Long getOperatorid() {
        return operatorid;
    }
    
    public void setOperatorid(Long operatorid) {
        this.operatorid = operatorid;
    }
    
    public Long getEmpid() {
        return empid;
    }
    
    public void setEmpid(Long empid) {
        this.empid = empid;
    }
    
    public Long getOrgid() {
        return orgid;
    }
    
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getOperatorname() {
        return operatorname;
    }
    
    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }
    
    public String getEmpname() {
        return empname;
    }
    
    public void setEmpname(String empname) {
        this.empname = empname;
    }
    
    public String getOrgname() {
        return orgname;
    }
    
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    
    public String getOrgseq() {
        return orgseq;
    }
    
    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
    }
    
    public Integer getOrglevel() {
        return orglevel;
    }
    
    public void setOrglevel(Integer orglevel) {
        this.orglevel = orglevel;
    }
    
    public String getOrgdegree() {
        return orgdegree;
    }
    
    public void setOrgdegree(String orgdegree) {
        this.orgdegree = orgdegree;
    }
    
    public Long getParentorgid() {
        return parentorgid;
    }
    
    public void setParentorgid(Long parentorgid) {
        this.parentorgid = parentorgid;
    }
    
    public Integer getChecked() {
        return checked;
    }
    
    public void setChecked(Integer checked) {
        this.checked = checked;
    }
    
}
