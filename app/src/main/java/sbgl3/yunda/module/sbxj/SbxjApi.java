package sbgl3.yunda.module.sbxj;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;

public interface SbxjApi {
    /**
     * 获取巡检计划
     **/
    @FormUrlEncoded
    @POST("inspectPlan/queryPageList.do")
    Observable<BaseResponsBean<List<InspectPlanBean>>> getInspectPlan(@Field("start") int start,
                                                                      @Field("limit") int limit,
                                                                      @Field("entityJson") String entityJson);

    /**
     * 获取巡检计划设备
     **/
    @FormUrlEncoded
    @POST("inspectPlanEquipment/queryPageList2.do")
    Observable<BaseResponsBean<List<InspectPlanEquipmentBean>>> getInspectPlanEquip(@Field("start") int start,
                                                                                    @Field("limit") int limit,
                                                                                    @Field("entityJson") String entityJson);

    /**
     * 更新巡检开工时间
     **/
    @FormUrlEncoded
    @POST("inspectPlanEquipment/updateRealBeginTime.do")
    Observable<BaseResponsBean> UpLoadRecordTime(@Field("ids") String ids);

    /**
     * 获取巡检范围
     **/
    @FormUrlEncoded
    @POST("inspectRecord/findPageList.do")
    Observable<BaseResponsBean<List<InspectRecord>>> getInspectRecords(@Field("start") int start,
                                                                       @Field("limit") int limit,
                                                                       @Field("entityJson") String entityJson);

    /**
     * 批量提交巡检项
     **/
    @FormUrlEncoded
    @POST("inspectRecord/confirm.do")
    Observable<BaseResponsBean> ConfirmRecords(@Field("ids") String start,
                                               @Field("checkResult") String checkResult);
    /**
     * 提交巡检项
     **/
    @FormUrlEncoded
    @POST("inspectRecord/confirm.do")
    Observable<BaseResponsBean> ConfirmRecord(@Field("ids") String ids,
                                              @Field("checkResult") String checkResult,
                                              @Field("filePathArray") String filePathArray);

    /**
     * 批量提交巡检计划
     **/
    @FormUrlEncoded
    @POST("inspectPlanEquipmentEmp/updateFinish.do")
    Observable<BaseResponsBean> ConfirmEquipPlan(@Field("entityJson") String entityJson);

}
