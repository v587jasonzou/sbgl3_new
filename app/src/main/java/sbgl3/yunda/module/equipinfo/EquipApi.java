package sbgl3.yunda.module.equipinfo;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;

public interface EquipApi {

    /**
     * 设备信息查看，设备信息列表
     **/
    @FormUrlEncoded
    @POST("equipmentPrimaryInfo/pageList.do")
    Observable<BaseResponsBean<List<EquipmentPrimaryInfo>>> getEquipLists(@Field("start") int start,
                                                                          @Field("limit") int limit,
                                                                          @Field("entityJson") String entityJson);

}
