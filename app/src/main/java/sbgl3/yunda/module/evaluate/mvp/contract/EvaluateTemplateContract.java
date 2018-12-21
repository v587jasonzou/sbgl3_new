package sbgl3.yunda.module.evaluate.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;


public interface EvaluateTemplateContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void LoadData(List<EvaluateTemplateBean> list);
        void LoadMoreData(List<EvaluateTemplateBean> list);
        void loadFail();
        void loadSuccess();
        void addSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean<List<EvaluateTemplateBean>>> getTemplate(int start, int limit, String sort, String whereListJson);
        Observable<BaseResponsBean> addEvaluateTemplate (String idx, String templateIdx);
        Observable<BaseResponsBean> startUp (String appraisePlanIdx, int planType);
    }
}
