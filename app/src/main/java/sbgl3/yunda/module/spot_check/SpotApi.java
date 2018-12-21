package sbgl3.yunda.module.spot_check;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.spot_check.entity.SpotCheckResponse;

public interface SpotApi {
    /**
     * 获取点检计划
     **/
    @FormUrlEncoded
    @POST("pointCheck/startUp.do")
    Observable<SpotCheckResponse> getSpotCheck(@Field("equipmentCode") String equipmentCode);

    /**
     * 保存点检项状态
     **/
    @FormUrlEncoded
    @POST("pointCheckContent/updateTechnologyStateFlag.do")
    Observable<BaseResponsBean> SaveItemStatus(@Field("idx") String idx,
                                              @Field("technologyStateFlag") String technologyStateFlag);
    /**
     * 提交点检
     **/
    @FormUrlEncoded
    @POST("pointCheck/updateFinish.do")
    Observable<BaseResponsBean> ConfirmSpot(@Field("entityJson") String entityJson);
    /**
     * 开启设备
     **/
    @FormUrlEncoded
    @POST("pointCheck/startUpEquipment.do")
    Observable<BaseResponsBean> StartEquip(@Field("idx") String idx);

    /**
     * 停止设备
     **/
    @FormUrlEncoded
    @POST("pointCheck/endUpEquipment.do")
    Observable<SpotCheckResponse> EndEquip(@Field("idx") String idx);
}
