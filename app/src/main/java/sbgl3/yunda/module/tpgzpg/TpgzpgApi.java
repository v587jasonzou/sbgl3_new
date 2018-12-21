package sbgl3.yunda.module.tpgzpg;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;

public interface TpgzpgApi {
    /**
     * 故障提票列表
     *
     **/
    @FormUrlEncoded
    @POST("faultOrder/pageQuery.do")
    Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(@Field("start") int start,
                                                                         @Field("limit") int limit,
                                                                         @Field("whereListJson") String whereListJson);

    /**
     * 退回信息
     *
     **/
    @FormUrlEncoded
    @POST("faultOrderRecord/pageQuery.do")
    Observable<BaseResponsBean<List<BackInfoBean>>> getBackInfo(@Field("sort") String sort, @Field("whereListJson") String whereListJson);

    /**
     * 派工
     *
     **/
    @FormUrlEncoded
    @POST("faultOrder/saveRepairEmpForPDA.do")
    Observable<BaseResponsBean> confirm(@Field("ids") String ids, @Field("entityJson") String entityJson);

    /**
     * 退回
     *
     **/
    @FormUrlEncoded
    @POST("faultOrderRecord/updateToBack.do")
    Observable<BaseResponsBean> back(@Field("faultOrderIdx") String ids,
                                     @Field("backReason") String backReason,
                                     @Field("backPersonDuty") String backPersonDuty);
}
