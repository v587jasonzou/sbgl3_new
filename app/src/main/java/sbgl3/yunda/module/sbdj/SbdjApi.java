package sbgl3.yunda.module.sbdj;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.sbdj.entity.EquipResponse;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;

public interface SbdjApi {
    /**
     * 获取设备登记信息
     **/
    @FormUrlEncoded
    @POST("equipmentUnionRFID/queryPageList.do")
    Observable<BaseResponsBean<List<EquipmentUnionRFID>>> getEquipInfos(@Field("start") int start,
                                                                        @Field("limit") int limit,
                                                                        @Field("entityJson") String entityJson);

    /**
     * 设备信息登记
     **/
    @FormUrlEncoded
    @POST("equipmentUnionRFID/save.do")
    Observable<BaseResponsBean> registerEquip(@Field("entityJson") String entityJson);

    /**
     * 验证RFID的编码是否是设备的编码
     **/
    @FormUrlEncoded
    @POST("equipmentUnionRFID/checkIsBind.do")
    Observable<EquipResponse> upLoadEquip(@Field("equipmentCode") String equipmentCode);

    /**
     * 解除绑定
     **/
    @FormUrlEncoded
    @POST("equipmentUnionRFID/delete.do")
    Observable<BaseResponsBean> UnregisterEquip(@Field("ids") String ids);


}
