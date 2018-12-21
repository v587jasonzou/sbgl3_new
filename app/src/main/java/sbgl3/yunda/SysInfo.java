package sbgl3.yunda;

import java.util.ArrayList;
import java.util.List;

import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.module.main.Entry.MenuBean;

/**
 * 用户信息相关类
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class SysInfo {
    public static UserInfo user;
    public static String empname;// 人员姓名
    public static Long empid; // 人员编号
    public static Long orgid; // 主机构编号
    public static Long operatorid; // 操作员编号
    public static String uuid;
    public static List<MenuBean> menus = new ArrayList<>();
}
