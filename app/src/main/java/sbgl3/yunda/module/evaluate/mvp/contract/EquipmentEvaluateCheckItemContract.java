package sbgl3.yunda.module.evaluate.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;


public interface EquipmentEvaluateCheckItemContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void LoadData(List<EquipmentEvaluatePlanBean> list);
        void LoadMoreData(List<EquipmentEvaluatePlanBean> list);
        void loadFail();
        void loadSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<EquipmentEvaluatePlanBean>>> getEvaluatePlan(int start, int limit, String entityJson);
    }
}
