package sbgl3.yunda.module.gztp.mvp.contract;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lzy.imagepicker.bean.ImageItem;

import io.reactivex.Observable;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.module.gztp.entry.EquipmentPrimaryInfo;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.entry.ResponsEquip;


public interface FaultTicketEditContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getFaultNoSuccess(String num);
        void onLoadFaild(String type,String msg);
        void UpLoadImagesSuccess(ImagesBean bean);
        void addOrderSuccess();
        void getEquipSuccess(EquipmentPrimaryInfo equip);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<FaultOrder> getFaultOrderNo();
        Observable<ImageUploadResponse> UpLoadImages(ImageItem image, String tableName);
        Observable<BaseResponsBean> AddOrder(String filePathArray,String entityJson);
        Observable<ResponsEquip> getEquipInfo(String equipmentCode,String equipmentType);
    }
}
