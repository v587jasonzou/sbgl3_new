package sbgl3.yunda.module.appraise;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraiserBean;

public interface EquipmentAppraiseApi {
    /**
     * 获取鉴定设备列表
     **/
    @FormUrlEncoded
    @POST("equipmentIdentify/queryPageList.do")
    Observable<BaseResponsBean<List<EquipmentAppraiserBean>>> getAppraiseEquipment(@Field("planStatus") String  planStatus,
                                                                                   @Field("start") int start,
                                                                                   @Field("limit") int limit,
                                                                                   @Field("entityJson") String entityJson);


    /**
     * 提交鉴定结果
     **/
    @FormUrlEncoded
    @POST("equipmentIdentify/updateResultByPda.do")
    Observable<BaseResponsBean> submitAppraiseResult(@Field("entityJson") String entityJson);

    /**
     * 获取鉴定计划
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/queryPageList.do")
    Observable<BaseResponsBean<List<EquipmentAppraisePlanBean>>> getAppraisePlan(@Field("start") int start,
                                                                                 @Field("limit") int limit,
                                                                                 @Field("entityJson") String entityJson);

    /**
     * 提交鉴定明细
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/savaOrUpdateByItemTypePDA.do")
    Observable<BaseResponsBean> submitAppraiseDetail(@Field("entityJson") String entityJson);

    /**
     * 获取下一条鉴定数据
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/getNextWorkOrderPDA.do")
    Observable<BaseResponsBean<EquipmentAppraisePlanBean>> getNextData(@Field("idx") String idx);

    /**
     * 获取鉴定设备列表
     **/
    @FormUrlEncoded
    @POST("appraiseTemplate/pageQuery.do")
    Observable<BaseResponsBean<List<AppraiseTemplateBean>>> getTemplate(@Field("start") int start,
                                                                        @Field("limit") int limit,
                                                                        @Field("sort") String  sort,
                                                                        @Field("whereListJson") String whereListJson);

    /**
     * 提交模板
     **/
    @FormUrlEncoded
    @POST("equipmentIdentify/updateAppraiseTemplate.do")
    Observable<BaseResponsBean> addAppraiseTemplate(@Field("idx") String idx,
                                                    @Field("templateIdx") String templateIdx);

    /**
     * 初始化
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/startUp.do")
    Observable<BaseResponsBean> startUp(@Field("appraisePlanIdx") String appraisePlanIdx,
                                            @Field("planType") int planType);
}
