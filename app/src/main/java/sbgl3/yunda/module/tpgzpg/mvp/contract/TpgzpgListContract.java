package sbgl3.yunda.module.tpgzpg.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;


public interface TpgzpgListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void loadFaultData(List<FaultOrderBean> list);
        void loadMoreFaultData(List<FaultOrderBean> list);
        void loadFail();
        void loadSuccess();
        void getFixUserSuccess(List<UserInfo> list);
        void submitSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(int start, int limit, String whereListJson);
        Observable<UserResponse> getUsers(Long orgId, String empName);
        Observable<BaseResponsBean> confirm(String ids,String entityJson);
    }
}
