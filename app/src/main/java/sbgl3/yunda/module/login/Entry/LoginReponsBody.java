package sbgl3.yunda.module.login.Entry;

import java.io.Serializable;
import java.util.List;

import sbgl3.yunda.entry.UserInfo;

public class LoginReponsBody implements Serializable {

    /**
     * success : true
     * todoJob : [{"jobNum":1,"jobText":"待处理(1)","jobType":"安全风险项","jobTypeId":114,"jobUrl":"/view/jfgl/aq/SafetyRisk.jsp"},{"jobNum":5,"jobText":"待处理(5)","jobType":"故障提票","jobTypeId":50301,"jobUrl":"/view/repair/fault/tp/FaultOrder.jsp"},{"jobNum":23,"jobText":"待处理(23)","jobType":"维修待料信息","jobTypeId":505,"jobUrl":"/view/repair/jxdl/RepairWorkOrderStuff.jsp"},{"jobNum":1,"jobText":"待处理(1)","jobType":"计划检修","jobTypeId":3},{"jobNum":33,"jobText":"待处理(33)","jobType":"使用人确认","jobTypeId":50601,"jobUrl":"/view/repair/userconfirm/UserConfirm.jsp"},{"jobNum":5,"jobText":"未派工(5)","jobType":"提票调度派工","jobTypeId":50302,"jobUrl":"/view/repair/fault/ddpg/FaultOrder.jsp"},{"jobNum":1,"jobText":"待处理(1)","jobType":"验收人确认","jobTypeId":8,"jobUrl":"/view/repair/task/accept/RepairTaskList.jsp"}]
     * users : {"empid":747,"empname":"何云刚","operatorid":763,"operatorname":"何云刚","orgdegree":"tream","orgid":1851,"orglevel":4,"orgname":"管理组","orgseq":".0.18.1804.1864.1851.","parentorgid":1864,"userid":"50003"}
     */

    private boolean success;
    private UserInfo users;
    private List<TodoJobBean> todoJob;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserInfo getUsers() {
        return users;
    }

    public void setUsers(UserInfo users) {
        this.users = users;
    }

    public List<TodoJobBean> getTodoJob() {
        return todoJob;
    }

    public void setTodoJob(List<TodoJobBean> todoJob) {
        this.todoJob = todoJob;
    }

    public static class UsersBean {
        /**
         * empid : 747
         * empname : 何云刚
         * operatorid : 763
         * operatorname : 何云刚
         * orgdegree : tream
         * orgid : 1851
         * orglevel : 4
         * orgname : 管理组
         * orgseq : .0.18.1804.1864.1851.
         * parentorgid : 1864
         * userid : 50003
         */

        private int empid;
        private String empname;
        private int operatorid;
        private String operatorname;
        private String orgdegree;
        private int orgid;
        private int orglevel;
        private String orgname;
        private String orgseq;
        private int parentorgid;
        private String userid;

        public int getEmpid() {
            return empid;
        }

        public void setEmpid(int empid) {
            this.empid = empid;
        }

        public String getEmpname() {
            return empname;
        }

        public void setEmpname(String empname) {
            this.empname = empname;
        }

        public int getOperatorid() {
            return operatorid;
        }

        public void setOperatorid(int operatorid) {
            this.operatorid = operatorid;
        }

        public String getOperatorname() {
            return operatorname;
        }

        public void setOperatorname(String operatorname) {
            this.operatorname = operatorname;
        }

        public String getOrgdegree() {
            return orgdegree;
        }

        public void setOrgdegree(String orgdegree) {
            this.orgdegree = orgdegree;
        }

        public int getOrgid() {
            return orgid;
        }

        public void setOrgid(int orgid) {
            this.orgid = orgid;
        }

        public int getOrglevel() {
            return orglevel;
        }

        public void setOrglevel(int orglevel) {
            this.orglevel = orglevel;
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

        public int getParentorgid() {
            return parentorgid;
        }

        public void setParentorgid(int parentorgid) {
            this.parentorgid = parentorgid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }

    public static class TodoJobBean implements Serializable{
        /**
         * jobNum : 1
         * jobText : 待处理(1)
         * jobType : 安全风险项
         * jobTypeId : 114
         * jobUrl : /view/jfgl/aq/SafetyRisk.jsp
         */

        private Integer jobNum;
        private String jobText;
        private String jobType;
        private int jobTypeId;
        private String jobUrl;

        public Integer getJobNum() {
            return jobNum;
        }

        public void setJobNum(Integer jobNum) {
            this.jobNum = jobNum;
        }

        public String getJobText() {
            return jobText;
        }

        public void setJobText(String jobText) {
            this.jobText = jobText;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public int getJobTypeId() {
            return jobTypeId;
        }

        public void setJobTypeId(int jobTypeId) {
            this.jobTypeId = jobTypeId;
        }

        public String getJobUrl() {
            return jobUrl;
        }

        public void setJobUrl(String jobUrl) {
            this.jobUrl = jobUrl;
        }
    }
}
