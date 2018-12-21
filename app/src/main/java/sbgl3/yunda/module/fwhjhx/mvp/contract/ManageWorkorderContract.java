package sbgl3.yunda.module.fwhjhx.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import io.reactivex.Observable;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairHistoryResponse;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;
import sbgl3.yunda.module.fwhjhx.mvp.entity.WorkOrderResponse;


public interface ManageWorkorderContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getImagesSuccess(List<ImagesBean> images);
        void confirmSuccess();
        void UpLoadImagesSuccess(ImagesBean bean);
        void getUnitSuccess(List<EosDictEntry> list);
        void getHistorysSuccess(List<String> list);
        void getFixUserSuccess(List<UserInfo> list);
        void getStuffSuccess(List<MateriaBean> list);
        void getNextOrderSuccess(RepairWorkOrder order);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponsBean> getImages(String idx);
        Observable<ImageUploadResponse> UpLoadImages(ImageItem image, String tableName);
        Observable<BaseResponsBean> ConfirmOrder(String idx,String OtherWorker,String RepairRecord,String filePathArray);
        Observable<List<EosDictEntry>> getUnits();
        Observable<RepairHistoryResponse> getHistory(int start, int limit, String definidx);
        Observable<UserResponse> getUsers(Long orgId, String empName);
        Observable<BaseResponsBean<List<MateriaBean>>> getStuffs(int start,int limit,String entityJson);
        Observable<WorkOrderResponse> getNextWorkOrder(String idx);
    }
}
