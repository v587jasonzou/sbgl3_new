package sbgl3.yunda.module.fwhjhx.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairScopeCase;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;


public interface FwhRepaiScopsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void OnLoadRepairScopeSuccess(List<RepairScopeCase> list);
        void OnLoadOrderSuccess(List<RepairWorkOrder> list);
        void ConfirmAllSuccess();
        void ConfirmSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScopes(String entityJson);
        Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(String entityJson);
        Observable<BaseResponsBean> Updatatime(String ids);
        Observable<BaseResponsBean> ConfirmAll(String jsondata);
        Observable<BaseResponsBean> Confirm(String idx);
    }
}
