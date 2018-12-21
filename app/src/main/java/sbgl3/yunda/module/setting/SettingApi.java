package sbgl3.yunda.module.setting;

import com.jess.arms.base.entry.BaseResponsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SettingApi {
    //下载巡检计划-巡检设备表
    @FormUrlEncoded
    @POST("pda/download.do")
    Observable<BaseResponsBean> upLoadInspectPlanEquip(@Field("className") String className,
                                                       @Field("limit") String limit,
                                                       @Field("start") String start);
}
