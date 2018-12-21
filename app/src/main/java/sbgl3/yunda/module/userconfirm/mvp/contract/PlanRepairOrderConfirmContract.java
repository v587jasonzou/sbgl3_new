package sbgl3.yunda.module.userconfirm.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.entry.RepairScopeCase;
import sbgl3.yunda.module.userconfirm.entry.RepairWorkOrder;


public interface PlanRepairOrderConfirmContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadRepairScope(List<RepairScopeCase> list);
        void loadWorkOrder(List<RepairWorkOrder> list);
        void getNextDataSuccess(PlanRepairEquipListBean bean);
        PlanRepairEquipListBean loadNextData();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScopes(int start, int limit, String entityJson);
        Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(int start, int limit, String entityJson);
        Observable<BaseResponsBean> planRepairConfirm(String ids);

    }
}
