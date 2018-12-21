package sbgl3.yunda.entry;

import java.io.Serializable;
import java.util.List;

/**
 * <li>标题: 登录Response
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class UserResponse implements Serializable {
   private Boolean success;
   private List<UserInfo> userList;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }
}
