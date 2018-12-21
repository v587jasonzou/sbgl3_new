package sbgl3.yunda.module.gzcl;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;

public interface GzclApi {

    /**
     * 故障处理待确认列表
     *
     **/
    @FormUrlEncoded
    @POST("faultOrder/pageQuery.do")
    Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(@Field("start") int start,
                                                                         @Field("limit") int limit,
                                                                         @Field("whereListJson") String whereListJson);

    /**
     * 故障处理提交
     *
     **/
    @FormUrlEncoded
    @POST("faultOrder/updateFinish.do")
    Observable<BaseResponsBean> submitOrder(@Field("idx") String idx,
                                            @Field("entityJson") String entityJson,
                                            @Field("filePathArray") String filePathArray);
}
