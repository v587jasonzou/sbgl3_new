package sbgl3.yunda.module.main.Entry;

import java.io.Serializable;

public class MenuBean implements Serializable {

    /**
     * appid : 0
     * funcaction :
     * funccode : SBTJDJ
     * funcdesc :
     * funcgroupid : 3000
     * funcname : 设备停机登记
     * functype : 0
     * ischeck :
     * ismenu : y
     * parainfo :
     */

    private int appid;
    private String funcaction;
    private String funccode;
    private String funcdesc;
    private int funcgroupid;
    private String funcname;
    private String functype;
    private String ischeck;
    private String ismenu;
    private String parainfo;
    private Integer todoNum;

    public Integer getTodoNum() {
        return todoNum;
    }

    public void setTodoNum(Integer todoNum) {
        this.todoNum = todoNum;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getFuncaction() {
        return funcaction;
    }

    public void setFuncaction(String funcaction) {
        this.funcaction = funcaction;
    }

    public String getFunccode() {
        return funccode;
    }

    public void setFunccode(String funccode) {
        this.funccode = funccode;
    }

    public String getFuncdesc() {
        return funcdesc;
    }

    public void setFuncdesc(String funcdesc) {
        this.funcdesc = funcdesc;
    }

    public int getFuncgroupid() {
        return funcgroupid;
    }

    public void setFuncgroupid(int funcgroupid) {
        this.funcgroupid = funcgroupid;
    }

    public String getFuncname() {
        return funcname;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public String getFunctype() {
        return functype;
    }

    public void setFunctype(String functype) {
        this.functype = functype;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(String ismenu) {
        this.ismenu = ismenu;
    }

    public String getParainfo() {
        return parainfo;
    }

    public void setParainfo(String parainfo) {
        this.parainfo = parainfo;
    }
}
