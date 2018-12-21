package sbgl3.yunda.module.userconfirm;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.entry.RepairScopeCase;
import sbgl3.yunda.module.userconfirm.entry.RepairWorkOrder;

public interface UserConfirmApi {

    /**
     * 计划修待确认列表
     *
     **/
    @FormUrlEncoded
    @POST("repairTaskList/user")
    Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getPlanRepairEquipments(@Field("start") int start,
                                                                                       @Field("limit") int limit,
                                                                                       @Field("entityJson") String entityJson);

    /**
     * 计划修所有待确认设备all
     *
     **/
    @FormUrlEncoded
    @POST("repairTaskList/user")
    Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getAllPlanRepairEquipments(@Field("entityJson") String entityJson);


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
     * 故障处理待确认列表
     *
     **/
    @FormUrlEncoded
    @POST("faultOrder/queryPageList2User.do")
    Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(@Field("start") int start,
                                                                         @Field("limit") int limit,
                                                                         @Field("entityJson") String entityJson);

    /**
     * 获取故障处理所有待确认设备all
     *
     **/
    @FormUrlEncoded
    @POST("faultOrder/queryPageList2User.do")
    Observable<BaseResponsBean<List<FaultOrderBean>>> getAllFaultEquipments(@Field("entityJson") String entityJson);


    /**
     * 计划修使用人确认
     */
    @FormUrlEncoded
    @POST("repairTaskList/syr/confirmed")
    Observable<BaseResponsBean> planRepairConfirm(@Field("ids") String ids);

    /**
     * 故障处理使用人确认
     */
    @FormUrlEncoded
    @POST("faultOrder/confirm.do")
    Observable<BaseResponsBean> faultConfirm(@Field("ids") String ids);
}
