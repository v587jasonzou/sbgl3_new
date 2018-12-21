package sbgl3.yunda.module.fwhjhx;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairHistoryResponse;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairScopeCase;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;
import sbgl3.yunda.module.fwhjhx.mvp.entity.StuffParams;
import sbgl3.yunda.module.fwhjhx.mvp.entity.WorkOrderResponse;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;

public interface RepairApi {

    /**
     * 计划检修，待检修设备
     **/
    @FormUrlEncoded
    @POST("repairTaskList/task")
    Observable<BaseResponsBean<List<RepairTaskList>>> getEquipLists(@Field("start") int start,
                                                                    @Field("limit") int limit,
                                                                    @Field("entityJson") String entityJson);

    /**
     * 获取检修范围
     **/
    @FormUrlEncoded
    @POST("repairScopeCase/queryPageList.do")
    Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScopes(@Field("start") int start,
                                                                       @Field("limit") int limit,
                                                                       @Field("orgId") long orgId,
                                                                       @Field("entityJson") String entityJson);

    /**
     * 获取检修工单
     **/
    @FormUrlEncoded
    @POST("repairWorkOrder/pageList.do")
    Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(@Field("start") int start,
                                                                     @Field("limit") int limit,
                                                                     @Field("entityJson") String entityJson);

    /**
     * 更新开工时间
     **/
    @FormUrlEncoded
    @POST("repairTaskList/updateRealBeginTime.do")
    Observable<BaseResponsBean> UpdataTime(@Field("ids") String ids);

    /**
     * 批量提交工单
     **/
    @FormUrlEncoded
    @POST("repairWorkOrder/updateFinishBatch.do")
    Observable<BaseResponsBean> ConfirmOrderAll(@Field("jsonData") String jsonData);

    /**
     * 提交工单
     **/
    @FormUrlEncoded
    @POST("repairWorkOrder/updateFinish.do")
    Observable<BaseResponsBean> ConfirmOrder(@Field("idx") String idx);


    /**
     * 查询特殊符号
     **/
    @GET("dict/list/E_SBJX_UNIT")
    Observable<List<EosDictEntry>> getUnits();

    /**
     * 获取你是审批结果
     **/
    @FormUrlEncoded
    @POST("repairWorkOrder/findHistoryRepairRecords.do")
    Observable<RepairHistoryResponse> getHistorys(@Field("start") int start,
                                                  @Field("limit") int limit,
                                                  @Field("defineIdx") String defineIdx);

    /**
     * 获取物料信息
     **/
    @FormUrlEncoded
    @POST("repairWorkOrderStuff/pageList.do")
    Observable<BaseResponsBean<List<MateriaBean>>> getStuffs(@Field("start") int start,
                                                             @Field("limit") int limit,
                                                             @Field("entityJson") String entityJson);

    /**
     * 获取物料信息
     **/
    @FormUrlEncoded
    @POST("stuffClass/pageList.do")
    Observable<BaseResponsBean<List<StuffParams>>> getStuffParams(@Field("start") int start,
                                                                  @Field("limit") int limit,
                                                                  @Field("entityJson") String entityJson);
    /**
     * 物料信息添加修改
     **/
    @FormUrlEncoded
    @POST("repairWorkOrderStuff/saveOrUpdate.do")
    Observable<BaseResponsBean> getUpdateStuff(@Field("entityJson") String entityJson);

    /**
     * 提交工单
     **/
    @FormUrlEncoded
    @POST("repairWorkOrder/updateFinish.do")
    Observable<BaseResponsBean> ConfirmOrderNew(@Field("idx") String idx,
                                                  @Field("otherWorkerName") String otherWorkerName,
                                                  @Field("repairRecord")String repairRecord,
                                                  @Field("filePathArray")String filePathArray);

    /**
     * 提交工单
     **/
    @FormUrlEncoded
    @POST("repairWorkOrder/getNextWorkOrder.do")
    Observable<WorkOrderResponse> getNextOrder(@Field("idx") String idx);
    /**
     * 开始待料
     **/
    @FormUrlEncoded
    @POST("repairWorkOrderStuff/updateWaitStart.do")
    Observable<BaseResponsBean> StartWaitStuff(@Field("idx") String idx);
    /**
     * 结束待料
     **/
    @FormUrlEncoded
    @POST("repairWorkOrderStuff/updateWaitEnd.do")
    Observable<BaseResponsBean> EndWaitStuff(@Field("idx") String idx);

    /**
     * 删除物料
     **/
    @FormUrlEncoded
    @POST("repairWorkOrderStuff/delete.do")
    Observable<BaseResponsBean> DeleteStuff(@Field("ids") String idx);
}
