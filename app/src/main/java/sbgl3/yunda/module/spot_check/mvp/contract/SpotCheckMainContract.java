package sbgl3.yunda.module.spot_check.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;
import sbgl3.yunda.module.spot_check.entity.PointCheck;
import sbgl3.yunda.module.spot_check.entity.SpotCheckResponse;


public interface SpotCheckMainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getSpotCheckSuccess(PointCheck entity);
        void OnItemStatusChangeSuccess();
        void CofirmSpotSuccess();
        void StartEquipSuccess();
        void EndEquipSuccess(PointCheck check);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<SpotCheckResponse> getSpotCheck(String code);
        Observable<BaseResponsBean> ChangeItemStatus(String idx,String statu);
        Observable<BaseResponsBean> ConfirmSpot(String entityJson);
        Observable<BaseResponsBean> StartEquip(String idx);
        Observable<SpotCheckResponse> EndEquip(String idx);
    }
}
