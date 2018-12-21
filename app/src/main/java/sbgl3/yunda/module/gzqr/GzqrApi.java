package sbgl3.yunda.module.gzqr;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.gzqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.gzqr.entry.RepairScopeCase;
import sbgl3.yunda.module.gzqr.entry.RepairWorkOrder;

public interface GzqrApi {

    /**
     * 工长确认列表
     *
     **/
    @FormUrlEncoded
    @POST("repairTaskList/foreman")
    Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getEquipments(@Field("start") int start,
                                                                             @Field("limit") int limit,
                                                                             @Field("entityJson") String entityJson,
                                                                             @Field("orgId") Long orgId);

    /**
     * 获取检修范围--分页
     *
     */
    @FormUrlEncoded
    @POST("repairScopeCase/queryPageList.do")
    Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScops(@Field("start") int start,
                                                                      @Field("limit") int limit,
                                                                      @Field("entityJson") String entityJson,
                                                                      @Field("orgId") Long orgId);

    /**
     * 获取检修工单
     */
    @FormUrlEncoded
    @POST("repairWorkOrder/pageList.do")
    Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(@Field("start") int start,
                                                                     @Field("limit") int limit,
                                                                     @Field("entityJson") String entityJson);

    /**
     * 确认
     */
    @FormUrlEncoded
    @POST("repairTaskList/gz/confirmed")
    Observable<BaseResponsBean> confirmSave(@Field("ids") String ids);
}
