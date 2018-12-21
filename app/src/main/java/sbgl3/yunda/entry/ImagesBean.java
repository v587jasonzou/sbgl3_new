package sbgl3.yunda.entry;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
/**
 * <li>标题: 图片显示相关类
 * <li>说明: 图片本地url,服务器url,图片名称等信息
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class ImagesBean implements Serializable {
    private String imagesName;
    private String imagesPath;
    private String ImagesLocalPath;
//    private Bitmap imageBitmap;


    public String getImagesName() {
        return imagesName;
    }

    public void setImagesName(String imagesName) {
        this.imagesName = imagesName;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getImagesLocalPath() {
        return ImagesLocalPath;
    }

    public void setImagesLocalPath(String imagesLocalPath) {
        ImagesLocalPath = imagesLocalPath;
    }
}
