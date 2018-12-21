package sbgl3.yunda.module.evaluate;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluateBean;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;

public interface EquipmentEvaluateApi {
    /**
     * 获取评定设备列表
     **/
    @FormUrlEncoded
    @POST("equipmentEvaluate/queryPageList.do")
    Observable<BaseResponsBean<List<EquipmentEvaluateBean>>> getEvaluateEquipment(@Field("planStatus") String planStatus,
                                                                                      @Field("start") int start,
                                                                                      @Field("limit") int limit,
                                                                                      @Field("entityJson") String entityJson);


    /**
     * 提交评定结果
     **/
    @FormUrlEncoded
    @POST("equipmentEvaluate/updateResultByPda.do")
    Observable<BaseResponsBean> submitEvaluateResult(@Field("entityJson") String entityJson);

    /**
     * 获取评定计划
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/queryPageList.do")
    Observable<BaseResponsBean<List<EquipmentEvaluatePlanBean>>> getEvaluatePlan(@Field("start") int start,
                                                                                 @Field("limit") int limit,
                                                                                 @Field("entityJson") String entityJson);

    /**
     * 提交评定明细
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/savaOrUpdateByItemTypePDA.do")
    Observable<BaseResponsBean> submitEvaluateDetail(@Field("entityJson") String entityJson);

    /**
     * 获取下一条评定数据
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/getNextWorkOrderPDA.do")
    Observable<BaseResponsBean<EquipmentEvaluatePlanBean>> getNextData(@Field("idx") String idx);

    /**
     * 获取评定模板列表
     **/
    @FormUrlEncoded
    @POST("appraiseTemplate/pageQuery.do")
    Observable<BaseResponsBean<List<EvaluateTemplateBean>>> getTemplate(@Field("start") int start,
                                                                        @Field("limit") int limit,
                                                                        @Field("sort") String sort,
                                                                        @Field("whereListJson") String whereListJson);

    /**
     * 提交评定模板
     **/
    @FormUrlEncoded
    @POST("equipmentEvaluate/updateAppraiseTemplate.do")
    Observable<BaseResponsBean> addEvaluateTemplate(@Field("idx") String idx,
                                                    @Field("templateIdx") String templateIdx);

    /**
     * 初始化
     **/
    @FormUrlEncoded
    @POST("appraiseTmplData/startUp.do")
    Observable<BaseResponsBean> startUp(@Field("appraisePlanIdx") String appraisePlanIdx,
                                        @Field("planType") int planType);
}
