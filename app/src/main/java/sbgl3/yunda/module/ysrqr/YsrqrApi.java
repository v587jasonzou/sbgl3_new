package sbgl3.yunda.module.ysrqr;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.ysrqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.ysrqr.entry.RepairScopeCase;
import sbgl3.yunda.module.ysrqr.entry.RepairWorkOrder;

public interface YsrqrApi {

    /**
     * 验收人确认列表
     *
     **/
    @FormUrlEncoded
    @POST("repairTaskList/acceptor")
    Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getEquipments(@Field("start") int start,
                                                                             @Field("limit") int limit,
                                                                             @Field("entityJson") String entityJson);
    /**
     * 验收人确认列表all
     *
     **/
    @FormUrlEncoded
    @POST("repairTaskList/acceptor")
    Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getAllEquipments(@Field("entityJson") String entityJson);

    /**
     * 获取检修范围
     */
    @FormUrlEncoded
    @POST("repairScopeCase/queryPageList.do")
    Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScops(@Field("start") int start,
                                                                      @Field("limit") int limit,
                                                                      @Field("entityJson") String entityJson);

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
    @POST("repairTaskList/ys/confirmed")
    Observable<BaseResponsBean> confirm(@Field("ids") String ids,
                                        @Field("acceptanceReviews") String acceptanceReviews,
                                        @Field("acceptorNames") String acceptorNames);
}
