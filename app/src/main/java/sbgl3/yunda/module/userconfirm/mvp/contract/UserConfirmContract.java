package sbgl3.yunda.module.userconfirm.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;


public interface UserConfirmContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadData(List<PlanRepairEquipListBean> list);
        void loadMoreData(List<PlanRepairEquipListBean> list);
        void loadAllPlanData(List<PlanRepairEquipListBean> list);
        void loadFaultData(List<FaultOrderBean> list);
        void loadAllFaultData(List<FaultOrderBean> list);
        void loadMoreFaultData(List<FaultOrderBean> list);
        void loadFail();
        void loadSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getPlanRepairEquipments (int start, int limit, String entityJson);
        Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getAllPlanRepairEquipments (String entityJson);
        Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(int start,int limit,String entityJson);
        Observable<BaseResponsBean<List<FaultOrderBean>>> getAllFaultEquipments(String entityJson);
    }
}
