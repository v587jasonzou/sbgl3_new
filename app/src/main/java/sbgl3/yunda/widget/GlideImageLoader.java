package sbgl3.yunda.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.loader.ImageLoader;

import sbgl3.yunda.R;

/**
 * 拍照及照片选择相关类-Glide图片加载器
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.image_upload_icon)    //加载成功之前占位图
                .error(R.mipmap.image_upload_faild)    //加载错误之后的错误图
                .override(width, height)    //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
//                .fitCenter();
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop();
//                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
//                .skipMemoryCache(true)	//跳过内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
//                .diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
//                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
        Glide.with(activity)
                .load(path)
                .thumbnail(0.2f)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.image_upload_icon)    //加载成功之前占位图
                .error(R.mipmap.image_upload_faild)    //加载错误之后的错误图
                .override(width, height)    //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
//                .fitCenter();
                .centerCrop();
        Glide.with(activity)
                .load(path)
                .thumbnail(0.2f)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
