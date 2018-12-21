package sbgl3.yunda.module.sbdj.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import sbgl3.yunda.module.sbdj.entity.EquipResponse;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;


public interface SbdjMainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void OnLoadEquipsSuccess(List<EquipmentUnionRFID> list);
        void OnLoadMoreEquipsSuccess(List<EquipmentUnionRFID> list);
        void OnLoadFaild(String msg);
        void OnRegisterSuccess();
        void OnRegisterFaild(String msg);
        void OnUpLoadResSuccess(EquipResponse stringObjectMap);
        void UnRegiterSuccess();
        void UnRegisterFaild(String msg);
        void OnUpLoadResFaild(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<EquipmentUnionRFID>>> getEquipments(int start,int limit
        ,String entityJson);
        Observable<BaseResponsBean> RegisterEquip(String entityJson);
        Observable<EquipResponse> UpLoadRegister(String equipmentCode);
        Observable<BaseResponsBean> UnRegisterEquip(String ids);
    }
}
