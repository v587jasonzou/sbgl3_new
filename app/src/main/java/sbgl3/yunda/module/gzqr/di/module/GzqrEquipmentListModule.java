package sbgl3.yunda.module.gzqr.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gzqr.mvp.contract.GzqrEquipmentListContract;
import sbgl3.yunda.module.gzqr.mvp.model.GzqrEquipmentListModel;


@Module
public class GzqrEquipmentListModule {
    private GzqrEquipmentListContract.View view;

    /**
     * 构建GzqrEquipmentListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GzqrEquipmentListModule(GzqrEquipmentListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GzqrEquipmentListContract.View provideGzqrEquipmentListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GzqrEquipmentListContract.Model provideGzqrEquipmentListModel(GzqrEquipmentListModel model) {
        return model;
    }
}