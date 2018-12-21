package sbgl3.yunda.entry;

/**
 * <li>标题: 设备管理系统
 * <li>说明: RFID扫描结果-EPC
 * <li>创建人：何东
 * <li>创建日期：2016年6月17日
 * <li>修改人: 黄杨
 * <li>修改日期：2017/6/30
 * <li>修改内容：取消tid
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 1.0
 */
public class EpcDataBase {
    public String epc;
    public String equipmentCode;
    public int valid;

    public EpcDataBase(){};
    
    public EpcDataBase(String e, int v) {
        epc = e;
        valid = v;
    }
}