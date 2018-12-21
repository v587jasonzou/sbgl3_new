package sbgl3.yunda.module.gztp;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.entry.ResponsEquip;

public interface FaultTicketApi {
    //获取故障提票列表
    @FormUrlEncoded
    @POST("faultOrder/pageQuery.do")
    Observable<BaseResponsBean<List<FaultOrder>>> getFaultOrderList(@Field("start") int start,
                                                                    @Field("limit") int limit,
                                                                    @Field("whereListJson") String whereListJson);
    //获取故障提票编号
    @GET("faultOrder/createFaultOrderNo.do")
    Observable<FaultOrder> getFaultNo();
    //添加故障提票
    @FormUrlEncoded
    @POST("faultOrder/saveOrUpdate2.do")
    Observable<BaseResponsBean> AddFaultOrder(@Field("filePathArray") String filePathArray,
                                              @Field("entityJson") String entityJson);

    //添加故障提票设备信息
    @FormUrlEncoded
    @POST("faultOrder/getEquipmentByCode.do")
    Observable<ResponsEquip> getEquipInfo(@Field("equipmentCode") String equipmentCode,
                                          @Field("equipmentType") String equipmentType);

    //故障提票确认
    @FormUrlEncoded
    @POST("faultOrder/confirm.do")
    Observable<BaseResponsBean> confirmOrder(@Field("ids") String ids);

}
