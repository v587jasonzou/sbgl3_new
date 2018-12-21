package sbgl3.yunda.module.ysrqr.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.ysrqr.mvp.contract.YsrqrEquipmentListContract;
import sbgl3.yunda.module.ysrqr.mvp.model.YsrqrEquipmentListModel;


@Module
public class YsrqrEquipmentListModule {
    private YsrqrEquipmentListContract.View view;

    /**
     * 构建YsrqrEquipmentListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public YsrqrEquipmentListModule(YsrqrEquipmentListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    YsrqrEquipmentListContract.View provideYsrqrEquipmentListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    YsrqrEquipmentListContract.Model provideYsrqrEquipmentListModel(YsrqrEquipmentListModel model) {
        return model;
    }
}