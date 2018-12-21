package sbgl3.yunda.entry;

import java.io.Serializable;
/**
 * <li>标题: 事件总线信息传输相关类
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class MessageBean<T> implements Serializable {
    private String msgType;
    private String msgInfo;
    private Boolean isSuccess;
    private T msgContent;
    private Integer position;
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public T getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(T msgContent) {
        this.msgContent = msgContent;
    }
}
