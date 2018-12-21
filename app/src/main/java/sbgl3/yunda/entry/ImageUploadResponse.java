package sbgl3.yunda.entry;

import java.io.Serializable;
/**
 * <li>标题: 图片上传相关类
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class ImageUploadResponse implements Serializable {

    /**
     * success : true
     * filePath : F:\EquipmentUpload\e_inspect_record\2018\8\20180828172256701.jpg
     */

    private Boolean success;
    private String filePath;

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
