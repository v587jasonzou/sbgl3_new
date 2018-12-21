package sbgl3.yunda.module.sbxj.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;


public interface SBXJRecordContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void LoadData(List<InspectRecord> list);
        void LoadMoreData(List<InspectRecord> list);
        void SubmitRecordSuccess();
        void SubmitPlanSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean> UpLoadRecordTime(String ids);
        Observable<BaseResponsBean<List<InspectRecord>>> GetInspectRecords(int start,int limit,String entityJson);
        Observable<BaseResponsBean>ConfirmRecords(String ids,String checkResult);
        Observable<BaseResponsBean>ConfirmEquipPlan(String entytiJson);
    }
}
