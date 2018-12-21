package sbgl3.yunda.module.fwhjhx.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.StuffParams;


public interface StuffContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getStuffsSucceess(List<MateriaBean> list);
        void getStuffsParamsSuccess(List<StuffParams> list);
        void UpdataStuffSuccess(String type);
        void StartStuffWaitSuccess();
        void EndStuffWaitSuccess();
        void DeleteStuffSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<MateriaBean>>> getStuffs(int start,int limit,String entityJson);
        Observable<BaseResponsBean<List<StuffParams>>> getStuffsParam(int start,int limit,String entityJson);
        Observable<BaseResponsBean>UpdateStuff(String entityJson);
        Observable<BaseResponsBean>StartStuffWait(String idx);
        Observable<BaseResponsBean>EndStuffWait(String idx);
        Observable<BaseResponsBean>DeleteStuff(String ids);
    }
}
