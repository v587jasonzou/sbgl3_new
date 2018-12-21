package com.jess.arms.base;

import android.os.Environment;

public class AppConstant {
    /** 处理设备编码（补足20个字符），用于保存到RFID标签内 */
    public static String DEV_CODE_FIX_CHAR = "*";
    /** 拍摄照片保存地址 */
    public static String IMAGE_PATH = Environment.getExternalStorageDirectory() + "/sbgl3/images/";
    // 照片后缀
    public static final String JPG_EXTNAME = ".jpg";
    public static final int PHOTO_COMPRESS_MAX_WIGTH = 800;
    public static final int PHOTO_COMPRESS_MAX_HEIGHT = 1200;
}
