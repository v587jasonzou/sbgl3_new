package sbgl3.yunda.globle;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;

public interface GlobleApi {
    /**
     * 删除照片
     **/
    @FormUrlEncoded
    @POST("pdaAttachment/deleteImage.do")
    Call<BaseResponsBean> DeleteImages(@Field("filePath") String filePath);

    /**
     * 获取照片
     **/
    @FormUrlEncoded
    @POST("pdaAttachment/downloadImages.do")
    Observable<BaseResponsBean> getImages(@Field("attachmentKeyIDX") String attachmentKeyIDX);

    /**
     * 上传照片
     **/
    @FormUrlEncoded
    @POST("pdaAttachment/uploadImage.do")
    Observable<ImageUploadResponse> UpLoadImages(@Field("photoBase64Str") String photoBase64Str,
                                                 @Field("extName") String extName,
                                                 @Field("tableName") String tableName);

    /**
     * 提交照片
     **/
    @FormUrlEncoded
    @POST("equipmentPrimaryInfo/saveUploadImage.do")
    Observable<BaseResponsBean> Confirmphotos(@Field("idx") String idx,
                                              @Field("TableName") String TableName,
                                              @Field("filePathArray") String filePathArray);

    /**
     * 获取用户数据
     **/
    @FormUrlEncoded
    @POST("employee/getEmpListByOrgid.do")
    Observable<UserResponse> getUsers(@Field("orgId") long orgId,
                                      @Field("empName") String empName);

}
