package sbgl3.yunda.entry;

import java.io.Serializable;
/**
 * <li>标题: 事件总线传输点击事件相关类
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class ItemClickMessage implements Serializable {
    private String type;
    private int position;
    private String EquipCode;
    private String messageInfo;

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public String getEquipCode() {
        return EquipCode;
    }

    public void setEquipCode(String equipCode) {
        EquipCode = equipCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
